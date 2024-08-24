package co.oudom.ecommerce.mapper;

import co.oudom.ecommerce.domain.Category;
import co.oudom.ecommerce.features.category.dto.CategoryRenameRequest;
import co.oudom.ecommerce.features.category.dto.CategoryRequest;
import co.oudom.ecommerce.features.category.dto.CategoryResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toCategoryResponse(Category category);

    List<CategoryResponse> toCategoryResponses(List<Category> categories);

    Category fromCategoryRequest(CategoryRequest categoryRequest);

}
