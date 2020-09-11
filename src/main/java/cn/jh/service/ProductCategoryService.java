package cn.jh.service;

import cn.jh.dto.ProductCaregoryExecution;
import cn.jh.exception.ProductCategoryOperationException;
import cn.jh.pojo.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> queryProductCategoryList( Long shopId);

    ProductCaregoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException;

    ProductCaregoryExecution deleteProductCategory( long productCategoryId,long shopId) throws ProductCategoryOperationException;

}


