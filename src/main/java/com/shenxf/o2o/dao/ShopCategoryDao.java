package com.shenxf.o2o.dao;

import com.shenxf.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryDao {
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition")
                                         ShopCategory shopCategoryCondition);
}
