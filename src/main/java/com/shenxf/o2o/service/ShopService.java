package com.shenxf.o2o.service;

import com.shenxf.o2o.dto.ShopExecution;
import com.shenxf.o2o.entity.Shop;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;

public interface ShopService {

    /**
     * 创建商铺
     *
     * @param Shop
     *            shop
     * @return ShopExecution shopExecution
     * @throws Exception
     */
    ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg) throws RuntimeException;
}
