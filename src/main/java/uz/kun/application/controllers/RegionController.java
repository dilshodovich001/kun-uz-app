package uz.kun.application.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.kun.infrastructure.config.Language;
import uz.kun.domain.models.request.RegionRequest;
import uz.kun.domain.models.response.RegionResponse;
import uz.kun.application.services.RegionService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/region")
public class RegionController {
    private final RegionService regionService;

    /**======== PUBLIC REQUESTS ========*/
    @GetMapping("/public/{lang}/list")
    public ResponseEntity<List<RegionResponse>> getList(@PathVariable Language lang) {
        log.info("Request region list, language: {}", lang.name());
        return ResponseEntity.ok(regionService.getList(lang));
    }
    /**======== PUBLIC REQUESTS END ========*/



    /**======== PRIVATE REQUESTS ========*/
    @PostMapping("/private/create")
    public ResponseEntity<RegionResponse> create(
            @RequestBody @Valid RegionRequest request
    ) {
        log.info("Request to create a region: {}", request.toString());
        return ResponseEntity.ok(regionService.create(request));
    }

    @GetMapping("/private")
    public ResponseEntity<RegionResponse> getById(@RequestParam("id") Integer id) {
        log.info("Get request region id: {}", id);
        return ResponseEntity.ok(regionService.getRegion(id));
    }

    @PutMapping("/private/update")
    public ResponseEntity<RegionResponse> update(
            @RequestParam("id") Integer id,
            @RequestBody RegionRequest request
    ) {
        log.info("Region update request: {}, id: {}", request.toString(), id);
        return ResponseEntity.ok(regionService.update(id, request));
    }

    @DeleteMapping("/private/delete")
    public ResponseEntity<String> delete(@RequestParam("id") Integer id) {
        log.info("Delete request, id: {}", id);

        var result = regionService.delete(id);
        if (result) log.info("Region successfully deleted !");

        return ResponseEntity.ok("Region successfully deleted !");
    }

    /**======== PRIVATE REQUESTS END ========*/

}
