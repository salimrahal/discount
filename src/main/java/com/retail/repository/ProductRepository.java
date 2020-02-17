package com.retail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.retail.entity.ProductLine;


@Repository
public interface ProductRepository extends JpaRepository<ProductLine, Integer>{

}
