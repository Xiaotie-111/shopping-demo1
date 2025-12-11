package edu.ngd.platform.service;

import edu.ngd.platform.model.Order;

import java.util.List;

/**
 * 订单服务接口
 */
public interface OrderService {
    /**
     * 创建订单
     * @param order 订单信息
     * @return 是否创建成功
     */
    boolean createOrder(Order order);
    
    /**
     * 更新订单
     * @param order 订单信息
     * @return 是否更新成功
     */
    boolean updateOrder(Order order);
    
    /**
     * 删除订单
     * @param id 订单ID
     * @return 是否删除成功
     */
    boolean deleteOrder(Long id);
    
    /**
     * 根据ID获取订单
     * @param id 订单ID
     * @return 订单信息
     */
    Order getOrderById(Long id);
    
    /**
     * 根据订单号获取订单
     * @param orderNo 订单号
     * @return 订单信息
     */
    Order getOrderByOrderNo(String orderNo);
    
    /**
     * 获取所有订单
     * @return 订单列表
     */
    List<Order> getAllOrders();
    
    /**
     * 根据用户ID获取订单
     * @param userId 用户ID
     * @return 订单列表
     */
    List<Order> getOrdersByUserId(Long userId);
    
    /**
     * 根据状态获取订单
     * @param status 订单状态
     * @return 订单列表
     */
    List<Order> getOrdersByStatus(Integer status);
    
    /**
     * 更新订单状态
     * @param id 订单ID
     * @param status 订单状态
     * @return 是否更新成功
     */
    boolean updateOrderStatus(Long id, Integer status);
    
    /**
     * 支付订单
     * @param orderNo 订单号
     * @param paymentMethod 支付方式
     * @param paymentNo 支付流水号
     * @return 是否支付成功
     */
    boolean payOrder(String orderNo, Integer paymentMethod, String paymentNo);
    
    /**
     * 发货处理
     * @param id 订单ID
     * @param shippingMethod 配送方式
     * @param shippingAddress 配送地址
     * @return 是否发货成功
     */
    boolean shipOrder(Long id, Integer shippingMethod, String shippingAddress);
    
    /**
     * 确认收货
     * @param id 订单ID
     * @return 是否确认成功
     */
    boolean receiveOrder(Long id);
    
    /**
     * 取消订单
     * @param id 订单ID
     * @return 是否取消成功
     */
    boolean cancelOrder(Long id);
    
    /**
     * 退款处理
     * @param id 订单ID
     * @param refundAmount 退款金额
     * @param refundReason 退款原因
     * @return 是否退款成功
     */
    boolean refundOrder(Long id, Double refundAmount, String refundReason);
    
    /**
     * 搜索订单
     * @param keyword 关键词
     * @return 订单列表
     */
    List<Order> searchOrders(String keyword);
}
