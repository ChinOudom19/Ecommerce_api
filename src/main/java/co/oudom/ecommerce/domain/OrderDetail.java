package co.oudom.ecommerce.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "orders_details")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String uuid;

    @Column(nullable = false)
    private Double qty;

    @Column(nullable = false)
    private Double price;

    @ManyToOne
    private ProductItem ProductItem;

    @ManyToOne
    private Order order;

}
