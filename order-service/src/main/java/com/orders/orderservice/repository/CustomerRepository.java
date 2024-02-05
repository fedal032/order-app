package com.orders.orderservice.repository;

import com.orders.orderservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>
{
    Customer findByCode(String code);

}
