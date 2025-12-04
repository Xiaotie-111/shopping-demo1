package edu.ngd.eurke.shoppingdemo1.controller;

import edu.ngd.eurke.shoppingdemo1.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    // 模拟商品数据库（实际项目应使用数据库）
    private static List<Product> products = new ArrayList<>();

    static {
        // 初始化一些示例商品
        products.add(new Product("iPhone 17", 5999.0));
        products.add(new Product("MacBook Air", 8999.0));
        products.add(new Product("AirPods Pro", 1999.0));
    }

    // 首页：展示商品列表
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("products", products);
        model.addAttribute("now", LocalDateTime.now());
        return "index"; // 对应 templates/index.html
    }

    // 跳转到编辑页面（GET）
    @GetMapping("/edit-product")
    public String showEditForm(@RequestParam String name,
                               @RequestParam Double price,
                               Model model) {
        model.addAttribute("name", name);
        model.addAttribute("price", price);
        return "edit-product"; // 对应 templates/edit-product.html
    }

    // 处理商品更新（POST）
    @PostMapping("/update-product")
    public String updateProduct(@RequestParam String originalName,
                                @RequestParam String name,
                                @RequestParam Double price) {
        // 查找并更新商品
        for (Product p : products) {
            if (p.getName().equals(originalName)) {
                p.setName(name);
                p.setPrice(price);
                break;
            }
        }
        // 更新完成后重定向回首页
        return "redirect:/";
    }
}