package cn.jh.dao;

import cn.jh.pojo.ProductCategory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductCategoryDao {
    List<ProductCategory> queryProductCategoryList(@Param("shopId") Long shopId);
    /**
     * 新增商品类别
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    int deleteProductCategory(@Param("productCategoryId") long productCategoryId,@Param("shopId") long shopId);
}


