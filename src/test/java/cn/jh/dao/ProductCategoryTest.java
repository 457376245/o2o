package cn.jh.dao;

import cn.jh.pojo.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductCategoryTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Test
    public void testQueryProductCategoryById(){
        List<ProductCategory> productCategories = productCategoryDao.queryProductCategoryById(21l);
        for (ProductCategory productCategory:productCategories){
            System.out.println(productCategory);
        }
    }
}
