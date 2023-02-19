package uz.kun.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.kun.exception.ItemNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    private CommentResponse toResponse(CommentEntity e) {
        return CommentResponse.builder()
                .id(e.getId())
                .content(e.getContent())
                .userId(e.getUserId())
                .articleId(e.getArticleId())
                .createdDate(e.getCreatedDate())
                .updatedDate(e.getUpdatedDate())
                .build();
    }
    private CommentEntity get(Integer id){
        return commentRepository.findById(id)
                .orElseThrow(()-> new ItemNotFoundException("Not found"));
    }

    public CommentResponse create(CommentRequest request) {
        var comment = CommentEntity.builder()
                .content(request.getContent())
                .userId(request.getUserId())
                .articleId(request.getArticleId())
                .createdDate(LocalDateTime.now())
                .build();

        commentRepository.save(comment);
        return toResponse(comment);
    }

    public CommentResponse getById(Integer id) {
        var comment = get(id);
        return toResponse(comment);
    }

    public List<CommentResponse> getList(int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC));
        return commentRepository.findAll(pageable)
                .map(this::toResponse)
                .toList();
    }

    public boolean delete(Integer id) {
        var comment = get(id);
        commentRepository.delete(comment);
        return true;
    }

    public CommentResponse update(Integer id, CommentRequest request) {
        var comment = get(id);
        comment.setContent(request.getContent());

        commentRepository.save(comment);
        return toResponse(comment);
    }
}
