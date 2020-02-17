package com.retail.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.retail.dto.BillDTO;
import com.retail.dto.CustomerDTO;
import com.retail.dto.NetPaymentDTO;
import com.retail.entity.ProductLine;
import com.retail.entity.model.ProductType;
import com.retail.service.BillService;
import com.retail.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class BillControllerTest {

	private MockMvc mvc;

	@Mock
	private CustomerService customerService;

	private ObjectMapper mapper = new ObjectMapper();
	
	NetPaymentDTO netPaymentDTO;
	
	@Mock
	private BillService billService;

	private List<CustomerDTO> customers = new ArrayList<>();

	@Before
	public void setUp() {

		this.mvc = standaloneSetup(new BillController(billService, customerService)).alwaysExpect(status().isOk())
				.build();

		CustomerDTO c1 = CustomerDTO.builder().name("Charbel").build();
		CustomerDTO c2 = CustomerDTO.builder().name("Salim").build();
		CustomerDTO c3 = CustomerDTO.builder().name("Giselle").build();

		this.customers = Arrays.asList(c1, c2, c3);
        Mockito.when(this.customerService.findAll()).thenReturn(this.customers);
		//mock response
        netPaymentDTO = new NetPaymentDTO(665.0,1000.0);
	}

	@Test
	public void customersEqualSize() throws Exception {
		this.mvc.perform(get("/customers").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(this.customers.size()));
	}

	@Test
    public void whenComputeNetPayableForEmployee() throws Exception
    { 
		List<ProductLine> productLines = new ArrayList<ProductLine>();
		productLines.add(new ProductLine("Tshirt", ProductType.CLOTHES,BigDecimal.valueOf(500)));
		productLines.add(new ProductLine("CAN FOOD", ProductType.OTHER,BigDecimal.valueOf(500)));
		log.info("size line {}", productLines.size());
		BillDTO billDto = new BillDTO(1, "num1", new Date(), 1, productLines);
		String json = mapper.writeValueAsString(billDto);
		
		//mock the response
		when(billService.computeNetPayable((BillDTO) any(BillDTO.class))).thenReturn(netPaymentDTO);
		
		log.info("request {}",json);
		this.mvc.perform(MockMvcRequestBuilders
                .post("/computenetpayable")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.netPayable").value(665.0));
		
    }
}
