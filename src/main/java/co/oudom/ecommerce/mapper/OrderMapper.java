package co.oudom.ecommerce.mapper;

import co.oudom.ecommerce.domain.Order;
import co.oudom.ecommerce.features.order.dto.OrderProductResponse;
import co.oudom.ecommerce.features.order.dto.OrderRequest;
import co.oudom.ecommerce.features.order.dto.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    // Map order and order details to OrderResponse
    @Mapping(source = "orderProductResponses", target = "orderProductList")
    OrderResponse toOrderResponse(Order order, List<OrderProductResponse> orderProductResponses);

}
