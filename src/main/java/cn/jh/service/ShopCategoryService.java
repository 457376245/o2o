package cn.jh.service;

import cn.jh.pojo.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryService {
    List<ShopCategory> queryShopCategoryList( ShopCategory shopCategoryCondition);

}
