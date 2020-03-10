package com.shenxf.o2o.dto;

import com.shenxf.o2o.entity.Product;
import com.shenxf.o2o.enums.ProductStateEnum;

import java.util.List;

public class ProductExecution {
    // 结果状态
    private int state;

    // 状态标识
    private String stateInfo;

    // 商品数量
    private int count;

    // 操作的Product(增删改商品的时候用到)
    private Product product;

    // product列表(查询商品列表的时候使用)
    private List<Product> productList;

    public ProductExecution(){}

    // 商品操作失败的时候使用的构造器
    public ProductExecution(ProductStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    // 商品操作成功的时候使用的构造器
    public ProductExecution(ProductStateEnum stateEnum, Product product) {
        this(stateEnum);
        this.product = product;
    }

    // 商品操作成功的时候使用的构造器
    public ProductExecution(ProductStateEnum stateEnum, List<Product> productList) {
        this(stateEnum);
        this.productList = productList;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
