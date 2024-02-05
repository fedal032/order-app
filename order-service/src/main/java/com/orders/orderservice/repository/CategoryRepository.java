package com.orders.orderservice.repository;

import com.orders.orderservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>
{
}
