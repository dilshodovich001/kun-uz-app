package uz.kun.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.kun.model.request.TagRequest;
import uz.kun.service.tag.TagService;

@Controller
@RequestMapping("/tag")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/create")
    public String createTag(Model model) {
        var tagRequest = TagRequest.builder().build();
        model.addAttribute("tagRequest", tagRequest);
        return "tag/create-tag";
    }

    @PostMapping("/create")
    public String createTag(
            @ModelAttribute("tagRequest")
            TagRequest tagRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "tag/create-tag";
        }

        tagService.createTag(tagRequest);
        return "redirect:/tag/list";
    }

    @GetMapping("/list")
    public String tagsList(Model model) {
        var tagsList = tagService.getAllTagsList();
        model.addAttribute("tags", tagsList);

        return "tag/tags-list";
    }

    @GetMapping("/update/{tagId}")
    public String updateTag(
            @PathVariable int tagId,
            Model model
    ) {
        var tag = tagService.getTagById(tagId);
        var tagRequest = TagRequest.builder()
                .key(tag.getKey())
                .nameRu(tag.getNameRu())
                .nameUz(tag.getNameUz())
                .nameEn(tag.getNameEn())
                .build();

        model.addAttribute("tagRequest", tagRequest);
        model.addAttribute("tagId", tagId);

        return "tag/update-tag";
    }

    @PostMapping("/update/{tagId}")
    public String updateTag(
            @PathVariable int tagId,
            @ModelAttribute("tagRequest")
            TagRequest tagRequest,
            BindingResult bindingResult
    ){
        if (bindingResult.hasErrors()) {
            return "tag/update-tag";
        }

        tagService.updateTag(tagId, tagRequest);
        return "redirect:/tag/list";
    }

    @GetMapping("/delete/{tagId}")
    public String deleteTag(
            @PathVariable int tagId
    ) {
        tagService.deleteTag(tagId);
        return "redirect:/tag/list";
    }

}
