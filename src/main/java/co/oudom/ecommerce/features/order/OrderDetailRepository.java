package co.oudom.ecommerce.features.order;

import co.oudom.ecommerce.domain.Order;
import co.oudom.ecommerce.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    List<OrderDetail> findByOrder(Order order);
}
