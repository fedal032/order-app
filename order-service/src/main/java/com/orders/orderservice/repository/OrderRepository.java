package com.orders.orderservice.repository;

import com.orders.orderservice.dto.PurchasedItemsInfoDto;
import com.orders.orderservice.dto.TotalItemsInfoDto;
import com.orders.orderservice.entity.Customer;
import com.orders.orderservice.entity.OrderStatus;
import com.orders.orderservice.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long>
{
    List<Orders> findAllByCustomerAndStatus(Customer customer, OrderStatus status);

    @Query (value = " select cat.code as category, sum(od.amount * od.price) as totalAmount " +
            " from orders o" +
            "         join order_detail od on o.id = od.order_id " +
            "         join customer c on c.id = o.customer_id " +
            "         join category cat on cat.id = od.category_id " +
            " where c.code = :customer " +
            "   and o.status = :#{#status.name()}" +
            " group by c.id, cat.id", nativeQuery = true)
    List<TotalItemsInfoDto> retrieveTotalPurchasedItemsByCustomer(@Param("customer") String customer, @Param("status") OrderStatus status);

    @Query (value = " select cat.code as category, sum(od.amount * od.price) as totalPrice " +
            " from orders o" +
            "         join order_detail od on o.id = od.order_id " +
            "         join customer c on c.id = o.customer_id " +
            "         join category cat on cat.id = od.category_id " +
            " where c.code = :customer " +
            "   and o.status != :#{#status.name()} " +
            " group by c.id, cat.id", nativeQuery = true)
    List<PurchasedItemsInfoDto> retrieveTotalAmountPerCategoryByCustomer(@Param("customer") String customer, @Param("status") OrderStatus status);

    Orders findFirstByCode(String code);
}
