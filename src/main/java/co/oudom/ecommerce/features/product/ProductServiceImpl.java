package co.oudom.ecommerce.features.product;

import co.oudom.ecommerce.domain.Category;
import co.oudom.ecommerce.domain.Product;
import co.oudom.ecommerce.features.category.CategoryRepository;
import co.oudom.ecommerce.features.fileupload.FileUploadService;
import co.oudom.ecommerce.features.fileupload.FileUploadServiceImpl;
import co.oudom.ecommerce.features.fileupload.dto.FileUploadResponse;
import co.oudom.ecommerce.features.product.dto.ProductRequest;
import co.oudom.ecommerce.features.product.dto.ProductResponse;
import co.oudom.ecommerce.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public void deleteByUuid(String uuid) {

        Product product = productRepository
                .findByUuid(uuid)
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        productRepository.delete(product);

    }

    @Override
    public ProductResponse updateByUuid(String uuid, ProductRequest productRequest) {

        // Find the product by UUID
        Product product = productRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Product not found"));

        // Update product details from the request
        product.setName(productRequest.name());
        product.setTitle(productRequest.title());
        product.setDescription(productRequest.description());

        // Update category if provided and exists
        if (productRequest.categoryName() != null) {
            Category category = categoryRepository.findByName(productRequest.categoryName())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found"));
            product.setCategory(category);
        }

        // Save the updated product
        productRepository.save(product);

        // Map updated product to response
        return productMapper.toProductResponse(product);
    }

    @Override
    public ProductResponse findByUuid(String uuid) {

        Product product = productRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found"));
        return productMapper.toProductResponse(product);
    }

    @Override
    public List<ProductResponse> findList() {

        Sort sortByName = Sort.by(Sort.Direction.DESC, "name");
        List<Product> products = productRepository.findAll(sortByName);

        return productMapper.toProductResponses(products);
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {

        // Validate category existence
        Category category  = categoryRepository.findByName(productRequest.categoryName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        // Map product request to product entity
        Product product = productMapper.fromProductRequest(productRequest);

        // Set UUID for the product
        product.setUuid(UUID.randomUUID().toString());

        // Set category for the product
        product.setCategory(category);

        // Save the product entity
        productRepository.save(product);

        // Build and return product response
        return productMapper.toProductResponse(product);
    }
}

