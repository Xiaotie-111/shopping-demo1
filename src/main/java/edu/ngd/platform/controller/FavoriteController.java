package edu.ngd.platform.controller;

import edu.ngd.platform.model.Product;
import edu.ngd.platform.model.User;
import edu.ngd.platform.service.FavoriteService;
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
 * 收藏控制器
 */
@Controller
@RequestMapping("/favorite")
public class FavoriteController {
    
    @Autowired
    private FavoriteService favoriteService;
    
    /**
     * 跳转到收藏页面
     * @param model 模型对象
     * @param session HTTP会话
     * @return 收藏页面视图
     */
    @GetMapping
    public String favorite(Model model, HttpSession session) {
        // 获取当前登录用户
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 未登录，跳转到登录页面
            return "redirect:/login";
        }
        
        // 获取用户收藏列表
        List<Product> favoriteProducts = favoriteService.getFavoriteList(user.getId());
        
        model.addAttribute("favoriteProducts", favoriteProducts);
        return "favorite/list";
    }
    
    /**
     * 添加商品到收藏夹
     * @param productId 商品ID
     * @param session HTTP会话
     * @return 重定向到原页面或收藏页面
     */
    @PostMapping("/add")
    public String addToFavorite(@RequestParam Long productId, 
                              HttpSession session) {
        // 获取当前登录用户
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 未登录，跳转到登录页面
            return "redirect:/login";
        }
        
        // 添加商品到收藏夹
        favoriteService.addToFavorite(user.getId(), productId);
        
        // 获取来源页面，默认为商品详情页
        String referer = (String) session.getAttribute("referer");
        if (referer == null) {
            referer = "/product/detail/" + productId;
        }
        
        return "redirect:" + referer;
    }
    
    /**
     * 从收藏夹中移除商品
     * @param productId 商品ID
     * @param session HTTP会话
     * @return 重定向到收藏页面
     */
    @GetMapping("/remove")
    public String removeFromFavorite(@RequestParam Long productId, 
                                  HttpSession session) {
        // 获取当前登录用户
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 未登录，跳转到登录页面
            return "redirect:/login";
        }
        
        // 从收藏夹中移除商品
        favoriteService.removeFromFavorite(user.getId(), productId);
        return "redirect:/favorite";
    }
}