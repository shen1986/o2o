# 学校商铺系统
学校商铺系统

## IDEA破解方法
- [IDEA破解方法](https://shimo.im/docs/9pJJRJPr6thtPxJd/read)

## thumbnailator
- [图片压缩以及水印处理](https://mvnrepository.com/artifact/net.coobird/thumbnailator)

## MUI mobile
- [SUI mobile](https://sui.ctolog.com/)

## jackson-databind
- JSON 转换用

## 第三方验证码图片
- kaptcha

## 读写分离
- 数据层面的主从配置实现
    + Master 主
        * 主要承担更新数据的工作
        ```shall
        [mysqld]
        server-id=1
        log-bin=master-bin
        log-bin-index=master-bin.index
        ```
        * 查看使用`SHOW MASTER STATUS`命令 
        * create user repl;
        *  GRANT REPLICATION SLAVE ON *.* TO 'repl'@'从机地址' IDENTIFIED BY 'mysql';
        *  flush privileges;
    + Slave 从
        * 主要承担数据读取的工作
        ```shall
        [mysqld]
        # slave config setting
        server-id=2
        relay-log-index=slave-relay-bin.index
        relay-log=slave-relay-bin
        ```
        *  ` change master to master_host='主机地址',master_port=3306,master_user='repl',master_password='mysql',master_log_file='master-bin.000001',master_log_pos=0;`
        * ` start slave;`
        * `show master status\G;`
     + 这个项目的第一个坑，主从服务的的mysql版本不一样，导致从数据库删除重新安装。
     主服务器的是docker的mysql，进入虚拟机改配置无法vim，安装vim后死机。重装加重新配置，耗时半天。
     + 连带笔记
     + 授予某个IP远程链接权限
     grant all PRIVILEGES on *.* to root@'远程主机ip'  identified by 'root';
     
     + docker 宿主与虚拟机相互复制
     docker cp /root/mysql/conf/ 03f56c464917:/etc/mysql
     docker cp /etc/mysql/conf/mysql.conf.d/mysqld.cnf b7c958c7c387:/etc/mysql/mysql.conf.d/ 
     
     + 修改数据库密码
     update user set password=password('xxxx') where user = 'root';
     
- 代码层面的读写分离实现
    + 在mybatis里面追加拦截器配置
    ```xml
    <plugins>
        <plugin interceptor="com.shenxf.o2o.dao.split.DynamicDataSourceInterceptor"></plugin>
    </plugins>
    ```
    + 编写拦截器
    ```java
    @Intercepts({@Signature(type = Executor.class, method="update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method="query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class})})
    public class DynamicDataSourceInterceptor implements Interceptor {
        private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);
        private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";
        
        @Override
        public Object intercept(Invocation invocation) throws Throwable {
            boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
            Object[] objects = invocation.getArgs();
            MappedStatement ms = (MappedStatement) objects[0];
            String lookupKey = DynamicDataSourceHolder.DB_MASTER;;
    
            if (!synchronizationActive) {
    
                // 读方法
                if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
    
                    // selectKey 为自增id查询主键（Select last_INSERT_ID()）方法，使用主库
                    if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                        lookupKey = DynamicDataSourceHolder.DB_MASTER;
                    } else {
                        BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
                        String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
    
                        if (sql.matches(REGEX)) {
                            lookupKey = DynamicDataSourceHolder.DB_MASTER;
                        } else {
                            lookupKey = DynamicDataSourceHolder.DB_SLAVE;
                        }
                    }
                }
            } else {
                lookupKey = DynamicDataSourceHolder.DB_MASTER;
            }
            logger.debug("设置方法[{}] use[{}] Strategy, SqlCommanType [{}]..",
                    ms.getId(), lookupKey, ms.getSqlCommandType().name());
            // DynamicDataSourceHolder为一个静态变量，当发生拦截就对这个变量进行赋值，在后续Spring调用Dao层时再使用此变量
            DynamicDataSourceHolder.setDbType(lookupKey);
            return invocation.proceed();
        }
    
        // 发生增删改查的时候，让程序进入我们的拦截逻辑
        @Override
        public Object plugin(Object target) {
            if (target instanceof Executor) {
                return Plugin.wrap(target, this);
            } else {
                return target;
            }
        }
    
        @Override
        public void setProperties(Properties properties) {
    
        }
    }
    ```
  
    + 修改Spring-dao的配置
    ```xml
    <!-- 把主从连接的共通配置写在抽象对象中 -->
    <bean id="abstractDataSource" abstract="true"
		  class="com.mchange.v2.c3p0.ComboPooledDataSource" destroy-method="close">

		<!-- c3p0连接池的私有属性 -->
		<property name="maxPoolSize" value="30" />
		<property name="minPoolSize" value="10" />
		<!-- 关闭连接后不自动commit -->
		<property name="autoCommitOnClose" value="false" />
		<!-- 获取连接超时时间 -->
		<property name="checkoutTimeout" value="10000" />
		<!-- 当获取连接失败重试次数 -->
		<property name="acquireRetryAttempts" value="2" />
	</bean>
    
    <!-- 主连接配置，并继承共通抽象对象 -->
	<bean id="master" parent="abstractDataSource">
		<!-- 配置连接池属性 -->
		<property name="driverClass" value="${jdbc.driver}" />
		<property name="jdbcUrl" value="${jdbc.master.url}" />
		<property name="user" value="${jdbc.username}" />
		<property name="password" value="${jdbc.password}" />
	</bean>

    <!-- 从连接配置，并继承共通抽象对象 -->
	<bean id="slave" parent="abstractDataSource">
		<!-- 配置连接池属性 -->
		<property name="driverClass" value="${jdbc.driver}" />
		<property name="jdbcUrl" value="${jdbc.slave.url}" />
		<property name="user" value="${jdbc.username}" />
		<property name="password" value="${jdbc.password}" />
	</bean>

	<!-- 配置动态数据源，这儿的targetDataSource就是路由数据源对应的名称, 会从DynamicDataSourceHolder中读取当前使用主、从的数据库 -->
	<bean id="dynamicDataSource" class="com.shenxf.o2o.dao.split.DynamicDataSource">
		<property name="targetDataSources" >
			<map>
				<entry value-ref="master" key="master"></entry>
				<entry value-ref="slave" key="slave"></entry>
			</map>
		</property>
	</bean>

    <!-- dataSource懒加载 -->
	<bean id="dataSource"
		  class="org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy" >
		<property name="targetDataSource">
			<ref bean="dynamicDataSource" />
		</property>
	</bean>

	<!-- 3.配置SqlSessionFactory对象 -->
	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<!-- 注入数据库连接池 -->
		<property name="dataSource" ref="dataSource" />
		<!-- 配置MyBaties全局配置文件:mybatis-config.xml -->
		<property name="configLocation" value="classpath:mybatis-config.xml" />
		<!-- 扫描entity包 使用别名 -->
		<property name="typeAliasesPackage" value="com.shenxf.o2o.entity" />
		<!-- 扫描sql配置文件:mapper需要的xml文件 -->
		<property name="mapperLocations" value="classpath:mapper/*.xml" />
	</bean>
    ```
