package edu.ngd.platform.service;

import edu.ngd.platform.model.Cart;
import edu.ngd.platform.model.CartItem;

import java.util.List;

/**
 * 购物车服务接口
 */
public interface CartService {
    /**
     * 根据用户ID获取购物车
     * @param userId 用户ID
     * @return 购物车信息
     */
    Cart getCartByUserId(Long userId);
    
    /**
     * 获取购物车商品项列表
     * @param cartId 购物车ID
     * @return 购物车商品项列表
     */
    List<CartItem> getCartItemsByCartId(Long cartId);
    
    /**
     * 添加商品到购物车
     * @param userId 用户ID
     * @param productId 商品ID
     * @param quantity 商品数量
     * @return 是否添加成功
     */
    boolean addToCart(Long userId, Long productId, Integer quantity);
    
    /**
     * 更新购物车商品数量
     * @param cartItemId 购物车商品项ID
     * @param quantity 商品数量
     * @return 是否更新成功
     */
    boolean updateCartItemQuantity(Long cartItemId, Integer quantity);
    
    /**
     * 从购物车中移除商品
     * @param cartItemId 购物车商品项ID
     * @return 是否移除成功
     */
    boolean removeFromCart(Long cartItemId);
    
    /**
     * 清空购物车
     * @param cartId 购物车ID
     * @return 是否清空成功
     */
    boolean clearCart(Long cartId);
    
    /**
     * 计算购物车总价
     * @param cartId 购物车ID
     * @return 购物车总价
     */
    Double calculateTotalPrice(Long cartId);
}