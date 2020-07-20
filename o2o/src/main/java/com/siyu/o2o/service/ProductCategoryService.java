package com.siyu.o2o.service;

import com.siyu.o2o.dto.ProductCategoryExecution;
import com.siyu.o2o.exceptions.ProductCategoryOperationException;
import java.util.List;

public interface ProductCategoryService {

   List getProductCategoryList(long var1);

   ProductCategoryExecution batchAddProductCategory(List var1) throws ProductCategoryOperationException;

   ProductCategoryExecution deleteProductCategory(long var1, long var3) throws ProductCategoryOperationException;
}
