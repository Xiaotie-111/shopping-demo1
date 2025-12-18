package edu.ngd.platform.service.impl;

import edu.ngd.platform.model.Cart;
import edu.ngd.platform.model.CartItem;
import edu.ngd.platform.model.Product;
import edu.ngd.platform.service.CartService;
import edu.ngd.platform.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 购物车服务实现类
 */
@Service
public class CartServiceImpl implements CartService {
    
    // 模拟数据库存储，实际项目中应该使用数据库
    private static final ConcurrentHashMap<Long, Cart> CART_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Long, List<CartItem>> CART_ITEM_MAP = new ConcurrentHashMap<>();
    private static Long CURRENT_CART_ID = 1L;
    private static Long CURRENT_CART_ITEM_ID = 1L;
    
    @Autowired
    private ProductService productService;
    
    @Override
    public Cart getCartByUserId(Long userId) {
        // 查找用户的购物车
        for (Cart cart : CART_MAP.values()) {
            if (cart.getUserId().equals(userId)) {
                return cart;
            }
        }
        
        // 如果用户没有购物车，创建一个新的
        Cart newCart = new Cart();
        newCart.setId(CURRENT_CART_ID++);
        newCart.setUserId(userId);
        newCart.setTotalPrice(0.0);
        newCart.setTotalQuantity(0);
        newCart.setCreateTime(new Date());
        newCart.setUpdateTime(new Date());
        CART_MAP.put(newCart.getId(), newCart);
        CART_ITEM_MAP.put(newCart.getId(), new ArrayList<>());
        return newCart;
    }
    
    @Override
    public List<CartItem> getCartItemsByCartId(Long cartId) {
        return CART_ITEM_MAP.getOrDefault(cartId, new ArrayList<>());
    }
    
    @Override
    public boolean addToCart(Long userId, Long productId, Integer quantity) {
        // 获取或创建用户购物车
        Cart cart = getCartByUserId(userId);
        
        // 获取商品信息
        Product product = productService.getById(productId);
        if (product == null) {
            return false;
        }
        
        // 检查库存
        if (product.getStock() < quantity) {
            return false;
        }
        
        // 获取购物车商品项列表
        List<CartItem> cartItems = CART_ITEM_MAP.get(cart.getId());
        
        // 检查购物车中是否已存在该商品
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProductId().equals(productId)) {
                // 更新商品数量
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setUpdateTime(new Date());
                // 更新购物车总价和总数量
                updateCartTotal(cart);
                return true;
            }
        }
        
        // 如果购物车中不存在该商品，创建新的购物车商品项
        CartItem newCartItem = new CartItem();
        newCartItem.setId(CURRENT_CART_ITEM_ID++);
        newCartItem.setCartId(cart.getId());
        newCartItem.setProductId(productId);
        newCartItem.setProductName(product.getName());
        newCartItem.setProductBrand(product.getBrand());
        newCartItem.setProductPrice(product.getPrice());
        newCartItem.setQuantity(quantity);
        newCartItem.setImageUrl(product.getImageUrl());
        newCartItem.setCreateTime(new Date());
        newCartItem.setUpdateTime(new Date());
        
        // 添加到购物车商品项列表
        cartItems.add(newCartItem);
        
        // 更新购物车总价和总数量
        updateCartTotal(cart);
        
        return true;
    }
    
    @Override
    public boolean updateCartItemQuantity(Long cartItemId, Integer quantity) {
        // 遍历所有购物车商品项，查找要更新的商品
        for (List<CartItem> cartItems : CART_ITEM_MAP.values()) {
            for (CartItem cartItem : cartItems) {
                if (cartItem.getId().equals(cartItemId)) {
                    // 更新商品数量
                    cartItem.setQuantity(quantity);
                    cartItem.setUpdateTime(new Date());
                    // 更新购物车总价和总数量
                    Cart cart = CART_MAP.get(cartItem.getCartId());
                    updateCartTotal(cart);
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean removeFromCart(Long cartItemId) {
        // 遍历所有购物车商品项，查找要移除的商品
        for (List<CartItem> cartItems : CART_ITEM_MAP.values()) {
            for (CartItem cartItem : cartItems) {
                if (cartItem.getId().equals(cartItemId)) {
                    // 移除商品
                    cartItems.remove(cartItem);
                    // 更新购物车总价和总数量
                    Cart cart = CART_MAP.get(cartItem.getCartId());
                    updateCartTotal(cart);
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public boolean clearCart(Long cartId) {
        // 清空购物车商品项
        CART_ITEM_MAP.put(cartId, new ArrayList<>());
        // 更新购物车总价和总数量
        Cart cart = CART_MAP.get(cartId);
        if (cart != null) {
            cart.setTotalPrice(0.0);
            cart.setTotalQuantity(0);
            cart.setUpdateTime(new Date());
            CART_MAP.put(cartId, cart);
            return true;
        }
        return false;
    }
    
    @Override
    public Double calculateTotalPrice(Long cartId) {
        List<CartItem> cartItems = CART_ITEM_MAP.getOrDefault(cartId, new ArrayList<>());
        Double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProductPrice() * cartItem.getQuantity();
        }
        return totalPrice;
    }
    
    @Override
    public Integer getCartTotalQuantity(Long cartId) {
        Cart cart = CART_MAP.get(cartId);
        if (cart != null) {
            return cart.getTotalQuantity();
        }
        return 0;
    }
    
    // 更新购物车总价和总数量
    private void updateCartTotal(Cart cart) {
        List<CartItem> cartItems = CART_ITEM_MAP.getOrDefault(cart.getId(), new ArrayList<>());
        Double totalPrice = 0.0;
        Integer totalQuantity = 0;
        
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProductPrice() * cartItem.getQuantity();
            totalQuantity += cartItem.getQuantity();
        }
        
        cart.setTotalPrice(totalPrice);
        cart.setTotalQuantity(totalQuantity);
        cart.setUpdateTime(new Date());
        CART_MAP.put(cart.getId(), cart);
    }
}