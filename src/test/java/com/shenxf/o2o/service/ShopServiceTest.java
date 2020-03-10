package com.shenxf.o2o.service;

import com.shenxf.o2o.BaseTest;
import com.shenxf.o2o.dto.ShopExecution;
import com.shenxf.o2o.entity.Area;
import com.shenxf.o2o.entity.PersonInfo;
import com.shenxf.o2o.entity.Shop;
import com.shenxf.o2o.entity.ShopCategory;
import com.shenxf.o2o.enums.ShopStateEnum;
import com.shenxf.o2o.exceptions.ShopOperationException;
import com.shenxf.o2o.utils.FileUtil;
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
        FileItem fileItem = FileUtil.createFileItem(shopImg, "表单字段名");
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
        FileItem fileItem = FileUtil.createFileItem(shopImg, "表单字段名");
        CommonsMultipartFile xxCMF = new CommonsMultipartFile(fileItem);
        ShopExecution se = shopService.addShop(shop, xxCMF);

        assertEquals(ShopStateEnum.CHECK.getState(), se.getState());
    }


}
