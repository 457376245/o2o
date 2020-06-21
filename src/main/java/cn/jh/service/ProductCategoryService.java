package cn.jh.service;

import cn.jh.pojo.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> queryProductCategoryById(@Param("shopId") Long shopId);
}
