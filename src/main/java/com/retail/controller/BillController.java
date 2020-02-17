package com.retail.controller;

import java.util.List;

import javax.persistence.metamodel.StaticMetamodel;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.retail.dto.BillDTO;
import com.retail.dto.CustomerDTO;
import com.retail.dto.NetPaymentDTO;
import com.retail.service.BillService;
import com.retail.service.CustomerService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BillController {

	BillService billService;
	CustomerService customerService;

	public BillController(BillService billService, CustomerService customerService) {
		super();
		this.billService = billService;
		this.customerService = customerService;
	}

	/*
	 * compute net payable
	 */
	@ApiOperation(notes = "Compute Net Payable.",
            value = "Compute Net Payable",
            nickname = "getById",
            tags = {"customers"})
	@PostMapping("/computenetpayable")
	public ResponseEntity<NetPaymentDTO> computeNetPayable(@RequestBody BillDTO billDTO) throws NotFoundException {
		log.info("processing payment discount Bill ID {}", billDTO.getId());
		NetPaymentDTO respDTO = billService.computeNetPayable(billDTO);
		ResponseEntity<NetPaymentDTO> res = new ResponseEntity<NetPaymentDTO>(respDTO, HttpStatus.OK);
		return res;
	}

	/*
	 * get all customers
	 */
	@ApiOperation(notes = "Returns  list of all customers.", value = "Get a list of all customers.", nickname = "getAll", tags = {
			"customers" })
	@GetMapping("/customers")
	public ResponseEntity<List<CustomerDTO>> getAll() {
		log.info("processing getAll customers");
		List<CustomerDTO> res = customerService.findAll();
		ResponseEntity<List<CustomerDTO>> resp = new ResponseEntity<List<CustomerDTO>>(res, HttpStatus.OK);
		return resp;
	}

	@ApiOperation(notes = "Returns a Customer.", value = "Get Customer", nickname = "getById", tags = { "customers" })
	@GetMapping("/customer")
	public ResponseEntity<CustomerDTO> getById(@RequestParam Integer id) throws NotFoundException {
		log.info("processing get customer");
		CustomerDTO res = null;
		res = customerService.findById(id);
		ResponseEntity<CustomerDTO> resp = new ResponseEntity<CustomerDTO>(res, HttpStatus.OK);
		return resp;
	}

}
