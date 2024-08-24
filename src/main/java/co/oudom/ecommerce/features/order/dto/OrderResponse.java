package co.oudom.ecommerce.features.order.dto;

import co.oudom.ecommerce.domain.OrderStatus;
import co.oudom.ecommerce.features.address.dto.AddressResponse;
import co.oudom.ecommerce.features.user.dto.UserResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(

        String uuid,

        Double totalPrice,

        OrderStatus status,

        LocalDateTime orderDate,

        AddressResponse address,

        UserResponse user,

        List<OrderProductResponse> orderProductList

) {
}
