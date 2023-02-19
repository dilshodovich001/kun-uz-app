package uz.kun.comment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    /**======== PUBLIC REQUESTS ========*/
    @GetMapping
    public ResponseEntity<CommentResponse> getById(
            @RequestParam Integer id
    ){
        return ResponseEntity.ok(commentService.getById(id));
    }

    @GetMapping("/public/list")
    public ResponseEntity<List<CommentResponse>> getList(
            @RequestParam int page,
            @RequestParam int size
    ){
        return ResponseEntity.ok(commentService.getList(page,size));
    }

    @PutMapping("/public/{commentId}")
    public ResponseEntity<?> update(@PathVariable("commentId") Integer commentId,
                                    @RequestBody @Valid CommentRequest request){
        return ResponseEntity.ok(commentService.update(commentId,request));
    }
    /**======== PUBLIC REQUESTS END ========*/



    /**======== PRIVATE REQUESTS ========*/
    @PostMapping("/private/create")
    public ResponseEntity<CommentResponse> create(
            @RequestBody CommentRequest request
    ){
        return ResponseEntity.ok(commentService.create(request));
    }

    @DeleteMapping("/private/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(commentService.delete(id));
    }

    /**======== PRIVATE REQUESTS END ========*/

}
