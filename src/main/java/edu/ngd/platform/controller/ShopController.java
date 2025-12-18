package edu.ngd.platform.controller;

import edu.ngd.platform.model.Cart;
import edu.ngd.platform.model.Category;
import edu.ngd.platform.model.Product;
import edu.ngd.platform.model.User;
import edu.ngd.platform.service.CartService;
import edu.ngd.platform.service.CategoryService;
import edu.ngd.platform.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
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
    
    @Autowired
    private CartService cartService;
    
    /**
     * 跳转到电商平台首页
     * @return 重定向到商品浏览页面
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/product/browse";
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