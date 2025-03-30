package com.praj.test.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_command")
public class Product {
    @Id
    @JsonProperty("pId")
    private String pId;

    @JsonProperty("pName")
    private String pName;

    @JsonProperty("pDescription")
    private String pDescription;

    @JsonProperty("pPrice")
    private double pPrice;

}
