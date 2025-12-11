package edu.ngd.platform.controller;

import edu.ngd.platform.model.Cart;
import edu.ngd.platform.model.CartItem;
import edu.ngd.platform.model.User;
import edu.ngd.platform.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 购物车控制器
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    /**
     * 跳转到购物车页面
     * @param model 模型对象
     * @param session HTTP会话
     * @return 购物车页面视图
     */
    @GetMapping
    public String cart(Model model, HttpSession session) {
        // 获取当前登录用户
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 未登录，跳转到登录页面
            return "redirect:/login";
        }
        
        // 获取用户购物车
        Cart cart = cartService.getCartByUserId(user.getId());
        List<CartItem> cartItems = cartService.getCartItemsByCartId(cart.getId());
        
        model.addAttribute("cart", cart);
        model.addAttribute("cartItems", cartItems);
        return "cart/list";
    }
    
    /**
     * 添加商品到购物车
     * @param productId 商品ID
     * @param quantity 商品数量
     * @param session HTTP会话
     * @return 重定向到购物车页面
     */
    @PostMapping("/add")
    public String addToCart(@RequestParam Long productId, 
                          @RequestParam(defaultValue = "1") Integer quantity, 
                          HttpSession session) {
        // 获取当前登录用户
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 未登录，跳转到登录页面
            return "redirect:/login";
        }
        
        // 添加商品到购物车
        cartService.addToCart(user.getId(), productId, quantity);
        return "redirect:/cart";
    }
    
    /**
     * 更新购物车商品数量
     * @param cartItemId 购物车商品项ID
     * @param quantity 商品数量
     * @return 重定向到购物车页面
     */
    @PostMapping("/update")
    public String updateCartItem(@RequestParam Long cartItemId, 
                               @RequestParam Integer quantity) {
        // 更新购物车商品数量
        cartService.updateCartItemQuantity(cartItemId, quantity);
        return "redirect:/cart";
    }
    
    /**
     * 从购物车中移除商品
     * @param cartItemId 购物车商品项ID
     * @return 重定向到购物车页面
     */
    @GetMapping("/remove")
    public String removeFromCart(@RequestParam Long cartItemId) {
        // 从购物车中移除商品
        cartService.removeFromCart(cartItemId);
        return "redirect:/cart";
    }
    
    /**
     * 清空购物车
     * @param session HTTP会话
     * @return 重定向到购物车页面
     */
    @GetMapping("/clear")
    public String clearCart(HttpSession session) {
        // 获取当前登录用户
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 未登录，跳转到登录页面
            return "redirect:/login";
        }
        
        // 获取用户购物车
        Cart cart = cartService.getCartByUserId(user.getId());
        // 清空购物车
        cartService.clearCart(cart.getId());
        return "redirect:/cart";
    }
}