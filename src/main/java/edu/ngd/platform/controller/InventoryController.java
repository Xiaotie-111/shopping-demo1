package edu.ngd.platform.controller;

import edu.ngd.platform.model.Inventory;
import edu.ngd.platform.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 库存管理控制器
 */
@Controller
@RequestMapping("/inventory")
public class InventoryController {
    
    @Autowired
    private InventoryService inventoryService;
    
    /**
     * 跳转到库存列表页面
     * @param model 模型对象
     * @param page 当前页码，默认为1
     * @return 库存列表页面视图
     */
    @GetMapping("/list")
    public String inventoryList(Model model, @RequestParam(defaultValue = "1") Integer page) {
        List<Inventory> inventories = inventoryService.getAllInventories();
        model.addAttribute("inventories", inventories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", 3); // 模拟总页数为3
        return "inventory/list";
    }
    
    /**
     * 跳转到库存添加页面
     * @return 库存添加页面视图
     */
    @GetMapping("/add")
    public String inventoryAdd() {
        return "inventory/add";
    }
    
    /**
     * 处理库存添加请求
     * @param inventory 库存信息
     * @return 重定向到库存列表页面
     */
    @PostMapping("/add")
    public String doInventoryAdd(Inventory inventory) {
        inventoryService.addInventory(inventory);
        return "redirect:/inventory/list";
    }
    
    /**
     * 跳转到库存编辑页面
     * @param id 库存ID
     * @param model 模型对象
     * @return 库存编辑页面视图
     */
    @GetMapping("/edit/{id}")
    public String inventoryEdit(@PathVariable Long id, Model model) {
        Inventory inventory = inventoryService.getInventoryById(id);
        model.addAttribute("inventory", inventory);
        return "inventory/edit";
    }
    
    /**
     * 处理库存编辑请求
     * @param inventory 库存信息
     * @return 重定向到库存列表页面
     */
    @PostMapping("/edit")
    public String doInventoryEdit(Inventory inventory) {
        inventoryService.updateInventory(inventory);
        return "redirect:/inventory/list";
    }
    
    /**
     * 处理库存删除请求
     * @param id 库存ID
     * @return 重定向到库存列表页面
     */
    @GetMapping("/delete/{id}")
    public String inventoryDelete(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return "redirect:/inventory/list";
    }
    
    /**
     * 跳转到库存调整页面
     * @param id 库存ID
     * @param model 模型对象
     * @return 库存调整页面视图
     */
    @GetMapping("/adjust/{id}")
    public String inventoryAdjust(@PathVariable Long id, Model model) {
        Inventory inventory = inventoryService.getInventoryById(id);
        model.addAttribute("inventory", inventory);
        return "inventory/adjust";
    }
    
    /**
     * 处理库存调整请求
     * @param productId 商品ID
     * @param quantity 调整数量
     * @return 重定向到库存列表页面
     */
    @PostMapping("/adjust")
    public String doInventoryAdjust(@RequestParam Long productId, @RequestParam Integer quantity) {
        inventoryService.adjustInventory(productId, quantity);
        return "redirect:/inventory/list";
    }
    
    /**
     * 跳转到库存预警页面
     * @param model 模型对象
     * @return 库存预警页面视图
     */
    @GetMapping("/alerts")
    public String inventoryAlerts(Model model) {
        List<Inventory> alerts = inventoryService.getInventoryAlerts();
        model.addAttribute("alerts", alerts);
        return "inventory/alerts";
    }
    
    /**
     * 跳转到库存盘点页面
     * @return 库存盘点页面视图
     */
    @GetMapping("/check")
    public String inventoryCheck() {
        return "inventory/check";
    }
    
    /**
     * 跳转到库存导入页面
     * @return 库存导入页面视图
     */
    @GetMapping("/import")
    public String inventoryImport() {
        return "inventory/import";
    }
    
    /**
     * 跳转到库存导出页面
     * @return 库存导出页面视图
     */
    @GetMapping("/export")
    public String inventoryExport() {
        return "inventory/export";
    }
}