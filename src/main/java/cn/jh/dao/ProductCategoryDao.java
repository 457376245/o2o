package cn.jh.dao;

import cn.jh.pojo.ProductCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductCategoryDao {
    List<ProductCategory> queryProductCategoryById(@Param("shopId") Long shopId);
}
