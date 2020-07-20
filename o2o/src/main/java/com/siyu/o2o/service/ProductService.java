package com.siyu.o2o.service;

import com.siyu.o2o.dto.ImageHolder;
import com.siyu.o2o.dto.ProductExecution;
import com.siyu.o2o.entity.Product;
import com.siyu.o2o.exceptions.ProductOperationException;
import java.util.List;

public interface ProductService {

   ProductExecution getProductList(Product var1, int var2, int var3);

   Product getProductById(long var1);

   ProductExecution addProduct(Product var1, ImageHolder var2, List var3) throws ProductOperationException;

   ProductExecution modifyProduct(Product var1, ImageHolder var2, List var3) throws ProductOperationException;
}
