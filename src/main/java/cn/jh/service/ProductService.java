package cn.jh.service;

import cn.jh.dto.ImageHolder;
import cn.jh.dto.ProductExecution;
import cn.jh.exception.ProductOperationException;
import cn.jh.pojo.Product;

import java.io.InputStream;
import java.util.List;

public interface ProductService {


    /**
     *
     * @param product
     * @param thumbnail 产品缩略图
     * @param thumbnailList 产品详情图列表
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder>thumbnailList)throws ProductOperationException;

    Product getProductById(long productId);

    ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
                                   List<ImageHolder> productImgs) throws ProductOperationException;

    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);
}
