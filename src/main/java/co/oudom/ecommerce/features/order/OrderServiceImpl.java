package co.oudom.ecommerce.features.order;

import co.oudom.ecommerce.domain.*;
import co.oudom.ecommerce.features.address.AddressRepository;
import co.oudom.ecommerce.features.order.dto.OrderProductRequest;
import co.oudom.ecommerce.features.order.dto.OrderRequest;
import co.oudom.ecommerce.features.order.dto.OrderResponse;
import co.oudom.ecommerce.features.productitem.ProductItemRepository;
import co.oudom.ecommerce.features.user.UserRepository;
import co.oudom.ecommerce.mapper.OrderDetailMapper;
import co.oudom.ecommerce.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final ProductItemRepository productItemRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailMapper orderDetailMapper;

    @Override
    public OrderResponse createNew(OrderRequest orderRequest) {

        // Fetch user and address
        User user = userRepository.findByUuid(orderRequest.userUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Address address = addressRepository.findByUuid(orderRequest.addressUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        BigDecimal totalPrice = BigDecimal.ZERO;

        // Calculate total price for all products
        for (OrderProductRequest orderProductRequest : orderRequest.OrderProductList()) {
            ProductItem productItem = productItemRepository
                    .findByUuid(orderProductRequest.productItemUuid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product item not found"));
            totalPrice = totalPrice.add(BigDecimal.valueOf(productItem.getPrice() * orderProductRequest.qty()));
        }

        // Initialize and save order
        Order order = new Order();
        order.setUuid(UUID.randomUUID().toString());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PAID);
        order.setUser(user);
        order.setAddress(address);
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        // Save order details
        for (OrderProductRequest orderProductRequest : orderRequest.OrderProductList()) {
            ProductItem productItem = productItemRepository
                    .findByUuid(orderProductRequest.productItemUuid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product item not found"));

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setUuid(UUID.randomUUID().toString());
            orderDetail.setQty(orderProductRequest.qty());
            orderDetail.setProductItem(productItem);
            orderDetail.setOrder(order);
            orderDetail.setPrice(productItem.getPrice() * orderDetail.getQty());

            log.info("Order Detail: {}", orderDetail);
            orderDetailRepository.save(orderDetail);
        }

        // Retrieve order details and map to response
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrder(order);

        // Use OrderMapper to map Order and OrderDetails to OrderResponse
        return orderMapper.toOrderResponse(order,orderDetailMapper.toOrderProductResponseList(orderDetails));

    }
}
