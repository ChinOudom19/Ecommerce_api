package co.oudom.ecommerce.features.product;

import co.oudom.ecommerce.domain.Product;
import co.oudom.ecommerce.features.product.dto.ProductRequest;
import co.oudom.ecommerce.features.product.dto.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    void deleteProduct(@PathVariable("uuid") String uuid) {
        productService.deleteByUuid(uuid);
    }


    @PutMapping("/{uuid}")
    ProductResponse updateByUuid(@PathVariable("uuid") String uuid,@Valid @RequestBody ProductRequest productRequest) {
        return productService.updateByUuid(uuid, productRequest);
    }

    @GetMapping("/{uuid}")
    ProductResponse findByUuid(@PathVariable("uuid") String uuid) {return productService.findByUuid(uuid);}

    @GetMapping
    List<ProductResponse> findList() {
        return productService.findList();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    ProductResponse createProduct(@Valid @RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

}
