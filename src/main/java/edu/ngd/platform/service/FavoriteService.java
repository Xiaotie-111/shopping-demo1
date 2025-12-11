package edu.ngd.platform.service;

import edu.ngd.platform.model.Product;

import java.util.List;

/**
 * 收藏服务接口
 */
public interface FavoriteService {
    /**
     * 获取用户收藏列表
     * @param userId 用户ID
     * @return 收藏的商品列表
     */
    List<Product> getFavoriteList(Long userId);
    
    /**
     * 添加收藏
     * @param userId 用户ID
     * @param productId 商品ID
     * @return 是否添加成功
     */
    boolean addToFavorite(Long userId, Long productId);
    
    /**
     * 取消收藏
     * @param userId 用户ID
     * @param productId 商品ID
     * @return 是否取消成功
     */
    boolean removeFromFavorite(Long userId, Long productId);
    
    /**
     * 检查商品是否已收藏
     * @param userId 用户ID
     * @param productId 商品ID
     * @return 是否已收藏
     */
    boolean isFavorite(Long userId, Long productId);
}