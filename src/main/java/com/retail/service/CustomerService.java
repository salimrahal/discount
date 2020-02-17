package com.retail.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.retail.dto.CustomerDTO;
import com.retail.entity.Customer;
import com.retail.repository.CustomerRepository;


@Service
public class CustomerService {
	
	private final CustomerRepository customerRepository;
	
	@Autowired
    public CustomerService(CustomerRepository customerRepository)
    {

        this.customerRepository = customerRepository;
    }
	
	/**
	 * get all customers
	 * @return
	 */
	 public List<CustomerDTO> findAll()
	    {
		 return this.customerRepository.findAll().stream().
		 map(c -> CustomerDTO.builder().id(c.getId()).name(c.getName()).
				 customerSinceDate(c.getCustomerSinceDate()).
				 customerType(c.getCustomerType()).build()).collect(Collectors.toList());
	    }
	 
	 public CustomerDTO findById(Integer id) throws NotFoundException {
		 Customer c = this.customerRepository.findById(id).orElseThrow(()-> new NotFoundException());
		 return CustomerDTO.builder().id(c.getId()).name(c.getName()).
				 customerSinceDate(c.getCustomerSinceDate()).
				 customerType(c.getCustomerType()).build();
	 }
	
	
}


