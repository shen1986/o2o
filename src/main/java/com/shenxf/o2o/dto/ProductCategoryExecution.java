package com.shenxf.o2o.dto;

import com.shenxf.o2o.entity.ProductCategory;
import com.shenxf.o2o.enums.ProductCategoryStateEnum;

import java.util.List;

public class ProductCategoryExecution {
    // 结果状态
    private int state;

    // 状态标识
    private String stateInfo;

    // 商品类别数量
    private int count;

    // 操作的productCategory(增删改商品类别的时候用到)
    private ProductCategory productCategory;

    // productCategory列表(查询店铺列表的时候使用)
    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution(){}

    // 商品类别操作失败的时候使用的构造器
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 商品类别操作成功的时候使用的构造器
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum, ProductCategory productCategory) {
        this(stateEnum);
        this.productCategory = productCategory;
    }

    // 商品类别操作成功的时候使用的构造器
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum, List<ProductCategory> productCategoryList) {
        this(stateEnum);
        this.productCategoryList = productCategoryList;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }
}
