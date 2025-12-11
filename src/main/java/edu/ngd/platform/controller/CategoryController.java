package edu.ngd.platform.controller;

import edu.ngd.platform.model.Category;
import edu.ngd.platform.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理控制器
 */
@Controller
@RequestMapping("/category")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    /**
     * 跳转到分类列表页面
     * @param model 模型对象
     * @param page 当前页码，默认为1
     * @return 分类列表页面视图
     */
    @GetMapping("/list")
    public String categoryList(Model model, @RequestParam(defaultValue = "1") Integer page) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", 3); // 模拟总页数为3
        return "category/list";
    }
    
    /**
     * 跳转到分类添加页面
     * @param model 模型对象
     * @return 分类添加页面视图
     */
    @GetMapping("/add")
    public String categoryAdd(Model model) {
        List<Category> categories = categoryService.getCategoryTree();
        model.addAttribute("categories", categories);
        return "category/add";
    }
    
    /**
     * 处理分类添加请求
     * @param category 分类信息
     * @return 重定向到分类列表页面
     */
    @PostMapping("/add")
    public String doCategoryAdd(Category category) {
        categoryService.addCategory(category);
        return "redirect:/category/list";
    }
    
    /**
     * 跳转到分类编辑页面
     * @param id 分类ID
     * @param model 模型对象
     * @return 分类编辑页面视图
     */
    @GetMapping("/edit/{id}")
    public String categoryEdit(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        List<Category> categories = categoryService.getCategoryTree();
        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        return "category/edit";
    }
    
    /**
     * 处理分类编辑请求
     * @param category 分类信息
     * @return 重定向到分类列表页面
     */
    @PostMapping("/edit")
    public String doCategoryEdit(Category category) {
        categoryService.updateCategory(category);
        return "redirect:/category/list";
    }
    
    /**
     * 处理分类删除请求
     * @param id 分类ID
     * @return 重定向到分类列表页面
     */
    @GetMapping("/delete/{id}")
    public String categoryDelete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return "redirect:/category/list";
    }
    
    /**
     * 处理批量删除分类请求
     * @param ids 分类ID列表
     * @return 重定向到分类列表页面
     */
    @PostMapping("/batchDelete")
    public String batchDeleteCategory(@RequestParam List<Long> ids) {
        categoryService.batchDeleteCategory(ids);
        return "redirect:/category/list";
    }
    
    /**
     * 处理更新分类状态请求
     * @param id 分类ID
     * @param status 状态
     * @return 重定向到分类列表页面
     */
    @GetMapping("/updateStatus/{id}/{status}")
    public String updateCategoryStatus(@PathVariable Long id, @PathVariable Integer status) {
        categoryService.updateCategoryStatus(id, status);
        return "redirect:/category/list";
    }
    
    /**
     * 获取分类树数据（用于下拉选择）
     * @return 分类树数据
     */
    @ResponseBody
    @GetMapping("/tree")
    public List<Category> getCategoryTree() {
        return categoryService.getCategoryTree();
    }
}