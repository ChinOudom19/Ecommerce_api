package co.oudom.ecommerce.domain;

import co.oudom.ecommerce.features.address.dto.AddressResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String uuid;

    @Column(length = 10,unique = true, nullable = false)
    private String phoneNumber;

    @Column(length = 50, nullable = false)
    private String street;

    @Column(length = 50, nullable = false)
    private String village;

    @Column(length = 50, nullable = false)
    private String communeOrSangKat;

    @Column(length = 50, nullable = false)
    private String districtOrKhan;

    @Column(length = 50, nullable = false)
    private String provinceOrCity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "users_address",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "address_id", referencedColumnName = "id"))
    private User user;

}
