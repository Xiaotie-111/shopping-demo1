package edu.ngd.platform.service.impl;

import edu.ngd.platform.model.Favorite;
import edu.ngd.platform.model.Product;
import edu.ngd.platform.service.FavoriteService;
import edu.ngd.platform.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 收藏服务实现类
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {
    
    // 模拟数据库存储，实际项目中应该使用数据库
    private static final ConcurrentHashMap<Long, List<Favorite>> FAVORITE_MAP = new ConcurrentHashMap<>();
    private static Long CURRENT_FAVORITE_ID = 1L;
    
    @Autowired
    private ProductService productService;
    
    @Override
    public List<Product> getFavoriteList(Long userId) {
        List<Product> favoriteProducts = new ArrayList<>();
        List<Favorite> favorites = FAVORITE_MAP.getOrDefault(userId, new ArrayList<>());
        
        for (Favorite favorite : favorites) {
            Product product = productService.getById(favorite.getProductId());
            if (product != null) {
                favoriteProducts.add(product);
            }
        }
        
        return favoriteProducts;
    }
    
    @Override
    public boolean addToFavorite(Long userId, Long productId) {
        // 检查商品是否已收藏
        if (isFavorite(userId, productId)) {
            return false;
        }
        
        // 获取用户收藏列表，不存在则创建
        List<Favorite> favorites = FAVORITE_MAP.getOrDefault(userId, new ArrayList<>());
        
        // 创建收藏记录
        Favorite favorite = new Favorite();
        favorite.setId(CURRENT_FAVORITE_ID++);
        favorite.setUserId(userId);
        favorite.setProductId(productId);
        favorite.setCreateTime(new Date());
        
        // 添加到收藏列表
        favorites.add(favorite);
        FAVORITE_MAP.put(userId, favorites);
        return true;
    }
    
    @Override
    public boolean removeFromFavorite(Long userId, Long productId) {
        // 获取用户收藏列表
        List<Favorite> favorites = FAVORITE_MAP.getOrDefault(userId, new ArrayList<>());
        
        // 查找并移除收藏记录
        for (Favorite favorite : favorites) {
            if (favorite.getProductId().equals(productId)) {
                favorites.remove(favorite);
                FAVORITE_MAP.put(userId, favorites);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isFavorite(Long userId, Long productId) {
        // 获取用户收藏列表
        List<Favorite> favorites = FAVORITE_MAP.getOrDefault(userId, new ArrayList<>());
        
        // 检查商品是否已收藏
        for (Favorite favorite : favorites) {
            if (favorite.getProductId().equals(productId)) {
                return true;
            }
        }
        return false;
    }
    

}