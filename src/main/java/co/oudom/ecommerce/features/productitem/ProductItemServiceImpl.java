package co.oudom.ecommerce.features.productitem;

import co.oudom.ecommerce.domain.Category;
import co.oudom.ecommerce.domain.Product;
import co.oudom.ecommerce.domain.ProductItem;
import co.oudom.ecommerce.features.category.CategoryRepository;
import co.oudom.ecommerce.features.product.ProductRepository;
import co.oudom.ecommerce.features.productitem.dto.ProductItemRequest;
import co.oudom.ecommerce.features.productitem.dto.ProductItemResponse;
import co.oudom.ecommerce.mapper.ProductItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductItemServiceImpl implements ProductItemService {

    private final ProductItemRepository productItemRepository;
    private final ProductRepository productRepository;
    private final ProductItemMapper productItemMapper;
    private final CategoryRepository categoryRepository;

    // Product Item Deleted By UUID
    @Override
    public void deleteByUuid(String uuid) {

        // Find the product item by UUID
        ProductItem productItem = productItemRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product item not found"));

        // Find the associated product
        Product product = productItem.getProduct();

        // Delete the product item
        productItemRepository.delete(productItem);

        // Check if there are any remaining product items for the product
        if (product != null) {
            // No more items for this product, so delete the product
            productRepository.delete(product);
        }

    }

    @Override
    public ProductItemResponse updateStock(String uuid, ProductItemRequest productItemRequest) {

        ProductItem productItem = productItemRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product item not found"));

        productItem.setSku(productItemRequest.sku());
        productItem.setUpdateAt(LocalDateTime.now());

        productItemRepository.save(productItem);

        return productItemMapper.toProductItemResponse(productItem);
    }

    // Update Product Item By UUID
    @Override
    public ProductItemResponse updateByUuid(String uuid, ProductItemRequest productItemRequest) {

        ProductItem productItem = productItemRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product item not found"));

        // System set Product Item Update
        productItem.setQty(productItemRequest.qty());
        productItem.setDiscount(productItemRequest.discount());
        productItem.setUnitPrice(productItemRequest.unitPrice());
        productItem.setUpdateAt(LocalDateTime.now());
        productItem.setCreateAt(productItem.getCreateAt());

        // Validate Price after discount
        Double unitPrice = productItem.getUnitPrice();
        Double discount = productItem.getDiscount()/100;
        Double priceAfterDis = unitPrice - (unitPrice * discount);

        productItem.setPrice(priceAfterDis);

        // Save Product Item latest database back later
        productItemRepository.save(productItem);

        // Transfer Domain To DTO
        return productItemMapper.toProductItemResponse(productItem);
    }

    // Find Product Item By UUID
    @Override
    public ProductItemResponse findByUuid(String uuid) {

        ProductItem productItem = productItemRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product Item not found"));

        Integer newView = productItem.getView() + 1;
        productItem.setView(newView);

        productItemRepository.save(productItem);

        return productItemMapper.toProductItemResponse(productItem);
    }

    @Override
    public List<ProductItemResponse> findListByCategoryUuid(String uuid) {

        Category category = categoryRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        List<Product> product = productRepository.findByCategory(category);
        List<ProductItem> productItems = productItemRepository.findByProductIn(product);


        return productItems.stream().map(productItemMapper::toProductItemResponse).toList();
    }

    // FindList Product Items
    @Override
    public Page<ProductItemResponse> findList(int pageNumber, int pageSize) {

        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, sortById);

        Page<ProductItem> productItems = productItemRepository.findAll(pageRequest);

        // Transfer Domain to DTO
        return productItems.map(productItemMapper::toProductItemResponse);
    }

    // Create New Product Item
    @Override
    public ProductItemResponse createNew(ProductItemRequest productItemRequest) {

        // Validate Product
        Product product = productRepository
                .findByUuid(productItemRequest.productUuid())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        // Check if a ProductItem with the same Product already exists
        if (productItemRepository.existsByProduct(product)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ProductItem already exists for this Product");
        }

        // Transfer DTO to Domain Model
        ProductItem productItem = productItemMapper.fromProductItemRequest(productItemRequest);

        // System Generate data
        productItem.setUuid(UUID.randomUUID().toString());
        productItem.setCreateAt(LocalDateTime.now());
        productItem.setUpdateAt(LocalDateTime.now());
        productItem.setSku(true);
        productItem.setView(0);

        Double unitPrice = productItem.getUnitPrice();
        Double discount = productItem.getDiscount()/100;
        Double priceAfterDis = productItem.getUnitPrice() - (unitPrice * discount);

        productItem.setPrice(priceAfterDis);

        // Set Product for the ProductItem
        productItem.setProduct(product);

        // Save Product Item In Database
        productItemRepository.save(productItem);

        // Transfer Domain To DTO
        return productItemMapper.toProductItemResponse(productItem);
    }
}
