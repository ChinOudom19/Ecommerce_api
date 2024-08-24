package co.oudom.ecommerce.features.category;

import co.oudom.ecommerce.domain.Category;
import co.oudom.ecommerce.features.category.dto.CategoryRenameRequest;
import co.oudom.ecommerce.features.category.dto.CategoryRequest;
import co.oudom.ecommerce.features.category.dto.CategoryResponse;
import co.oudom.ecommerce.mapper.CategoryMapper;
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
public class CategoryServiceImpl implements CategoryService{

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public void deleteCategory(String uuid) {
        Category category = categoryRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        categoryRepository.delete(category);

    }

    @Override
    public CategoryResponse renameByUuid(String uuid, CategoryRenameRequest categoryRenameRequest) {

        Category category = categoryRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        category.setName(categoryRenameRequest.name());

        categoryRepository.save(category);

        return categoryMapper.toCategoryResponse(category);

    }

    @Override
    public CategoryResponse updateByUuid(String uuid, CategoryRequest categoryRequest) {

        Category category = categoryRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        category.setName(categoryRequest.name());
        category.setImage(categoryRequest.image());
        categoryRepository.save(category);
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public CategoryResponse findByUuid(String uuid) {
        Category category = categoryRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        // Create CategoryResponse using Lombok @Builder
        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> findList() {

        Sort sortByName = Sort.by(Sort.Direction.DESC, "name");
        List<Category> categories = categoryRepository.findAll(sortByName);
        return categoryMapper.toCategoryResponses(categories);
    }


    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {

        Category category = categoryMapper.fromCategoryRequest(categoryRequest);

        if (categoryRepository.existsByName(categoryRequest.name())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category name already exists");
        }

        category.setUuid(UUID.randomUUID().toString());

        categoryRepository.save(category);

        return categoryMapper.toCategoryResponse(category);
    }
}
