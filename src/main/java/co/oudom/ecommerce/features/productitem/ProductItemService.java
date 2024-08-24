package co.oudom.ecommerce.features.productitem;

import co.oudom.ecommerce.domain.Category;
import co.oudom.ecommerce.features.productitem.dto.ProductItemRequest;
import co.oudom.ecommerce.features.productitem.dto.ProductItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductItemService {

    void deleteByUuid(String uuid);

    ProductItemResponse updateStock(String uuid, ProductItemRequest productItemRequest);

    ProductItemResponse updateByUuid(String uuid,ProductItemRequest productItemRequest);

    ProductItemResponse findByUuid(String uuid);

    List<ProductItemResponse> findListByCategoryUuid(String uuid);

    Page<ProductItemResponse> findList(int pageNumber, int pageSize);

    ProductItemResponse createNew(ProductItemRequest productItemRequest);

}
