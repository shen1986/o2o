package com.shenxf.o2o.service;

import com.shenxf.o2o.BaseTest;
import com.shenxf.o2o.dto.ProductExecution;
import com.shenxf.o2o.entity.Product;
import com.shenxf.o2o.entity.ProductCategory;
import com.shenxf.o2o.entity.Shop;
import com.shenxf.o2o.enums.ProductStateEnum;
import com.shenxf.o2o.utils.FileUtil;
import org.apache.commons.fileupload.FileItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;

    @Test
    public void testAddProduct() {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(30L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(10L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品1");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());
        // 创建缩略图文件流
        File thumbnailFile = new File("E:\\work\\Java\\pinzhi.png");
        FileItem fileItem = FileUtil.createFileItem(thumbnailFile, "表单字段名");
        CommonsMultipartFile thumbnailFileCMF1 = new CommonsMultipartFile(fileItem);
        // 创建2个商品详细图文件流并将他们添加到详情图列表中
        File productImg1 = new File("E:\\work\\Java\\pinzhi.png");
        FileItem fileItem1 = FileUtil.createFileItem(productImg1, "表单字段名");
        CommonsMultipartFile productImgCMF1 = new CommonsMultipartFile(fileItem1);
        File productImg2 = new File("E:\\work\\Java\\Capture001.png");
        FileItem fileItem2 = FileUtil.createFileItem(productImg2, "表单字段名");
        CommonsMultipartFile productImgCMF2 = new CommonsMultipartFile(fileItem2);
        List<CommonsMultipartFile> productImgList = new ArrayList<>();
        productImgList.add(productImgCMF1);
        productImgList.add(productImgCMF2);
        // 添加商品并验证
        ProductExecution pe = productService.addProduct(product, thumbnailFileCMF1, productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
    }
}
