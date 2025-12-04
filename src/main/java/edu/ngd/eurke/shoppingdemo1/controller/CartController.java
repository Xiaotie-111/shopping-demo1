package edu.ngd.eurke.shoppingdemo1.controller;

import edu.ngd.eurke.shoppingdemo1.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CartController {

    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam String name,
                            @RequestParam Double price,
                            @RequestParam(defaultValue = "1") Integer quantity,
                            HttpServletRequest request) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return "redirect:/login?role=user";
        }

        List<Product.CartItem> cart = (List<Product.CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        // 查找是否已有该商品
        for (Product.CartItem item : cart) {
            if (item.getName().equals(name)) {
                item.setQuantity(item.getQuantity() + quantity);
                return "redirect:/";
            }
        }

        // 新增
        Product.CartItem newItem = new Product.CartItem(name, price, quantity);
        cart.add(newItem);

        return "redirect:/";
    }

    // 删除购物车中的商品
    @PostMapping("/remove-from-cart")
    public String removeFromCart(@RequestParam String name, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Product.CartItem> cart = (List<Product.CartItem>) session.getAttribute("cart");

        if (cart != null) {
            cart.removeIf(item -> item.getName().equals(name));
        }

        return "redirect:/cart";
    }

    // 更新购物车中商品数量
    @PostMapping("/update-cart")
    public String updateCart(@RequestParam String name,
                             @RequestParam Integer quantity,
                             HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Product.CartItem> cart = (List<Product.CartItem>) session.getAttribute("cart");

        if (cart != null) {
            for (Product.CartItem item : cart) {
                if (item.getName().equals(name)) {
                    if (quantity <= 0) {
                        cart.remove(item);
                    } else {
                        item.setQuantity(quantity);
                    }
                    break;
                }
            }
        }

        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<Product.CartItem> cart = (List<Product.CartItem>) session.getAttribute("cart");
        model.addAttribute("cartItems", cart != null ? cart : new ArrayList<>());
        return "cart";
    }
}
