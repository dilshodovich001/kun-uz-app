package uz.kun.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.kun.model.request.CategoryRequest;
import uz.kun.service.category.CategoryService;

@Controller
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/create")
    public String createCategoryPage(Model model) {
        var categoryRequest = CategoryRequest.builder().build();
        model.addAttribute("categoryRequest", categoryRequest);
        return "category/create-category";
    }

    @PostMapping("/create")
    public String createCategory(
            @ModelAttribute("categoryRequest")
            CategoryRequest categoryRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "category/create-category";
        }

        categoryService.createCategory(categoryRequest);
        return "redirect:/category/list";
    }

    @GetMapping("/list")
    public String categoriesList(Model model) {
        var categoriesList = categoryService.getCategoriesList();
        model.addAttribute("categoriesList", categoriesList);
        return "category/categories-list";
    }

    @GetMapping("/update/{categoryId}")
    public String updateCategoryPage(
            @PathVariable Integer categoryId,
            Model model
    ){
        var category = categoryService.getCategoryById(categoryId);
        var categoryRequest = CategoryRequest.builder()
                .key(category.getKey())
                .nameEn(category.getNameEn())
                .nameUz(category.getNameUz())
                .nameRu(category.getNameRu())
                .build();

        model.addAttribute("categoryRequest", categoryRequest);
        model.addAttribute("categoryId", categoryId);
        return "category/update-category";
    }

    @PostMapping("/update/{categoryId}")
    public String updateCategory(
            @PathVariable Integer categoryId,
            @ModelAttribute("categoryRequest")
            CategoryRequest categoryRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "redirect:/update/{categoryId}";
        }

        categoryService.updateCategory(categoryId, categoryRequest);
        return "redirect:/category/list";
    }

    @GetMapping("/delete/{categoryId}")
    public String deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return "redirect:/category/list";
    }
}
