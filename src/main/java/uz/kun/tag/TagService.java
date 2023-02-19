package uz.kun.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.kun.config.Language;
import uz.kun.config.DetailService;
import uz.kun.exception.ItemAlreadyExistsException;
import uz.kun.exception.ItemNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public TagResponse create(TagRequest request) {
        var tagExists = tagRepository.findByKey(request.getKey()).isPresent();
        if (tagExists)
            throw new ItemAlreadyExistsException("Tag already exists, key: " + request.getKey());

        var tag = toEntity(request);
        tagRepository.save(tag);

        return toResponse(tag);
    }

    private TagResponse toResponse(TagEntity tag) {
        return TagResponse.builder()
                .id(tag.getId())
                .key(tag.getKey())
                .nameRu(tag.getNameRu())
                .nameUz(tag.getNameUz())
                .nameEn(tag.getNameEn())
                .visible(tag.getVisible())
                .creatorId(tag.getCreatorId())
                .createdDate(tag.getCreatedDate())
                .build();
    }

    private TagEntity toEntity(TagRequest request) {
        var creator = DetailService.getUser();

        return TagEntity.builder()
                .key(request.getKey())
                .nameUz(request.getNameUz())
                .nameEn(request.getNameEn())
                .nameRu(request.getNameRu())
                .visible(true)
                .creatorId(creator.getId())
                .user(creator)
                .createdDate(LocalDateTime.now())
                .build();
    }

    public List<TagResponse> getList(Language lang) {
        return tagRepository.findAll()
                .stream()
                .map(e -> this.getById(e.getId(),lang))
                .toList();
    }

    public TagResponse getById(Integer id, Language lang){
        var tag = get(id);
        var response = toResponse(tag);
        switch (lang) {
            case uz -> response.setName(tag.getNameUz());
            case ru -> response.setName(tag.getNameRu());
            case en -> response.setName(tag.getNameEn());
        }
        return response;
    }

    public TagResponse getById(Integer id){
        var tag = get(id);
        return toResponse(tag);
    }

    public List<TagResponse> getTagList(List<Integer> tagList, Language lang){
        return tagList
                .stream()
                .map(e->this.getById(e,lang))
                .toList();
    }


    public TagResponse update(Integer id, TagRequest request) {
        var tag = get(id);

        tag.setKey(request.getKey());
        tag.setNameEn(request.getNameEn());
        tag.setNameRu(request.getNameRu());
        tag.setNameUz(request.getNameUz());

        tagRepository.save(tag);
        return toResponse(tag);
    }

    private TagEntity get(Integer id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Tag not found, id: " + id));
    }

    public boolean delete(Integer id) {
        var tag = get(id);
        tagRepository.delete(tag);
        return true;
    }
}
