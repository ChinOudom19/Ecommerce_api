package co.oudom.ecommerce.features.category;

import co.oudom.ecommerce.features.category.dto.CategoryRenameRequest;
import co.oudom.ecommerce.features.category.dto.CategoryRequest;
import co.oudom.ecommerce.features.category.dto.CategoryResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    void deleteCategory(String uuid);

    CategoryResponse renameByUuid(String uuid, CategoryRenameRequest categoryRenameRequest);

    CategoryResponse updateByUuid(String uuid, CategoryRequest categoryRequest);

    CategoryResponse findByUuid(String name);

    List<CategoryResponse> findList();

    CategoryResponse createCategory(CategoryRequest categoryRequest);

}
