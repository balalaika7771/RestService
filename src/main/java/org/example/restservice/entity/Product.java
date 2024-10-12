package org.example.restservice.entity;

import base.abstractions.Identifiable;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity(name = "product")
@Setter
@Getter
@NoArgsConstructor
public class Product extends Identifiable<Product> {

  @NotNull
  private String name;


}