package cn.jh.dto;

import cn.jh.enums.ProductCategoryStateEnum;
import cn.jh.pojo.ProductCategory;

import java.util.List;

public class ProductCaregoryExecution {

    private int state;
    private String stateInfo;
    private List<ProductCategory> productCategoryList;

    //操作失败的构造器
    public ProductCaregoryExecution(ProductCategoryStateEnum stateEnum) {
        this.state=stateEnum.getState();
        this.stateInfo=stateEnum.getStateInfo();
    }

    //操作成功的构造器
    public ProductCaregoryExecution(ProductCategoryStateEnum stateEnum, List<ProductCategory> productCategoryList) {

        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.productCategoryList = productCategoryList;
    }

    @Override
    public String toString() {
        return "ProductCaregoryExecution{" +
                "state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", productCategoryList=" + productCategoryList +
                '}';
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

    public List<ProductCategory> getProductCategoryList() {
        return productCategoryList;
    }

    public void setProductCategoryList(List<ProductCategory> productCategoryList) {
        this.productCategoryList = productCategoryList;
    }


}
