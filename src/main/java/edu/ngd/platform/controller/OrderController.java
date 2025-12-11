package edu.ngd.platform.controller;

import edu.ngd.platform.model.Order;
import edu.ngd.platform.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单管理控制器
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    /**
     * 跳转到订单列表页面
     * @param model 模型对象
     * @param page 当前页码，默认为1
     * @return 订单列表页面视图
     */
    @GetMapping("/list")
    public String orderList(Model model, @RequestParam(defaultValue = "1") Integer page) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", 3); // 模拟总页数为3
        return "order/list";
    }
    
    /**
     * 跳转到订单详情页面
     * @param id 订单ID
     * @param model 模型对象
     * @return 订单详情页面视图
     */
    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
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
     * @return 订单支付页面视图
     */
    @GetMapping("/pay/{id}")
    public String orderPay(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        model.addAttribute("order", order);
        return "order/pay";
    }
    
    /**
     * 处理订单支付请求
     * @param id 订单ID
     * @param paymentMethod 支付方式
     * @param paymentNo 支付流水号
     * @return 重定向到订单列表页面
     */
    @PostMapping("/pay")
    public String doOrderPay(@RequestParam Long id, @RequestParam Integer paymentMethod, @RequestParam String paymentNo) {
        Order order = orderService.getOrderById(id);
        orderService.payOrder(order.getOrderNo(), paymentMethod, paymentNo);
        return "redirect:/order/list";
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
}