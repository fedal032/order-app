package com.orders.orderservice.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.sql.Date;

@Entity(name = "customer")
@Getter
@Setter
public class Customer {

  @GeneratedValue(generator = "customer_generator")
  @GenericGenerator(
          name = "customer_generator",
          parameters = {
                  @Parameter(name = "sequence_name", value = "customer_seq"),
                  @Parameter(name = "increment_size", value = "1")
          }
  )
  @Id
  @Column(name = "id")
  private long id;

  @Column(name = "code")
  private String code;

  @Column(name = "name")
  private String name;

  @Column(name = "created_date")
  private Date createdDate;
}
