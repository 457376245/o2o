package cn.jh.service.impl;

import cn.jh.dao.ProductDao;
import cn.jh.dao.ProductImgDao;
import cn.jh.dto.ImageHolder;
import cn.jh.dto.ProductExecution;
import cn.jh.enums.ProductStateEnum;
import cn.jh.exception.ProductOperationException;
import cn.jh.pojo.Product;
import cn.jh.pojo.ProductImg;
import cn.jh.service.ProductService;
import cn.jh.util.FileUtil;
import cn.jh.util.ImageUtil;
import cn.jh.util.PageCalulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service

public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;


    @Override
    @Transactional
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
                    throw new ProductOperationException("创建商品失败");
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

    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductByProductId(productId);
    }


    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgs) throws ProductOperationException {
        if (product!=null&&product.getShop()!=null&&product.getShop().getShopId()!=null){
            product.setLastEditTime(new Date());
            //若商品缩略图不为空且原有缩略图不为空，则删除原有缩略图添加新缩略图
            if (thumbnail!=null){
                Product tempProduct = productDao.queryProductByProductId(product.getProductId());
                if (tempProduct.getImgAddr()!=null){
                    FileUtil.deleteFile(tempProduct.getImgAddr());
                }
                addThumbnail(product,thumbnail);
            }
            //若商品详情图不为空且原有详情图不为空，则删除原有详情图添加新详情图
            if(productImgs!=null){
                deleteProductImgList(product.getProductId());
                addProductImgList(product,productImgs);
            }
            try {
                //更新商品信息
                int effectedNum = productDao.updateProduct(product);
                if (effectedNum<=0){
                    throw new ProductOperationException("更新商品信息失败");
                }return new ProductExecution(ProductStateEnum.SUCCESS,product);
            }catch (Exception e){
                throw new ProductOperationException("更新商品信息失败"+e.getMessage());
            }
        }else return new ProductExecution(ProductStateEnum.EMPTY);
    }

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalulator.calculateRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        int count = productDao.queryProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }

    private void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest= FileUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr=ImageUtil.generateThumbnail(thumbnail,dest);
        product.setImgAddr(thumbnailAddr);
    }

    private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
        //获取图片存储路径，这里直接存放到相应店铺的文件夹下
        String dest = FileUtil.getShopImagePath(product.getShop().getShopId());
            List<ProductImg> productImgList = new ArrayList<ProductImg>();
            for (ImageHolder productImgHolder : productImgHolderList) {
                String imgAddr=ImageUtil.generateNormalImg(productImgHolder,dest);
                ProductImg productImg = new ProductImg();
                productImg.setImgAddr(imgAddr);
                productImg.setProductId(product.getProductId());
                productImg.setCreateTime(new Date());
                productImgList.add(productImg);
            }
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("创建商品详情图片失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品详情图片失败:" + e.toString());
            }

    }

    private void deleteProductImgList(long productId){
        List<ProductImg> productImgs = productImgDao.queryProductImgList(productId);
        //删除真实图片文件
        for (ProductImg productImg:productImgs){
            FileUtil.deleteFile(productImg.getImgAddr());
        }
        //删除数据库图片数据
        productImgDao.deleteProductImgByProductId(productId);
    }
}

