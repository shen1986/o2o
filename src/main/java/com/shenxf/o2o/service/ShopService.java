package com.shenxf.o2o.service;

import com.shenxf.o2o.dto.ShopExecution;
import com.shenxf.o2o.entity.Shop;
import com.shenxf.o2o.exceptions.ShopOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;

public interface ShopService {

    /**
     * 根据ShopCondition分页返回相应店铺列表
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);

    /**
     * 通过店铺Id获取店铺信息
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId);

    /**
     * 更新店铺信息，包括对图片的处理
     * @param shop
     * @param shopImg
     * @return
     * @throws ShopOperationException
     */
    ShopExecution modifyShop(Shop shop, CommonsMultipartFile shopImg)throws ShopOperationException;

    /**
     * 创建商铺
     *
     * @param Shop
     *            shop
     * @return ShopExecution shopExecution
     * @throws Exception
     */
    ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) throws ShopOperationException;
}
