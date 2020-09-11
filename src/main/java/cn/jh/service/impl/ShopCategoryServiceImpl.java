package cn.jh.service.impl;

import cn.jh.dao.ShopCategoryDao;
import cn.jh.pojo.ShopCategory;
import cn.jh.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryDao shopCategory;
    @Override
    public List<ShopCategory> queryShopCategoryList(ShopCategory shopCategoryCondition) {
        return shopCategory.queryShopCategory(shopCategoryCondition);
    }
}
