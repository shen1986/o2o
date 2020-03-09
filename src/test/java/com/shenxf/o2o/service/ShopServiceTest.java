package com.shenxf.o2o.service;

import com.shenxf.o2o.BaseTest;
import com.shenxf.o2o.dto.ShopExecution;
import com.shenxf.o2o.entity.Area;
import com.shenxf.o2o.entity.PersonInfo;
import com.shenxf.o2o.entity.Shop;
import com.shenxf.o2o.entity.ShopCategory;
import com.shenxf.o2o.enums.ShopStateEnum;
import com.shenxf.o2o.exceptions.ShopOperationException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void testgetShopList() {
        Shop shopCondition = new Shop();
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(14L);
        shopCondition.setShopCategory(sc);
        ShopExecution se = shopService.getShopList(shopCondition, 0 , 2);
        System.out.println("店铺列表数为：" + se.getShopList().size());
        System.out.println("店铺总数为：" + se.getCount());
    }

    @Test
    @Ignore
    public void testModifyShop() throws ShopOperationException, FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(30L);
        shop.setShopName("修改后的店铺名称");
        File shopImg = new File("E:\\work\\Java\\pinzhi.png");
        //使用
        FileItem fileItem = createFileItem(shopImg, "表单字段名");
        CommonsMultipartFile xxCMF = new CommonsMultipartFile(fileItem);

        ShopExecution se = shopService.modifyShop(shop, xxCMF);
        System.out.println("新的图片地址为：" + se.getShop().getShopImg());
    }


    @Test
    @Ignore
    public void testAddShop() {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(8L);
        area.setAreaId(3);
        shopCategory.setShopCategoryId(10L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺1");
        shop.setShopDesc("test1");
        shop.setShopAddr("test1");
        shop.setPhone("test1");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg = new File("E:\\work\\Java\\Capture001.png");

        //使用
        FileItem fileItem = createFileItem(shopImg, "表单字段名");
        CommonsMultipartFile xxCMF = new CommonsMultipartFile(fileItem);
        ShopExecution se = shopService.addShop(shop, xxCMF);

        assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
    }

    //把File转化为CommonsMultipartFile
    public FileItem createFileItem(File file, String fieldName) {
        //DiskFileItemFactory()：构造一个配置好的该类的实例
        //第一个参数threshold(阈值)：以字节为单位.在该阈值之下的item会被存储在内存中，在该阈值之上的item会被当做文件存储
        //第二个参数data repository：将在其中创建文件的目录.用于配置在创建文件项目时，当文件项目大于临界值时使用的临时文件夹，默认采用系统默认的临时文件路径
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        //fieldName：表单字段的名称；第二个参数 ContentType；第三个参数isFormField；第四个：文件名
        FileItem item = factory.createItem(fieldName, "text/plain", true, file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        FileInputStream fis = null;
        OutputStream os = null;
        try {
            fis = new FileInputStream(file);
            os = item.getOutputStream();
            while((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);//从buffer中得到数据进行写操作
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(os != null) {
                    os.close();
                }
                if(fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return item;
    }
}
