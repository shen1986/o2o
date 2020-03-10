package com.shenxf.o2o.service;

import com.shenxf.o2o.dto.ProductCategoryExecution;
import com.shenxf.o2o.entity.ProductCategory;
import com.shenxf.o2o.exceptions.ProductCategoryException;

import java.util.List;

public interface ProductCategoryService {
    /**
     * 查询指定某个商品类别下所有商品类别信息
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(long shopId);

    /**
     * 批量添加商品类别
     * @param productCategoryList
     * @return
     * @throws ProductCategoryException
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryException;

    /**
     * 删除商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryException
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryException;
}
