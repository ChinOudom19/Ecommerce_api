package co.oudom.ecommerce.features.user;

import co.oudom.ecommerce.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository  extends JpaRepository<Role, Integer> {

    // JPQL = Jakarta Persistent Query Language
    @Query("SELECT r FROM Role AS r WHERE r.name = 'USER'")
    Role findRolesUser();

    @Query("SELECT r FROM Role AS r WHERE r.name = 'CUSTOMER'")
    Role findRolesCustomer();

}
