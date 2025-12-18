package edu.ngd.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.ngd.platform.mapper.OrderItemMapper;
import edu.ngd.platform.mapper.OrderMapper;
import edu.ngd.platform.model.Order;
import edu.ngd.platform.model.OrderItem;
import edu.ngd.platform.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 订单服务实现类
 */
@Service
public class OrderServiceImpl implements OrderService {
    
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private OrderItemMapper orderItemMapper;
    
    @Override
    @Transactional
    public boolean createOrder(Order order) {
        // 设置创建时间和更新时间
        Date now = new Date();
        order.setCreateTime(now);
        order.setUpdateTime(now);
        
        // 保存订单
        int orderResult = orderMapper.insert(order);
        
        // 保存订单项
        if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
            for (OrderItem item : order.getOrderItems()) {
                item.setOrderId(order.getId());
                orderItemMapper.insert(item);
            }
        }
        
        return orderResult > 0;
    }
    
    @Override
    @Transactional
    public boolean updateOrder(Order order) {
        order.setUpdateTime(new Date());
        int result = orderMapper.updateById(order);
        return result > 0;
    }
    
    @Override
    @Transactional
    public boolean deleteOrder(Long id) {
        // 先删除订单项
        QueryWrapper<OrderItem> itemWrapper = new QueryWrapper<>();
        itemWrapper.eq("order_id", id);
        orderItemMapper.delete(itemWrapper);
        
        // 再删除订单
        int result = orderMapper.deleteById(id);
        return result > 0;
    }
    
    @Override
    public Order getOrderById(Long id) {
        Order order = orderMapper.selectById(id);
        if (order != null) {
            // 加载订单项
            QueryWrapper<OrderItem> itemWrapper = new QueryWrapper<>();
            itemWrapper.eq("order_id", id);
            List<OrderItem> orderItems = orderItemMapper.selectList(itemWrapper);
            order.setOrderItems(orderItems);
        }
        return order;
    }
    
    @Override
    public Order getOrderByOrderNo(String orderNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderMapper.selectOne(wrapper);
        
        if (order != null) {
            // 加载订单项
            QueryWrapper<OrderItem> itemWrapper = new QueryWrapper<>();
            itemWrapper.eq("order_id", order.getId());
            List<OrderItem> orderItems = orderItemMapper.selectList(itemWrapper);
            order.setOrderItems(orderItems);
        }
        
        return order;
    }
    
    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderMapper.selectList(null);
        // 加载每个订单的订单项
        for (Order order : orders) {
            QueryWrapper<OrderItem> itemWrapper = new QueryWrapper<>();
            itemWrapper.eq("order_id", order.getId());
            List<OrderItem> orderItems = orderItemMapper.selectList(itemWrapper);
            order.setOrderItems(orderItems);
        }
        return orders;
    }
    
    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<Order> orders = orderMapper.selectList(wrapper);
        
        // 加载每个订单的订单项
        for (Order order : orders) {
            QueryWrapper<OrderItem> itemWrapper = new QueryWrapper<>();
            itemWrapper.eq("order_id", order.getId());
            List<OrderItem> orderItems = orderItemMapper.selectList(itemWrapper);
            order.setOrderItems(orderItems);
        }
        
        return orders;
    }
    
    @Override
    public List<Order> getOrdersByStatus(Integer status) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("status", status);
        List<Order> orders = orderMapper.selectList(wrapper);
        
        // 加载每个订单的订单项
        for (Order order : orders) {
            QueryWrapper<OrderItem> itemWrapper = new QueryWrapper<>();
            itemWrapper.eq("order_id", order.getId());
            List<OrderItem> orderItems = orderItemMapper.selectList(itemWrapper);
            order.setOrderItems(orderItems);
        }
        
        return orders;
    }
    
    @Override
    @Transactional
    public boolean updateOrderStatus(Long id, Integer status) {
        Order order = new Order();
        order.setId(id);
        order.setStatus(status);
        order.setUpdateTime(new Date());
        int result = orderMapper.updateById(order);
        return result > 0;
    }
    
    @Override
    @Transactional
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
        
        int result = orderMapper.updateById(order);
        return result > 0;
    }
    
    @Override
    @Transactional
    public boolean shipOrder(Long id, Integer shippingMethod, String shippingAddress) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            return false;
        }
        
        order.setStatus(3); // 已发货
        order.setShippingMethod(shippingMethod);
        order.setShippingAddress(shippingAddress);
        order.setShippingTime(new Date());
        order.setUpdateTime(new Date());
        
        int result = orderMapper.updateById(order);
        return result > 0;
    }
    
    @Override
    @Transactional
    public boolean receiveOrder(Long id) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            return false;
        }
        
        order.setStatus(4); // 已完成
        order.setReceiveTime(new Date());
        order.setUpdateTime(new Date());
        
        int result = orderMapper.updateById(order);
        return result > 0;
    }
    
    @Override
    @Transactional
    public boolean cancelOrder(Long id) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            return false;
        }
        
        order.setStatus(5); // 已取消
        order.setUpdateTime(new Date());
        
        int result = orderMapper.updateById(order);
        return result > 0;
    }
    
    @Override
    @Transactional
    public boolean refundOrder(Long id, Double refundAmount, String refundReason) {
        Order order = orderMapper.selectById(id);
        if (order == null) {
            return false;
        }
        
        order.setStatus(6); // 已退款
        order.setUpdateTime(new Date());
        
        int result = orderMapper.updateById(order);
        return result > 0;
    }
    
    @Override
    public List<Order> searchOrders(String keyword) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.like("order_no", keyword)
               .or().like("user_name", keyword)
               .or().like("shipping_address", keyword);
        
        List<Order> orders = orderMapper.selectList(wrapper);
        
        // 加载每个订单的订单项
        for (Order order : orders) {
            QueryWrapper<OrderItem> itemWrapper = new QueryWrapper<>();
            itemWrapper.eq("order_id", order.getId());
            List<OrderItem> orderItems = orderItemMapper.selectList(itemWrapper);
            order.setOrderItems(orderItems);
        }
        
        return orders;
    }
}