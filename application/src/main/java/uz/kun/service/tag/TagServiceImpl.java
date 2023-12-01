package uz.kun.service.tag;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.kun.entity.Tag;
import uz.kun.model.request.TagRequest;
import uz.kun.model.response.TagResponse;
import uz.kun.repository.TagRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

@Service
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    /**
     * @param tagRequest
     */
    @Override
    public void createTag(TagRequest tagRequest) {
        boolean tagExists = tagRepository
                .findByKey(tagRequest.getKey())
                .isPresent();

        if (tagExists) {
            throw new RuntimeException("Tag already exist");
        }

        Tag tagEntity = Tag.builder()
                .key(tagRequest.getKey())
                .nameUz(tagRequest.getNameUz())
                .nameEn(tagRequest.getNameEn())
                .nameRu(tagRequest.getNameRu())
                .creatorId(new Random().nextInt(1, 100))
                .createdDate(LocalDateTime.now())
                .build();

        tagRepository.save(tagEntity);
    }

    private Function<Tag, TagResponse> mapToResponse() {
        return tagEntity -> TagResponse.builder()
                .id(tagEntity.getId())
                .key(tagEntity.getKey())
                .nameUz(tagEntity.getNameUz())
                .nameRu(tagEntity.getNameRu())
                .nameEn(tagEntity.getNameEn())
                .visible(tagEntity.getVisible())
                .creatorId(tagEntity.getCreatorId())
                .createdDate(tagEntity.getCreatedDate())
                .build();
    }

    /**
     * @param tagId
     * @param tagRequest
     */
    @Override
    public void updateTag(Integer tagId, TagRequest tagRequest) {
        var tagEntity = tagRepository
                .findById(tagId)
                .orElseThrow();

        tagEntity.setKey(tagRequest.getKey());
        tagEntity.setNameEn(tagRequest.getNameEn());
        tagEntity.setNameUz(tagRequest.getNameUz());
        tagEntity.setNameRu(tagRequest.getNameRu());

        tagRepository.save(tagEntity);
    }

    /**
     * @param tagId
     * @return
     */
    @Override
    public TagResponse getTagById(Integer tagId) {
        return tagRepository
                .findById(tagId)
                .map(mapToResponse())
                .orElseThrow();
    }

    /**
     * @return
     */
    @Override
    public List<TagResponse> getAllTagsList() {
        return tagRepository
                .findAll()
                .stream()
                .map(mapToResponse())
                .toList();
    }

    /**
     * @param tagId
     */
    @Override
    public void deleteTag(Integer tagId) {
        var tagEntity = tagRepository.findById(tagId).orElseThrow();
        tagRepository.delete(tagEntity);
    }
}
