package cn.jh.service.impl;

import cn.jh.dao.ProductCategoryDao;
import cn.jh.pojo.ProductCategory;
import cn.jh.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public List<ProductCategory> queryProductCategoryById(Long shopId) {
        return productCategoryDao.queryProductCategoryById(shopId);
    }
}
