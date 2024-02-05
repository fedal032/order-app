package com.orders.orderservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.sql.Date;
import java.util.List;

@Entity(name = "orders")
@Getter
@Setter
public class Orders
{
    @GeneratedValue(generator = "orders_generator")
    @GenericGenerator(
            name = "orders_generator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "orders_seq"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "code")
    private String code;

    @Column (name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;
}
