package cn.jh.dao;

import cn.jh.pojo.ProductCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductCategoryTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Test
    public void testQueryProductCategoryById(){
        List<ProductCategory> productCategories = productCategoryDao.queryProductCategoryList(7l);
        for (ProductCategory productCategory:productCategories){
            System.out.println(productCategory);
        }
    }

    @Test
    public void testBatchInsertProductCategory(){
        List<ProductCategory> productCategoryList=new ArrayList<ProductCategory>();
        productCategoryList.add(new ProductCategory(7l,"咖啡",1,new Date()));
        productCategoryList.add(new ProductCategory(7l,"牛奶",1,new Date()));

        int res = productCategoryDao.batchInsertProductCategory(productCategoryList);
        System.out.println(res);
    }

    @Test
    public void testDelProductCategory(){
        int res = productCategoryDao.deleteProductCategory(9, 7);
        System.out.println(res);
    }
}
