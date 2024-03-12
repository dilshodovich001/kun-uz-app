package uz.kun.application.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.domain.models.request.LikeRequest;
import uz.kun.domain.models.response.LikeResponse;
import uz.kun.application.services.LikeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class LikeController {
    private final LikeService likeService;

    /**======== PUBLIC REQUESTS ========*/
    @PostMapping("/public/create")
    public ResponseEntity<LikeResponse> create(
            @RequestParam Integer userId,
            @RequestBody LikeRequest request
    ){
        return ResponseEntity.ok(likeService.create(request,userId));
    }
    @DeleteMapping("/public/delete")
    public ResponseEntity<?> delete(
            @RequestParam Integer id
    ){
        return ResponseEntity.ok(likeService.delete(id));
    }
    /**======== PUBLIC REQUESTS END========*/



    /**======== PRIVATE REQUESTS ========*/

    @GetMapping("/private/{articleId}")
    public ResponseEntity<List<LikeResponse>> getAllByArticleId(
            @PathVariable int articleId,
            @RequestParam int page,
            @RequestParam int size
    ){
        return ResponseEntity.ok(likeService.getAllByArticleId(articleId, page, size));
    }

    @GetMapping("/private/{userId}")
    public ResponseEntity<List<LikeResponse>> getAllByProfileId(
            @PathVariable Integer userId
    ){
        return ResponseEntity.ok(likeService.getAllByUserId(userId));
    }

    @GetMapping("/private/list")
    public ResponseEntity<List<LikeResponse>> getList(
            @RequestParam int page,
            @RequestParam int size
    ){
        return ResponseEntity.ok(likeService.getList(page,size));
    }

    /**======== PRIVATE REQUESTS END========*/

}
