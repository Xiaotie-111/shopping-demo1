package edu.ngd.eurke.shoppingdemo1.controller;

import edu.ngd.eurke.shoppingdemo1.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1L, "iPhone 15", 5999.0, "最新款智能手机", "https://example.com/iphone.jpg"));
        products.add(new Product(2L, "MacBook Air", 8999.0, "轻薄笔记本电脑", "https://example.com/macbook.jpg"));
        products.add(new Product(3L, "AirPods Pro", 1999.0, "降噪无线耳机", "https://example.com/airpods.jpg"));

        model.addAttribute("products", products);
        return "index";
    }
}
