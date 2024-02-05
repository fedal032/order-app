package com.orders.orderservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.math.BigDecimal;

@Entity(name = "order_detail")
@Getter @Setter
public class OrderDetail {

  @GeneratedValue(generator = "order_detail_generator")
  @GenericGenerator(
          name = "order_detail_generator",
          parameters = {
                  @Parameter(name = "sequence_name", value = "order_detail_seq"),
                  @Parameter(name = "increment_size", value = "1")
          }
  )
  @Id
  @Column(name = "id")
  private long id;

  @Column(name = "code")
  private String code;

  @OneToOne
  @JoinColumn(name = "category_id", referencedColumnName = "id")
  private Category category;

  @Column(name = "amount")
  private Long amount;

  @Column(name = "price")
  private BigDecimal price;

  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  private Orders order;
}
