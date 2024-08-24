package co.oudom.ecommerce.features.order;

import co.oudom.ecommerce.features.order.dto.OrderRequest;
import co.oudom.ecommerce.features.order.dto.OrderResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    OrderResponse createNew(@Valid @RequestBody OrderRequest orderRequest) {
        return orderService.createNew(orderRequest);
    }

}
