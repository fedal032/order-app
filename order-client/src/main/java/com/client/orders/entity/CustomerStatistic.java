package com.client.orders.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.math.BigDecimal;
import java.sql.Date;

@Entity(name = "customer_stat")
@Getter
@Setter
@ToString
public class CustomerStatistic
{

  @GeneratedValue(generator = "customer_stat_generator")
  @GenericGenerator(
          name = "customer_stat_generator",
          parameters = {
                  @Parameter(name = "sequence_name", value = "customer_stat_seq"),
                  @Parameter(name = "increment_size", value = "1")
          }
  )
  @Id
  @Column(name = "id")
  private long id;

  @Column(name = "customer_code")
  private String customerCode;

  @Column(name = "total_amount")
  private BigDecimal totalAmount;

  @Column(name = "modified_date")
  private Date modifiedDate;
}
