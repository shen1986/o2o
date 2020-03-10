package com.shenxf.o2o.service;

import com.shenxf.o2o.dto.ProductExecution;
import com.shenxf.o2o.entity.Product;
import com.shenxf.o2o.exceptions.ProductOperationException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.List;

public interface ProductService {
    /**
     * 添加商品及图片处理
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product,
                                CommonsMultipartFile productImg,
                                List<CommonsMultipartFile> productImgList)
            throws ProductOperationException;
}
