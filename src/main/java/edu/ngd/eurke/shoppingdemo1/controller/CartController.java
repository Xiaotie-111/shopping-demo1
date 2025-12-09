//package edu.ngd.eurke.shoppingdemo1.controller;
//
//import edu.ngd.eurke.shoppingdemo1.Product;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//@Controller
//public class CartController {
//
//    @PostMapping("/add-to-cart")
//    public String addToCart(@RequestParam String name,
//                            @RequestParam Double price,
//                            @RequestParam(defaultValue = "1") Integer quantity,
//                            HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        String username = (String) session.getAttribute("username");
//
//        if (username == null) {
//            return "redirect:/login?role=user";
//        }
//
//        List<Product.CartItem> cart = (List<Product.CartItem>) session.getAttribute("cart");
//        if (cart == null) {
//            cart = new ArrayList<>();
//            session.setAttribute("cart", cart);
//        }
//
//        // 查找是否已有该商品
//        for (Product.CartItem item : cart) {
//            if (item.getName().equals(name)) {
//                item.setQuantity(item.getQuantity() + quantity);
//                return "redirect:/";
//            }
//        }
//
//        // 新增
//        Product.CartItem newItem = new Product.CartItem(name, price, quantity);
//        cart.add(newItem);
//
//        return "redirect:/";
//    }
//
//    // 删除购物车中的商品
//    @PostMapping("/remove-from-cart")
//    public String removeFromCart(@RequestParam String name, HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        List<Product.CartItem> cart = (List<Product.CartItem>) session.getAttribute("cart");
//
//        if (cart != null) {
//            cart.removeIf(item -> item.getName().equals(name));
//        }
//
//        return "redirect:/cart";
//    }
//
//    // 更新购物车中商品数量
//    @PostMapping("/update-cart")
//    public String updateCart(@RequestParam String name,
//                             @RequestParam Integer quantity,
//                             HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        List<Product.CartItem> cart = (List<Product.CartItem>) session.getAttribute("cart");
//
//        if (cart != null) {
//            // 使用 Iterator 避免并发修改异常
//            Iterator<Product.CartItem> iterator = cart.iterator();
//            while (iterator.hasNext()) {
//                Product.CartItem item = iterator.next();
//                if (item.getName().equals(name)) {
//                    if (quantity <= 0) {
//                        iterator.remove(); // 安全地移除元素
//                    } else {
//                        item.setQuantity(quantity);
//                    }
//                    break;
//                }
//            }
//        }
//
//        return "redirect:/cart";
//    }
//
//    @GetMapping("/cart")
//    public String viewCart(Model model, HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        List<Product.CartItem> cart = (List<Product.CartItem>) session.getAttribute("cart");
//        model.addAttribute("cartItems", cart != null ? cart : new ArrayList<>());
//        return "cart";
//    }
//}
//

package edu.ngd.eurke.shoppingdemo1.controller;

import edu.ngd.eurke.shoppingdemo1.model.CartItem;
import edu.ngd.eurke.shoppingdemo1.service.CartService;
import edu.ngd.eurke.shoppingdemo1.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @Autowired
    private ProductService productService;
    
    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getCartItems());
        model.addAttribute("totalPrice", cartService.getTotalPrice());
        return "cart";
    }
    
    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long productId, @RequestParam(defaultValue = "1") int quantity) {
        productService.findById(productId).ifPresent(product -> {
            cartService.addToCart(product, quantity);
        });
        return "redirect:/";
    }
    
    @PostMapping("/update-cart")
    public String updateCart(@RequestParam Long productId, @RequestParam int quantity) {
        cartService.updateQuantity(productId, quantity);
        return "redirect:/cart";
    }
    
    @GetMapping("/remove-from-cart")
    public String removeFromCart(@RequestParam Long productId) {
        cartService.removeFromCart(productId);
        return "redirect:/cart";
    }
    
    @GetMapping("/clear-cart")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/cart";
    }
}

