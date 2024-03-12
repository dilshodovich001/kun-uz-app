package uz.kun.application.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.kun.application.models.rest.ResponseData;
import uz.kun.domain.models.request.RegionRequest;
import uz.kun.domain.models.response.RegionResponse;
import uz.kun.domain.usecases.RegionUseCase;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/regions")
public class RegionController {

    private final RegionUseCase regionUseCase;

    @PostMapping
    public void create(@RequestBody RegionRequest regionRequest) {
        regionUseCase.create(regionRequest);
    }

    @GetMapping
    public ResponseData<RegionResponse> read(
            @RequestParam("regionId")
            Integer regionId
    ) {
        return ResponseData.ok(regionUseCase.read(regionId));
    }

    @PutMapping
    public ResponseData<RegionResponse> update(
            @RequestParam("regionId") Integer regionId,
            @RequestBody RegionRequest regionRequest
    ) {
        return ResponseData.ok(regionUseCase.update(regionId, regionRequest));
    }

    @DeleteMapping
    public void delete(@RequestParam("regionId") Integer regionId) {
        regionUseCase.delete(regionId);
    }

    @GetMapping("/list")
    public ResponseData<List<RegionResponse>> regionsList() {
        return ResponseData.ok(regionUseCase.regionsList());
    }
}