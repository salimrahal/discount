package com.retail.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.retail.dto.ProductDTO;
import com.retail.entity.ProductLine;
import com.retail.repository.ProductRepository;

public class ProductService {

	@Autowired
    private ProductRepository repository; 
    
	public void saveProduct(ProductDTO prod) {
		ProductLine product = new ProductLine(prod.getName(),prod.getProductType(), prod.getAmountPerLine());
		repository.save(product);
	}
	
}
