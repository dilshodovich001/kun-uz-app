package uz.kun.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.kun.config.DetailService;
import uz.kun.config.Language;
import uz.kun.exception.ItemAlreadyExistsException;
import uz.kun.exception.ItemNotFoundException;
import uz.kun.user.UserEntity;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /** CREATE **/
    public CategoryResponse create(CategoryRequest request) {
        var keyExists = categoryRepository.findByKey(request.getKey()).isPresent();
        if (keyExists)
            throw new ItemAlreadyExistsException("Category key already exists, value: " + request.getKey());

        var categoryEntity = toEntity(request);
        categoryRepository.save(categoryEntity);

        return toResponse(categoryEntity);
    }

    /** READ **/
    public CategoryResponse getByKey(String key) {
        var category = get(key);
        return toResponse(category);
    }

    public CategoryResponse getById(Integer id) {
        var category = get(id);
        return toResponse(category);
    }

    public CategoryResponse getById(Integer id, Language lang) {
        var category = get(id);
        var response = toResponse(category);

        switch (lang) {
            case uz -> response.setName(category.getNameUz());
            case ru -> response.setName(category.getNameRu());
            case en -> response.setName(category.getNameEn());
        }

        return response;
    }


    public List<CategoryResponse> getList(Language lang) {
        return categoryRepository.findAll()
                .stream()
                .map(e -> {
                    var response = this.toResponse(e);
                    switch (lang) {
                        case uz -> response.setName(e.getNameUz());
                        case ru -> response.setName(e.getNameRu());
                        case en -> response.setName(e.getNameEn());
                    }
                    return response;
                })
                .toList();
    }

    /** UPDATE **/
    public CategoryResponse update(Integer id, CategoryRequest request) {
        var category = get(id);

        category.setKey(request.getKey());
        category.setNameEn(request.getNameEn());
        category.setNameUz(request.getNameUz());
        category.setNameRu(request.getNameRu());

        categoryRepository.save(category);
        return toResponse(category);
    }

    /** DELETE **/
    public boolean delete(Integer id){
        var category = get(id);
        categoryRepository.delete(category);
        return true;
    }

    private CategoryResponse toResponse(CategoryEntity e) {
        return CategoryResponse.builder()
                .id(e.getId())
                .nameUz(e.getNameUz())
                .nameEn(e.getNameEn())
                .nameRu(e.getNameRu())
                .key(e.getKey())
                .creatorId(e.getCreatorId())
                .createdDate(e.getCreatedDate())
                .build();
    }

    private CategoryEntity toEntity(CategoryRequest request) {
        UserEntity creator = DetailService.getUser();

        return CategoryEntity.builder()
                .nameUz(request.getNameUz())
                .nameEn(request.getNameEn())
                .nameRu(request.getNameRu())
                .key(request.getKey())
                .creatorId(creator.getId())
                .user(creator)
                .createdDate(LocalDateTime.now())
                .build();
    }

    private CategoryEntity get(String key) {
        return categoryRepository.findByKey(key)
                .orElseThrow(() -> new ItemNotFoundException("Category not found, key: " + key));
    }

    private CategoryEntity get(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Category not found, id: " + id));
    }
}
