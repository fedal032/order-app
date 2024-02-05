package com.client.orders.repository;

import com.client.orders.entity.CustomerStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerStatisticRepository extends JpaRepository<CustomerStatistic, Long>
{
    CustomerStatistic findCustomerStatisticByCustomerCode(String customerCode);
}
