package com.retail.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.retail.entity.Bill;
import com.retail.entity.Customer;
import com.retail.entity.ProductLine;
import com.retail.entity.model.ProductType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
	
	private Integer id;
	private String name;
	private ProductType productType;
	private List<Bill> bills = new ArrayList<>();
	private BigDecimal amountPerLine;
    
	
	public ProductDTO(Integer id, String name, ProductType productType, BigDecimal amountPerLine) {
		super();
		this.id = id;
		this.name = name;
		this.productType = productType;
		this.amountPerLine = amountPerLine;
	}

}
