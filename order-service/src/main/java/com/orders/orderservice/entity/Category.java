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

@Entity(name = "category")
@Getter
@Setter
public class Category {

  @GeneratedValue(generator = "category_generator")
  @GenericGenerator(
          name = "category_generator",
          parameters = {
                  @Parameter(name = "sequence_name", value = "category_seq"),
                  @Parameter(name = "increment_size", value = "1")
          }
  )
  @Id
  @Column(name = "id")
  private long id;

  @Column(name = "code")
  private String code;

  @Column(name = "description")
  private String description;

}
