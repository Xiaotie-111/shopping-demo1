package edu.ngd.platform.controller;

import edu.ngd.platform.model.Category;
import edu.ngd.platform.model.Product;
import edu.ngd.platform.service.CategoryService;
import edu.ngd.platform.service.ProductService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 商品管理Controller
 */
@Controller
@RequestMapping("/product")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private CategoryService categoryService;
    
    /**
     * 跳转到商品列表页面（管理端）
     * @param model 模型对象
     * @param page 当前页码，默认为1
     * @return 商品列表页面视图
     */
    @GetMapping("/list")
    public String productList(Model model, @RequestParam(defaultValue = "1") Integer page) {
        List<Product> products = productService.list();
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", 3); // 模拟总页数为3
        return "product/list";
    }
    
    /**
     * 跳转到用户端商品列表页面
     * @param model 模型对象
     * @param keyword 搜索关键词
     * @param categoryId 分类ID
     * @param page 当前页码，默认为1
     * @return 用户端商品列表页面视图
     */
    @GetMapping("/browse")
    public String productBrowse(Model model, 
                              @RequestParam(required = false) String keyword, 
                              @RequestParam(required = false) Long categoryId, 
                              @RequestParam(defaultValue = "1") Integer page) {
        // 获取商品列表（可以根据关键词和分类筛选）
        List<Product> products = productService.listByCondition(keyword, categoryId);
        
        // 获取所有分类用于筛选
        List<Category> categories = categoryService.getAllCategories();
        
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", 3); // 模拟总页数为3
        
        return "product/browse";
    }
    
    /**
     * 跳转到商品详情页面
     * @param id 商品ID
     * @param model 模型对象
     * @return 商品详情页面视图
     */
    @GetMapping("/detail/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        // 获取商品详情
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        
        // 获取相关商品列表
        List<Product> products = productService.list();
        model.addAttribute("products", products);
        
        return "product/detail";
    }
    
    /**
     * 跳转到商品添加页面
     * @return 商品添加页面视图
     */
    @GetMapping("/add")
    public String productAdd(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "product/add";
    }
    
    /**
     * 处理商品添加请求
     * @param product 商品信息
     * @return 重定向到商品列表页面
     */
    @PostMapping("/add")
    public String doProductAdd(Product product) {
        productService.save(product);
        return "redirect:/product/list";
    }
    
    /**
     * 跳转到商品编辑页面
     * @param id 商品ID
     * @param model 模型对象
     * @return 商品编辑页面视图
     */
    @GetMapping("/edit/{id}")
    public String productEdit(@PathVariable Long id, Model model) {
        Product product = productService.getById(id);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "product/edit";
    }
    
    /**
     * 处理商品编辑请求
     * @param product 商品信息
     * @return 重定向到商品列表页面
     */
    @PostMapping("/edit")
    public String doProductEdit(Product product) {
        productService.updateById(product);
        return "redirect:/product/list";
    }
    
    /**
     * 处理商品删除请求
     * @param id 商品ID
     * @return 重定向到商品列表页面
     */
    @GetMapping("/delete/{id}")
    public String productDelete(@PathVariable Long id) {
        productService.removeById(id);
        return "redirect:/product/list";
    }
    
    /**
     * 处理商品上下架请求
     * @param id 商品ID
     * @param isShelf 是否上架
     * @return 重定向到商品列表页面
     */
    @GetMapping("/shelf/{id}/{isShelf}")
    public String shelfProduct(@PathVariable Long id, @PathVariable boolean isShelf) {
        productService.shelfProduct(id, isShelf);
        return "redirect:/product/list";
    }
    
    /**
     * 处理调整商品库存请求
     * @param id 商品ID
     * @param quantity 调整数量
     * @return 重定向到商品列表页面
     */
    @GetMapping("/stock/{id}/{quantity}")
    public String adjustStock(@PathVariable Long id, @PathVariable Integer quantity) {
        productService.adjustStock(id, quantity);
        return "redirect:/product/list";
    }
    
    /**
     * 跳转到商品导入页面
     * @return 商品导入页面视图
     */
    @GetMapping("/import")
    public String productImport() {
        return "product/import";
    }
    
    /**
     * 处理商品导入请求
     * @return 重定向到商品列表页面
     */
    @PostMapping("/import")
    public String doProductImport() {
        // TODO: 实现商品导入逻辑
        return "redirect:/product/list";
    }
    
    /**
     * 处理商品导出请求
     * @param response HttpServletResponse
     * @return 重定向到商品列表页面
     * @throws Exception 导出异常
     */
    @GetMapping("/export")
    public void productExport(HttpServletResponse response) throws Exception {
        // 获取商品列表
        List<Product> products = productService.list();
        
        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");
        String fileName = URLEncoder.encode("商品列表_", "UTF-8") + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        
        // 创建Excel文件
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("商品列表");
        
        // 创建表头
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "商品名称", "商品编码", "分类", "品牌", "价格", "库存", "销量", "状态", "创建时间"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
        
        // 填充数据
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            Row dataRow = sheet.createRow(i + 1);
            
            dataRow.createCell(0).setCellValue(product.getId());
            dataRow.createCell(1).setCellValue(product.getName());
            dataRow.createCell(2).setCellValue(product.getCode());
            dataRow.createCell(3).setCellValue(product.getCategoryName());
            dataRow.createCell(4).setCellValue(product.getBrand());
            dataRow.createCell(5).setCellValue(product.getPrice());
            dataRow.createCell(6).setCellValue(product.getStock());
            dataRow.createCell(7).setCellValue(product.getSales());
            dataRow.createCell(8).setCellValue(product.getStatus() == 1 ? "上架" : "下架");
            dataRow.createCell(9).setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(product.getCreateTime()));
        }
        
        // 自动调整列宽
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
        
        // 输出文件
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}