package co.oudom.ecommerce.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products_items")
public class ProductItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String uuid;

    @Column(length = 100, nullable = false)
    private Double qty;

    @Column(length = 100, nullable = false)
    private Double discount;

    @Column(nullable = false)
    private Double unitPrice;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Boolean sku;

    @Column(nullable = false)
    private Integer view;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Column(nullable = false)
    private LocalDateTime updateAt;

    @OneToOne
    private Product product;

    @OneToMany(mappedBy = "productItem")
    private List<Review> reviews;

}
