package com.shenxf.o2o.service.impl;

import com.shenxf.o2o.dao.ProductDao;
import com.shenxf.o2o.dao.ProductImgDao;
import com.shenxf.o2o.dto.ProductExecution;
import com.shenxf.o2o.entity.Product;
import com.shenxf.o2o.entity.ProductImg;
import com.shenxf.o2o.enums.ProductStateEnum;
import com.shenxf.o2o.exceptions.ProductOperationException;
import com.shenxf.o2o.service.ProductService;
import com.shenxf.o2o.util.ImageUtil;
import com.shenxf.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    /*
     * 1. 处理缩略图，获取缩略图相对路径并赋值给product
     * 2. 往tb_product写入商品信息，获取productId
     * 3. 结合productId批量处理商品详情图
     * 4. 将商品详情图列表批量插入tb_product_img中
     */
    @Override
    @Transactional
    public ProductExecution addProduct(Product product, CommonsMultipartFile thumbnail, List<CommonsMultipartFile> productImgList)
            throws ProductOperationException {
        // 空值判断
        if (product != null && product.getShop()!= null && product.getShop().getShopId() != null) {
            // 给商品设置默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            // 默认为上架状态
            product.setEnableStatus(1);
            // 若商品缩略图不为空则添加
            if (thumbnail != null) {
                addThumbnail(product, thumbnail);
            }
            try {
                // 创建商品信息
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("创建商品失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品失败" + e.getMessage());
            }
            // 若商品详情图不为空则添加
            if (productImgList != null && productImgList.size() > 0) {
                addProductImgList(product, productImgList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            // 传参为空则返回空值错误
            return  new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    /**
     * 添加缩略图
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, CommonsMultipartFile thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }

    /**
     * 添加详细页图片
     * @param product
     * @param productImgList
     */
    private void addProductImgList(Product product, List<CommonsMultipartFile> productImgList) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> tempProductImgList = new ArrayList<>();
        for (CommonsMultipartFile productImgFile:productImgList) {
            String imgAddr = ImageUtil.generateThumbnail(productImgFile, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            tempProductImgList.add(productImg);
        }
        // 如果确实是有图片需要添加的，就执行批量添加操作
        if (tempProductImgList.size() > 0) {
            try {
                int effectedNum = productImgDao.batchInsertProductImg(tempProductImgList);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("创建商品详情图片失败");
                }
            } catch (Exception e) {
                throw  new ProductOperationException("创建商品详情图片失败" + e.getMessage());
            }
        }
    }
}
