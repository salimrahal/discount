package com.retail.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NetPaymentDTO {
	public Double netPayable ;
	public Double totalPayable;

}
