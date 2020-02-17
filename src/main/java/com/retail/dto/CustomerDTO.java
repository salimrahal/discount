package com.retail.dto;

import java.util.Date;

import com.retail.entity.model.CustomerType;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerDTO {
	
	private Integer id;
	private String name;
	private Date customerSinceDate;
	private CustomerType customerType;
}
