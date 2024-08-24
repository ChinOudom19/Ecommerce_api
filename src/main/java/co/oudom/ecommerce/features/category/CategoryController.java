package co.oudom.ecommerce.features.category;

import co.oudom.ecommerce.features.category.dto.CategoryRenameRequest;
import co.oudom.ecommerce.features.category.dto.CategoryRequest;
import co.oudom.ecommerce.features.category.dto.CategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{uuid}")
    void deleteCategory(@PathVariable("uuid") String uuid) {
        categoryService.deleteCategory(uuid);
    }

    @PatchMapping("/{uuid}")
    CategoryResponse renameByUuid(@PathVariable("uuid") String uuid,@Valid @RequestBody CategoryRenameRequest categoryRenameRequest){
        return categoryService.renameByUuid(uuid,categoryRenameRequest);
    }

    @PutMapping("/{uuid}")
    CategoryResponse updateByUuid(@PathVariable("uuid") String uuid, @Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryService.updateByUuid(uuid, categoryRequest);
    }

    @GetMapping("/{uuid}")
    CategoryResponse findByUuid(@PathVariable("uuid") String uuid) {
        return categoryService.findByUuid(uuid);
    }

    @GetMapping
    List<CategoryResponse> findList() {
        return categoryService.findList();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    CategoryResponse createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

}
