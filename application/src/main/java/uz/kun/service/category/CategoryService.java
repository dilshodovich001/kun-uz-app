package uz.kun.service.category;

import uz.kun.model.request.CategoryRequest;
import uz.kun.model.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryResponse getCategoryByKey(String categoryKey);
    CategoryResponse getCategoryById(Integer categoryId);
    List<CategoryResponse> getCategoriesList();
    CategoryResponse updateCategory(Integer categoryId, CategoryRequest categoryRequest);
    boolean deleteCategory(Integer categoryId);
}
