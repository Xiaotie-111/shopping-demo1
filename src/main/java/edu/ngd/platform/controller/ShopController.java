package edu.ngd.platform.controller;

import edu.ngd.platform.model.Category;
import edu.ngd.platform.model.Product;
import edu.ngd.platform.service.CategoryService;
import edu.ngd.platform.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 电商平台首页控制器
 */
@Controller
public class ShopController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    /**
     * 跳转到电商平台首页
     * @return 电商平台首页视图
     */
    @GetMapping("/")
    public String index(Model model, 
                       @RequestParam(required = false) String keyword, 
                       @RequestParam(required = false) Long categoryId, 
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(required = false) String sortBy,
                       @RequestParam(required = false) String sortOrder) {
        // 获取商品列表（可以根据关键词和分类筛选，支持排序）
        List<Product> products = productService.listByCondition(keyword, categoryId);
        
        // 获取所有分类用于筛选
        List<Category> categories = categoryService.getAllCategories();
        
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", 3); // 模拟总页数为3
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", sortOrder);
        
        return "product/browse";
    }
    
    /**
     * 跳转到电商平台首页（别名）
     * @return 电商平台首页视图
     */
    @GetMapping("/shop")
    public String shop() {
        return "redirect:/";
    }
    
    /**
     * 跳转到电商平台首页（旧路径，兼容用）
     * @return 电商平台首页视图
     */
    @GetMapping("/shop/index")
    public String shopIndex() {
        return "redirect:/";
    }
}