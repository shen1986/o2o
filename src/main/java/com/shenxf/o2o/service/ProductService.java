package com.shenxf.o2o.service;

import com.shenxf.o2o.dto.ProductExecution;
import com.shenxf.o2o.entity.Product;
import com.shenxf.o2o.exceptions.ProductOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface ProductService {
    /**
     * 根据条件取得商品信息
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);

    /**
     *
     * @param productId
     * @return
     */
    Product getProductById(long productId);

    /**
     * 添加商品及图片处理
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product,
                                CommonsMultipartFile productImg,
                                List<CommonsMultipartFile> productImgList)
            throws ProductOperationException;

    /**
     * 修改商品以及图片处理
     * @param product
     * @param thumbnail
     * @param productImgs
     * @return
     * @throws RuntimeException
     */
    ProductExecution modifyProduct(Product product, CommonsMultipartFile thumbnail,
                                   List<CommonsMultipartFile> productImgs) throws RuntimeException;
}
