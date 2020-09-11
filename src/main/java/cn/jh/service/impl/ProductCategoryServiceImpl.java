package cn.jh.service.impl;

import cn.jh.dao.ProductCategoryDao;
import cn.jh.dto.ProductCaregoryExecution;
import cn.jh.enums.ProductCategoryStateEnum;
import cn.jh.exception.ProductCategoryOperationException;
import cn.jh.pojo.ProductCategory;
import cn.jh.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public List<ProductCategory> queryProductCategoryList(Long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Override
    public ProductCaregoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        if (productCategoryList.size()>0&&productCategoryList!=null){
            try {
                for (ProductCategory productCategory:productCategoryList){
                    productCategory.setCreateTime(new Date());
                }
                int res = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if (res<=0){
                    throw new ProductCategoryOperationException("创建商品类别失败");
                }
                else {
                    return new ProductCaregoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            }
            catch (Exception e){
                throw new ProductCategoryOperationException("创建商品类别失败:"+e.getMessage());
            }
        }else {
            return new ProductCaregoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }
    }

    @Override
    @Transactional
    public ProductCaregoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException {
        try{
            int res = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (res>0){
                return new ProductCaregoryExecution(ProductCategoryStateEnum.SUCCESS);
            }else {
                throw new ProductCategoryOperationException("创建商品删除失败");
            }

        }catch (Exception e){
            throw new ProductCategoryOperationException("创建商品删除失败:"+e.getMessage());
        }
    }
}
