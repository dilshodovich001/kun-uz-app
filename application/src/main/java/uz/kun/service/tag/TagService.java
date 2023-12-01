package uz.kun.service.tag;

import uz.kun.model.request.TagRequest;
import uz.kun.model.response.TagResponse;

import java.util.List;

public interface TagService {
    void createTag(TagRequest tagRequest);
    void updateTag(Integer tagId, TagRequest tagRequest);
    TagResponse getTagById(Integer tagId);
    List<TagResponse> getAllTagsList();
    void deleteTag(Integer tagId);
}
