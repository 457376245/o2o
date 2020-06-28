package cn.jh.service.impl;

import cn.jh.dao.ProductDao;
import cn.jh.dao.ProductImgDao;
import cn.jh.dto.ImageHolder;
import cn.jh.dto.ProductExecution;
import cn.jh.enums.ProductStateEnum;
import cn.jh.exception.ProductOperationException;
import cn.jh.pojo.Product;
import cn.jh.service.ProductService;
import cn.jh.util.FileUtil;
import cn.jh.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;


    @Override
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException {
        if (product!=null&&product.getShop()!=null&&product.getShop().getShopId()!=null){
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            if (thumbnail!=null){
                addThumbnail(product,thumbnail);
            }
            try{
                int effectedNub=productDao.insertProduct(product);
                if (effectedNub<0){
                    throw new ProductOperationException("创建商品失败")
                }
            }catch (Exception e){
                throw new ProductOperationException("创建商品失败"+e.toString());
            }
            if (productImgHolderList!=null&&productImgHolderList.size()>0){
                addProductImgList(product,productImgHolderList);
            }return new ProductExecution(ProductStateEnum.SUCCESS,product);
        }
        return new ProductExecution(ProductStateEnum.EMPTY);
    }

    private void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest= FileUtil.getShopImagePath(product.getShop().getShopId());
    }
}
