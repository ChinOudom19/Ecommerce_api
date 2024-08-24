package co.oudom.ecommerce.features.address;

import co.oudom.ecommerce.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    Optional<Address> findByUuid(String uuid);

}
