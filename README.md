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
- 代码层面的读写分离实现

