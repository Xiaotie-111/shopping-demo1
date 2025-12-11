package edu.ngd.platform.service.impl;

import edu.ngd.platform.model.Order;
import edu.ngd.platform.model.OrderItem;
import edu.ngd.platform.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 订单服务实现类
 */
@Service
public class OrderServiceImpl implements OrderService {
    
    // 模拟数据库存储，实际项目中应该使用数据库
    private static final ConcurrentHashMap<Long, Order> ORDER_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Long, OrderItem> ORDER_ITEM_MAP = new ConcurrentHashMap<>();
    private static Long CURRENT_ORDER_ID = 1L;
    private static Long CURRENT_ORDER_ITEM_ID = 1L;
    
    // 初始化一些测试数据
    static {
        // 创建订单
        Order order = new Order();
        order.setId(CURRENT_ORDER_ID++);
        order.setOrderNo("ORD202512100001");
        order.setUserId(1L);
        order.setUserName("测试用户");
        order.setTotalAmount(7999.0);
        order.setActualAmount(7999.0);
        order.setStatus(1); // 待支付
        order.setPaymentMethod(0);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        order.setCreateBy("system");
        order.setUpdateBy("system");
        ORDER_MAP.put(order.getId(), order);
        
        // 创建订单项
        OrderItem orderItem = new OrderItem();
        orderItem.setId(CURRENT_ORDER_ITEM_ID++);
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(1L);
        orderItem.setProductName("iPhone 15 Pro");
        orderItem.setProductCode("IP15P001");
        orderItem.setPrice(7999.0);
        orderItem.setQuantity(1);
        orderItem.setTotalPrice(7999.0);
        ORDER_ITEM_MAP.put(orderItem.getId(), orderItem);
        
        // 添加订单项到订单
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        order.setOrderItems(orderItems);
    }
    
    @Override
    public boolean createOrder(Order order) {
        order.setId(CURRENT_ORDER_ID++);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        ORDER_MAP.put(order.getId(), order);
        
        // 保存订单项
        if (order.getOrderItems() != null) {
            for (OrderItem orderItem : order.getOrderItems()) {
                orderItem.setId(CURRENT_ORDER_ITEM_ID++);
                orderItem.setOrderId(order.getId());
                ORDER_ITEM_MAP.put(orderItem.getId(), orderItem);
            }
        }
        
        return true;
    }
    
    @Override
    public boolean updateOrder(Order order) {
        Order existing = ORDER_MAP.get(order.getId());
        if (existing == null) {
            return false;
        }
        order.setUpdateTime(new Date());
        ORDER_MAP.put(order.getId(), order);
        return true;
    }
    
    @Override
    public boolean deleteOrder(Long id) {
        return ORDER_MAP.remove(id) != null;
    }
    
    @Override
    public Order getOrderById(Long id) {
        Order order = ORDER_MAP.get(id);
        if (order != null) {
            // 加载订单项
            List<OrderItem> orderItems = getOrderItemsByOrderId(id);
            order.setOrderItems(orderItems);
        }
        return order;
    }
    
    @Override
    public Order getOrderByOrderNo(String orderNo) {
        Order order = ORDER_MAP.values().stream()
                .filter(o -> o.getOrderNo().equals(orderNo))
                .findFirst()
                .orElse(null);
        
        if (order != null) {
            // 加载订单项
            List<OrderItem> orderItems = getOrderItemsByOrderId(order.getId());
            order.setOrderItems(orderItems);
        }
        
        return order;
    }
    
    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>(ORDER_MAP.values());
        // 加载每个订单的订单项
        for (Order order : orders) {
            List<OrderItem> orderItems = getOrderItemsByOrderId(order.getId());
            order.setOrderItems(orderItems);
        }
        return orders;
    }
    
    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        List<Order> orders = ORDER_MAP.values().stream()
                .filter(order -> order.getUserId().equals(userId))
                .collect(Collectors.toList());
        // 加载每个订单的订单项
        for (Order order : orders) {
            List<OrderItem> orderItems = getOrderItemsByOrderId(order.getId());
            order.setOrderItems(orderItems);
        }
        return orders;
    }
    
    @Override
    public List<Order> getOrdersByStatus(Integer status) {
        List<Order> orders = ORDER_MAP.values().stream()
                .filter(order -> order.getStatus().equals(status))
                .collect(Collectors.toList());
        // 加载每个订单的订单项
        for (Order order : orders) {
            List<OrderItem> orderItems = getOrderItemsByOrderId(order.getId());
            order.setOrderItems(orderItems);
        }
        return orders;
    }
    
    @Override
    public boolean updateOrderStatus(Long id, Integer status) {
        Order order = ORDER_MAP.get(id);
        if (order == null) {
            return false;
        }
        order.setStatus(status);
        order.setUpdateTime(new Date());
        ORDER_MAP.put(id, order);
        return true;
    }
    
    @Override
    public boolean payOrder(String orderNo, Integer paymentMethod, String paymentNo) {
        Order order = getOrderByOrderNo(orderNo);
        if (order == null) {
            return false;
        }
        order.setStatus(2); // 已支付
        order.setPaymentMethod(paymentMethod);
        order.setPaymentNo(paymentNo);
        order.setPaymentTime(new Date());
        order.setUpdateTime(new Date());
        ORDER_MAP.put(order.getId(), order);
        return true;
    }
    
    @Override
    public boolean shipOrder(Long id, Integer shippingMethod, String shippingAddress) {
        Order order = ORDER_MAP.get(id);
        if (order == null) {
            return false;
        }
        order.setStatus(3); // 已发货
        order.setShippingMethod(shippingMethod);
        order.setShippingAddress(shippingAddress);
        order.setShippingTime(new Date());
        order.setUpdateTime(new Date());
        ORDER_MAP.put(id, order);
        return true;
    }
    
    @Override
    public boolean receiveOrder(Long id) {
        Order order = ORDER_MAP.get(id);
        if (order == null) {
            return false;
        }
        order.setStatus(4); // 已完成
        order.setReceiveTime(new Date());
        order.setUpdateTime(new Date());
        ORDER_MAP.put(id, order);
        return true;
    }
    
    @Override
    public boolean cancelOrder(Long id) {
        Order order = ORDER_MAP.get(id);
        if (order == null) {
            return false;
        }
        order.setStatus(5); // 已取消
        order.setUpdateTime(new Date());
        ORDER_MAP.put(id, order);
        return true;
    }
    
    @Override
    public boolean refundOrder(Long id, Double refundAmount, String refundReason) {
        Order order = ORDER_MAP.get(id);
        if (order == null) {
            return false;
        }
        order.setStatus(6); // 已退款
        order.setUpdateTime(new Date());
        ORDER_MAP.put(id, order);
        return true;
    }
    
    @Override
    public List<Order> searchOrders(String keyword) {
        List<Order> orders = ORDER_MAP.values().stream()
                .filter(order -> order.getOrderNo().contains(keyword) || 
                        order.getUserName().contains(keyword) || 
                        order.getShippingAddress().contains(keyword))
                .collect(Collectors.toList());
        // 加载每个订单的订单项
        for (Order order : orders) {
            List<OrderItem> orderItems = getOrderItemsByOrderId(order.getId());
            order.setOrderItems(orderItems);
        }
        return orders;
    }
    
    // 根据订单ID获取订单项
    private List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return ORDER_ITEM_MAP.values().stream()
                .filter(orderItem -> orderItem.getOrderId().equals(orderId))
                .collect(Collectors.toList());
    }
}