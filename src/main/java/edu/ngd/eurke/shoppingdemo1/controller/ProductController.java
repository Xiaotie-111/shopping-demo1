//package edu.ngd.eurke.shoppingdemo1.controller;
//
//import edu.ngd.eurke.shoppingdemo1.Product;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpSession;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Controller
//public class ProductController {
//
//    // 模拟商品数据库（实际项目应使用数据库）
//    private static List<Product> products = new ArrayList<>();
//
//    static {
//        // 初始化一些示例商品
//        products.add(new Product("iPhone 17", 5999.0));
//        products.add(new Product("MacBook Air", 8999.0));
//        products.add(new Product("AirPods Pro", 1999.0));
//    }
//
//    // 首页：展示商品列表
//    @GetMapping("/")
//    public String home(Model model, HttpSession session) {
//        model.addAttribute("products", products);
//        model.addAttribute("now", LocalDateTime.now());
//
//        // 计算购物车中商品总数量
//        List<Product.CartItem> cart = (List<Product.CartItem>) session.getAttribute("cart");
//        if (cart != null && !cart.isEmpty()) {
//            int totalQuantity = cart.stream()
//                    .mapToInt(Product.CartItem::getQuantity)
//                    .sum();
//            model.addAttribute("cartTotalQuantity", totalQuantity);
//        } else {
//            model.addAttribute("cartTotalQuantity", 0);
//        }
//
//        return "index"; // 对应 templates/index.html
//    }
//
//    // 跳转到编辑页面（GET）
//    @GetMapping("/edit-product")
//    public String showEditForm(@RequestParam String name,
//                               @RequestParam Double price,
//                               Model model) {
//        model.addAttribute("name", name);
//        model.addAttribute("price", price);
//        return "edit-product"; // 对应 templates/edit-product.html
//    }
//
//    // 处理商品更新（POST）
//    @PostMapping("/update-product")
//    public String updateProduct(@RequestParam String originalName,
//                                @RequestParam String name,
//                                @RequestParam Double price) {
//        // 查找并更新商品
//        for (Product p : products) {
//            if (p.getName().equals(originalName)) {
//                p.setName(name);
//                p.setPrice(price);
//                break;
//            }
//        }
//        // 更新完成后重定向回首页
//        return "redirect:/";
//    }
//}

package edu.ngd.eurke.shoppingdemo1.controller;

import edu.ngd.eurke.shoppingdemo1.model.Product;
import edu.ngd.eurke.shoppingdemo1.service.CartService;
import edu.ngd.eurke.shoppingdemo1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private CartService cartService;

    // 商品列表页面
    @GetMapping("/products")
    public String home(Model model) {
        model.addAttribute("products", productService.findAll());
        model.addAttribute("now", LocalDateTime.now());

        // 计算购物车中商品总数量
        int totalQuantity = cartService.getCartItems().stream()
                .mapToInt(item -> item.getQuantity())
                .sum();
        model.addAttribute("cartTotalQuantity", totalQuantity);

        return "index"; // 对应 templates/index.html
    }

    // 跳转到编辑页面（GET）
    @GetMapping("/edit-product/{id}")
    public String showEditForm(@PathVariable("id") Long productId, Model model, HttpSession session) {
        // 检查用户是否已登录且为商家角色
        String role = (String) session.getAttribute("role");
        if (role == null || !"merchant".equals(role)) {
            model.addAttribute("message", "您没有权限访问此页面");
            return "message";
        }
        
        Optional<Product> productOpt = productService.findById(productId);
        if (!productOpt.isPresent()) {
            model.addAttribute("message", "商品不存在");
            return "message";
        }
        
        Product product = productOpt.get();
        model.addAttribute("product", product);
        return "edit-product"; // 对应 templates/edit-product.html
    }

    // 处理商品更新（POST）
    @PostMapping("/update-product")
    public String updateProduct(@RequestParam Long productId,
                                @RequestParam String name,
                                @RequestParam double price,
                                @RequestParam int stock,
                                Model model,
                                HttpSession session) {
        // 检查用户是否已登录且为商家角色
        String role = (String) session.getAttribute("role");
        if (role == null || !"merchant".equals(role)) {
            model.addAttribute("message", "您没有权限执行此操作");
            return "message";
        }
        
        Optional<Product> productOpt = productService.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setName(name);
            product.setPrice(java.math.BigDecimal.valueOf(price));
            product.setQuantity(stock); // 更新库存
            productService.updateProduct(product);
            session.setAttribute("successMessage", "商品信息修改成功！");
        } else {
            session.setAttribute("errorMessage", "商品不存在，修改失败！");
        }
        // 更新完成后重定向回首页
        return "redirect:/"; // 已由IndexController处理
    }
}
