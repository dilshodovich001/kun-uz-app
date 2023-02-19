package uz.kun.category;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.config.Language;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    /**======== PUBLIC REQUESTS ========*/
    @GetMapping("/public")
    public ResponseEntity<CategoryResponse> getById(
            @RequestParam("id") Integer id
    ) {
        log.info("Get by id: {}", id);
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @GetMapping("/public/list")
    public ResponseEntity<List<CategoryResponse>> getList(
            @RequestParam("lang") Language lang
    ) {
        log.info("Category language: {}", lang.name());
        return ResponseEntity.ok(categoryService.getList(lang));
    }
    /**======== PUBLIC REQUESTS END ========*/


    /**======== PRIVATE REQUESTS ========*/
    @PostMapping("/private/create")
    public ResponseEntity<CategoryResponse> create(
            @RequestBody @Valid CategoryRequest request
    ) {
        log.info("Category request: {}", request);
        return ResponseEntity.ok(categoryService.create(request));
    }

    @PutMapping("/private/update")
    public ResponseEntity<CategoryResponse> update(
            @RequestParam("id") Integer id,
            @RequestBody CategoryRequest request
    ) {
        log.info("Category update request: {}, category id: {}", request, id);
        return ResponseEntity.ok(categoryService.update(id, request));
    }

    @DeleteMapping("/private/delete")
    public ResponseEntity<String> delete(@RequestParam("id") Integer id) {
        log.info("Category delete request, category id: {}", id);
        boolean result = categoryService.delete(id);

        if (result) log.info("Category successfully deleted !");
        return ResponseEntity.ok("Category successfully deleted !");
    }
    /**======== PRIVATE REQUESTS END ========*/

}
