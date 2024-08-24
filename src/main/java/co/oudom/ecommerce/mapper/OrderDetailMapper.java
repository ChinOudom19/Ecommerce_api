package co.oudom.ecommerce.mapper;
import co.oudom.ecommerce.domain.OrderDetail;
import co.oudom.ecommerce.features.order.dto.OrderProductResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {

    // Map a single OrderDetail to OrderProductResponse
    OrderProductResponse toOrderProductResponse(OrderDetail orderDetail);

    // Map list of OrderDetails to list of OrderProductResponse
    List<OrderProductResponse> toOrderProductResponseList(List<OrderDetail> orderDetails);
}
