package co.oudom.ecommerce.features.order;

import co.oudom.ecommerce.features.order.dto.OrderRequest;
import co.oudom.ecommerce.features.order.dto.OrderResponse;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

    OrderResponse createNew(OrderRequest orderRequest);

}
