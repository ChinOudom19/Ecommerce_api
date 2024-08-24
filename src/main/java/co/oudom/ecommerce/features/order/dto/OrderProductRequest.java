package co.oudom.ecommerce.features.order.dto;

public record OrderProductRequest(

        String productItemUuid,

        Double qty
) {
}
