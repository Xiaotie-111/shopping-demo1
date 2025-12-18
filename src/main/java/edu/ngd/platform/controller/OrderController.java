package edu.ngd.platform.controller;

import edu.ngd.platform.model.Order;
import edu.ngd.platform.model.OrderItem;
import edu.ngd.platform.model.Cart;
import edu.ngd.platform.model.CartItem;
import edu.ngd.platform.model.User;
import edu.ngd.platform.service.OrderService;
import edu.ngd.platform.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 订单管理控制器
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private CartService cartService;
    
    /**
     * 跳转到订单列表页面
     * @param model 模型对象
     * @param page 当前页码，默认为1
     * @param session HTTP会话
     * @return 订单列表页面视图
     */
    @GetMapping("/list")
    public String orderList(Model model, @RequestParam(defaultValue = "1") Integer page, HttpSession session) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", 3); // 模拟总页数为3
        
        // 从会话中获取用户信息
        Object user = session.getAttribute("user");
        model.addAttribute("user", user);
        
        // 获取购物车总数量
        Integer cartTotalQuantity = 0;
        if (user != null) {
            User loggedUser = (User) user;
            Cart cart = cartService.getCartByUserId(loggedUser.getId());
            if (cart != null) {
                cartTotalQuantity = cartService.getCartTotalQuantity(cart.getId());
            }
        }
        model.addAttribute("cartTotalQuantity", cartTotalQuantity);
        
        return "order/list";
    }
    
    /**
     * 跳转到管理员订单列表页面
     * @param model 模型对象
     * @return 管理员订单列表页面视图
     */
    @GetMapping("/admin_list")
    public String adminOrderList(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "order/admin_list";
    }
    
    /**
     * 跳转到订单详情页面
     * @param id 订单ID
     * @param model 模型对象
     * @param session HTTP会话
     * @return 订单详情页面视图
     */
    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable Long id, Model model, HttpSession session) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        
        // 从会话中获取用户信息
        Object user = session.getAttribute("user");
        model.addAttribute("user", user);
        
        // 获取购物车总数量
        Integer cartTotalQuantity = 0;
        if (user != null) {
            User loggedUser = (User) user;
            Cart cart = cartService.getCartByUserId(loggedUser.getId());
            if (cart != null) {
                cartTotalQuantity = cartService.getCartTotalQuantity(cart.getId());
            }
        }
        model.addAttribute("cartTotalQuantity", cartTotalQuantity);
        
        return "order/detail";
    }
    
    /**
     * 跳转到订单添加页面
     * @return 订单添加页面视图
     */
    @GetMapping("/add")
    public String orderAdd() {
        return "order/add";
    }
    
    /**
     * 处理订单添加请求
     * @param order 订单信息
     * @return 重定向到订单列表页面
     */
    @PostMapping("/add")
    public String doOrderAdd(Order order) {
        orderService.createOrder(order);
        return "redirect:/order/list";
    }
    
    /**
     * 处理订单删除请求
     * @param id 订单ID
     * @return 重定向到订单列表页面
     */
    @GetMapping("/delete/{id}")
    public String orderDelete(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/order/list";
    }
    
    /**
     * 跳转到订单编辑页面
     * @param id 订单ID
     * @param model 模型对象
     * @return 订单编辑页面视图
     */
    @GetMapping("/edit/{id}")
    public String orderEdit(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "order/edit";
    }
    
    /**
     * 处理订单编辑请求
     * @param order 订单信息
     * @return 重定向到订单列表页面
     */
    @PostMapping("/edit")
    public String doOrderEdit(Order order) {
        orderService.updateOrder(order);
        return "redirect:/order/list";
    }
    
    /**
     * 处理订单状态更新请求
     * @param id 订单ID
     * @param status 订单状态
     * @return 重定向到订单列表页面
     */
    @GetMapping("/updateStatus/{id}/{status}")
    public String updateOrderStatus(@PathVariable Long id, @PathVariable Integer status) {
        orderService.updateOrderStatus(id, status);
        return "redirect:/order/list";
    }
    
    /**
     * 跳转到订单支付页面
     * @param id 订单ID
     * @param model 模型对象
     * @param session HTTP会话
     * @return 订单支付页面视图
     */
    @GetMapping("/pay/{id}")
    public String orderPay(@PathVariable Long id, Model model, HttpSession session) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        
        // 从会话中获取用户信息
        Object user = session.getAttribute("user");
        model.addAttribute("user", user);
        
        // 获取购物车总数量
        Integer cartTotalQuantity = 0;
        if (user != null) {
            User loggedUser = (User) user;
            Cart cart = cartService.getCartByUserId(loggedUser.getId());
            if (cart != null) {
                cartTotalQuantity = cartService.getCartTotalQuantity(cart.getId());
            }
        }
        model.addAttribute("cartTotalQuantity", cartTotalQuantity);
        
        return "order/pay";
    }
    
    /**
     * 处理订单支付请求
     * @param id 订单ID
     * @param paymentMethod 支付方式
     * @param paymentNo 支付流水号，可选，默认系统生成
     * @return 重定向到订单列表页面
     */
    @PostMapping("/pay")
    public String doOrderPay(@RequestParam Long id, @RequestParam Integer paymentMethod, @RequestParam(required = false) String paymentNo) {
        Order order = orderService.getOrderById(id);
        // 生成支付流水号（如果未提供）
        if (paymentNo == null || paymentNo.isEmpty()) {
            paymentNo = "PAY" + System.currentTimeMillis();
        }
        orderService.payOrder(order.getOrderNo(), paymentMethod, paymentNo);
        return "redirect:/order/confirm";
    }
    
    /**
     * 跳转到订单发货页面
     * @param id 订单ID
     * @param model 模型对象
     * @return 订单发货页面视图
     */
    @GetMapping("/ship/{id}")
    public String orderShip(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "order/ship";
    }
    
    /**
     * 处理订单发货请求
     * @param id 订单ID
     * @param shippingMethod 配送方式
     * @param shippingAddress 配送地址
     * @return 重定向到订单列表页面
     */
    @PostMapping("/ship")
    public String doOrderShip(@RequestParam Long id, @RequestParam Integer shippingMethod, @RequestParam String shippingAddress) {
        orderService.shipOrder(id, shippingMethod, shippingAddress);
        return "redirect:/order/list";
    }
    
    /**
     * 处理订单确认收货请求
     * @param id 订单ID
     * @return 重定向到订单列表页面
     */
    @GetMapping("/receive/{id}")
    public String orderReceive(@PathVariable Long id) {
        orderService.receiveOrder(id);
        return "redirect:/order/list";
    }
    
    /**
     * 处理订单取消请求
     * @param id 订单ID
     * @return 重定向到订单列表页面
     */
    @GetMapping("/cancel/{id}")
    public String orderCancel(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return "redirect:/order/list";
    }
    
    /**
     * 处理订单退款请求
     * @param id 订单ID
     * @return 重定向到订单列表页面
     */
    @GetMapping("/refund/{id}")
    public String orderRefund(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        orderService.refundOrder(id, order.getActualAmount(), "用户申请退款");
        return "redirect:/order/list";
    }
    
    /**
     * 处理订单搜索请求
     * @param keyword 关键词
     * @param model 模型对象
     * @return 订单列表页面视图
     */
    @GetMapping("/search")
    public String searchOrder(@RequestParam String keyword, Model model) {
        List<Order> orders = orderService.searchOrders(keyword);
        model.addAttribute("orders", orders);
        model.addAttribute("keyword", keyword);
        return "order/list";
    }
    
    /**
     * 处理订单提交请求
     * @param consignee 收货人
     * @param phone 联系电话
     * @param address 收货地址
     * @param paymentMethod 支付方式
     * @param session HTTP会话
     * @return 重定向到订单支付页面
     */
    @PostMapping("/submit")
    public String submitOrder(@RequestParam("consignee") String consignee,
                             @RequestParam("phone") String phone,
                             @RequestParam("address") String address,
                             @RequestParam("paymentMethod") Integer paymentMethod,
                             HttpSession session) {
        // 获取当前登录用户
        User user = (User) session.getAttribute("user");
        if (user == null) {
            // 未登录，跳转到登录页面
            return "redirect:/login";
        }
        
        // 获取用户购物车
        Cart cart = cartService.getCartByUserId(user.getId());
        List<CartItem> cartItems = cartService.getCartItemsByCartId(cart.getId());
        
        // 创建订单对象
        Order order = new Order();
        order.setOrderNo("ORD" + System.currentTimeMillis());
        order.setUserId(user.getId());
        order.setUserName(consignee); // 使用收货人作为用户名
        
        // 计算订单总金额
        Double totalAmount = cart.getTotalPrice();
        order.setTotalAmount(totalAmount);
        order.setActualAmount(totalAmount); // 实际金额等于总金额
        order.setStatus(1); // 待支付
        order.setPaymentMethod(paymentMethod);
        order.setShippingAddress(address);
        order.setRemark("从购物车结算");
        
        // 将购物车商品项转换为订单项
        List<OrderItem> orderItems = new java.util.ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setProductName(cartItem.getProductName());
            orderItem.setProductBrand(cartItem.getProductBrand());
            orderItem.setPrice(cartItem.getProductPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setTotalPrice(cartItem.getProductPrice() * cartItem.getQuantity());
            orderItem.setImageUrl(cartItem.getImageUrl());
            orderItems.add(orderItem);
        }
        
        // 将订单项添加到订单
        order.setOrderItems(orderItems);
        
        // 保存订单
        orderService.createOrder(order);
        
        // 重定向到订单支付页面
        return "redirect:/order/pay/" + order.getId();
    }
    
    /**
     * 跳转到订单支付成功页面
     * @return 订单支付成功页面视图
     */
    @GetMapping("/confirm")
    public String orderConfirm() {
        return "order/confirm";
    }
}