package cn.jh.service;

import cn.jh.dto.ImageHolder;
import cn.jh.dto.ProductExecution;
import cn.jh.exception.ProductOperationException;
import cn.jh.pojo.Product;

import java.io.InputStream;
import java.util.List;

public interface ProductService {


    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder>thumbnailList)throws ProductOperationException;
}
