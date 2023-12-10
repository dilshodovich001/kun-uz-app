package uz.kun.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.kun.model.request.RegionRequest;
import uz.kun.model.response.RegionResponse;
import uz.kun.service.region.RegionService;

@Controller
@RequestMapping("/region")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    @GetMapping("/create")
    public String createRegion(Model model) {

        var regionRequest = RegionRequest.builder().build();
        model.addAttribute("regionRequest", regionRequest);

        return "region/create-region";
    }
    @PostMapping("/create")
    public String createRegion(
            @ModelAttribute("regionRequest")
            RegionRequest regionRequest,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return "region/create-region";
        }

        regionService.createRegion(regionRequest);
        return "redirect:/region/list";
    }

    @GetMapping("/list")
    public String getAllRegionsList(Model model) {
        var regions = regionService.getAllRegionsList();
        model.addAttribute("regions", regions);

        return "region/regions-list";
    }

    @GetMapping("/update/{regionId}")
    public String updateRegion(
            Model model,
            @PathVariable Integer regionId
    ) {

        var region = regionService.getRegionById(regionId);
        var regionRequest = RegionRequest.builder()
                .key(region.getKey())
                .nameUz(region.getNameUz())
                .nameRu(region.getNameRu())
                .nameEn(region.getNameEn())
                .build();

        model.addAttribute("regionRequest", regionRequest);
        model.addAttribute("regionId", regionId);

        return "region/update-region";
    }

    @PostMapping("/update/{regionId}")
    public String updateRegion(
            @ModelAttribute("regionRequest")
            RegionRequest regionRequest,
            @PathVariable Integer regionId,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return "redirect:/region/update/{regionId}";
        }

        regionService.updateRegion(regionId, regionRequest);
        return "redirect:/region/list";
    }

    @GetMapping("/delete/{regionId}")
    public String deleteRegion(@PathVariable Integer regionId) {
        regionService.deleteRegionById(regionId);
        return "redirect:/region/list";
    }
}
