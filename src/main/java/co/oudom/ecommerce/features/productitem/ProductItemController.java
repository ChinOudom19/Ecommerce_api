package co.oudom.ecommerce.features.productitem;

import co.oudom.ecommerce.domain.Category;
import co.oudom.ecommerce.features.productitem.dto.ProductItemRequest;
import co.oudom.ecommerce.features.productitem.dto.ProductItemResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product-item")
@RequiredArgsConstructor
public class ProductItemController {

    private final ProductItemService productItemService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    void deleteByUuid(@PathVariable("uuid") String uuid) {
        productItemService.deleteByUuid(uuid);
    }

    @PatchMapping("/{uuid}")
    ProductItemResponse updateStock(@PathVariable("uuid") String uuid, @Valid @RequestBody ProductItemRequest productItemRequest){
        return productItemService.updateStock(uuid, productItemRequest);
    }

    @PutMapping("/{uuid}")
    ProductItemResponse updateByUuid(@PathVariable("uuid") String uuid, @RequestBody @Valid ProductItemRequest productItemRequest) {
        return productItemService.updateByUuid(uuid, productItemRequest);
    }

    @GetMapping("/{uuid}")
    ProductItemResponse findByUuid(@PathVariable("uuid") String uuid) {
        return productItemService.findByUuid(uuid);
    }

    @GetMapping("/category/{uuid}")
    List<ProductItemResponse> findByCategoryUuid(@PathVariable("uuid") String uuid) {
        return productItemService.findListByCategoryUuid(uuid);
    }

    @GetMapping
    Page<ProductItemResponse> findList(
        @RequestParam(required = false, defaultValue = "0") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize
    ) {
        return productItemService.findList(pageNumber, pageSize);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ProductItemResponse createNew(@RequestBody ProductItemRequest productItemRequest) {
        return productItemService.createNew(productItemRequest);
    }
}
