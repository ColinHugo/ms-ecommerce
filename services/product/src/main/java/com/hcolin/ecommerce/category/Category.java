package com.hcolin.ecommerce.category;

import com.hcolin.ecommerce.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Category {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String description;

    @OneToMany( mappedBy = "category", cascade = CascadeType.REMOVE )
    private List< Product > products;

}