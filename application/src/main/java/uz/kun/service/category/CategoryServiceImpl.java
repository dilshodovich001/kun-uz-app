package uz.kun.service.category;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.kun.entity.Category;
import uz.kun.model.request.CategoryRequest;
import uz.kun.model.response.CategoryResponse;
import uz.kun.repository.CategoryRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Function;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * @param categoryRequest
     * @return created CategoryResponse
     */
    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {

        boolean categoryExists = categoryRepository
                .findByKey(categoryRequest.getKey())
                .isPresent();

        if (categoryExists) {
            throw new RuntimeException("Category already exists.");
        }

        var categoryEntity = Category.builder()
                .nameEn(categoryRequest.getNameEn())
                .nameRu(categoryRequest.getNameRu())
                .nameUz(categoryRequest.getNameUz())
                .key(categoryRequest.getKey())
                .creatorId(new Random().nextInt(1,1000)) //todo: authenticated user id...
                .createdDate(LocalDateTime.now())
                .build();

        categoryRepository.save(categoryEntity);

        Function<Category, CategoryResponse> categoryResponseMapper = mapToResponse();
        return categoryResponseMapper.apply(categoryEntity);
    }

    private Function<Category, CategoryResponse> mapToResponse() {
        return (categoryEntity) -> CategoryResponse.builder()
                .id(categoryEntity.getId())
                .nameUz(categoryEntity.getNameUz())
                .nameEn(categoryEntity.getNameEn())
                .nameRu(categoryEntity.getNameRu())
                .creatorId(categoryEntity.getCreatorId())
                .createdDate(categoryEntity.getCreatedDate())
                .build();
    }

    /**
     * @param categoryKey
     * @return CategoryResponse
     */
    @Override
    public CategoryResponse getCategoryByKey(String categoryKey) {
        return categoryRepository
                .findByKey(categoryKey)
                .map(mapToResponse())
                .orElseThrow();
    }


    /**
     * @param categoryId
     * @return CategoryResponse
     */
    @Override
    public CategoryResponse getCategoryById(Integer categoryId) {
        return categoryRepository
                .findById(categoryId)
                .map(mapToResponse())
                .orElseThrow();
    }

    /**
     * @return CategoriesList
     */
    @Override
    public List<CategoryResponse> getCategoriesList() {
        return categoryRepository
                .findAll()
                .stream()
                .map(mapToResponse())
                .toList();
    }

    /**
     * @param categoryId
     * @param categoryRequest
     * @return categoryResponse
     */
    @Override
    public CategoryResponse updateCategory(Integer categoryId, CategoryRequest categoryRequest) {
        var categoryEntity = categoryRepository
                .findById(categoryId)
                .orElseThrow();

        categoryEntity.setNameEn(categoryRequest.getNameEn());
        categoryEntity.setNameRu(categoryRequest.getNameRu());
        categoryEntity.setNameUz(categoryRequest.getNameUz());
        categoryEntity.setKey(categoryRequest.getKey());

        categoryRepository.save(categoryEntity);

        Function<Category, CategoryResponse> categoryResponseMapper = mapToResponse();
        return categoryResponseMapper.apply(categoryEntity);
    }

    /**
     * @param categoryId
     * @return true or false
     */
    @Override
    public boolean deleteCategory(Integer categoryId) {
        var categoryEntity = categoryRepository
                .findById(categoryId)
                .orElseThrow();

        categoryRepository.delete(categoryEntity);
        return true;
    }
}
