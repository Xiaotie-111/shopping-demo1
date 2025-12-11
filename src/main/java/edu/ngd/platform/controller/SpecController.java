package edu.ngd.platform.controller;

import edu.ngd.platform.model.Spec;
import edu.ngd.platform.model.SpecValue;
import edu.ngd.platform.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 规格管理控制器
 */
@Controller
@RequestMapping("/spec")
public class SpecController {
    
    @Autowired
    private SpecService specService;
    
    /**
     * 跳转到规格列表页面
     * @param model 模型对象
     * @param page 当前页码，默认为1
     * @return 规格列表页面视图
     */
    @GetMapping("/list")
    public String specList(Model model, @RequestParam(defaultValue = "1") Integer page) {
        List<Spec> specs = specService.getAllSpecs();
        model.addAttribute("specs", specs);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", 3); // 模拟总页数为3
        return "spec/list";
    }
    
    /**
     * 跳转到规格添加页面
     * @return 规格添加页面视图
     */
    @GetMapping("/add")
    public String specAdd() {
        return "spec/add";
    }
    
    /**
     * 处理规格添加请求
     * @param spec 规格信息
     * @return 重定向到规格列表页面
     */
    @PostMapping("/add")
    public String doSpecAdd(Spec spec) {
        specService.addSpec(spec);
        return "redirect:/spec/list";
    }
    
    /**
     * 跳转到规格编辑页面
     * @param id 规格ID
     * @param model 模型对象
     * @return 规格编辑页面视图
     */
    @GetMapping("/edit/{id}")
    public String specEdit(@PathVariable Long id, Model model) {
        Spec spec = specService.getSpecById(id);
        model.addAttribute("spec", spec);
        return "spec/edit";
    }
    
    /**
     * 处理规格编辑请求
     * @param spec 规格信息
     * @return 重定向到规格列表页面
     */
    @PostMapping("/edit")
    public String doSpecEdit(Spec spec) {
        specService.updateSpec(spec);
        return "redirect:/spec/list";
    }
    
    /**
     * 处理规格删除请求
     * @param id 规格ID
     * @return 重定向到规格列表页面
     */
    @GetMapping("/delete/{id}")
    public String specDelete(@PathVariable Long id) {
        specService.deleteSpec(id);
        return "redirect:/spec/list";
    }
    
    /**
     * 处理批量删除规格请求
     * @param ids 规格ID列表
     * @return 重定向到规格列表页面
     */
    @PostMapping("/batchDelete")
    public String batchDeleteSpec(@RequestParam List<Long> ids) {
        specService.batchDeleteSpec(ids);
        return "redirect:/spec/list";
    }
    
    /**
     * 处理更新规格状态请求
     * @param id 规格ID
     * @param status 状态
     * @return 重定向到规格列表页面
     */
    @GetMapping("/updateStatus/{id}/{status}")
    public String updateSpecStatus(@PathVariable Long id, @PathVariable Integer status) {
        specService.updateSpecStatus(id, status);
        return "redirect:/spec/list";
    }
    
    /**
     * 处理添加规格值请求
     * @param specValue 规格值信息
     * @return 重定向到规格编辑页面
     */
    @PostMapping("/addSpecValue")
    public String doAddSpecValue(SpecValue specValue) {
        specService.addSpecValue(specValue);
        return "redirect:/spec/edit/" + specValue.getSpecId();
    }
    
    /**
     * 处理编辑规格值请求
     * @param specValue 规格值信息
     * @return 重定向到规格编辑页面
     */
    @PostMapping("/editSpecValue")
    public String doEditSpecValue(SpecValue specValue) {
        specService.updateSpecValue(specValue);
        return "redirect:/spec/edit/" + specValue.getSpecId();
    }
    
    /**
     * 处理删除规格值请求
     * @param id 规格值ID
     * @param specId 规格ID
     * @return 重定向到规格编辑页面
     */
    @GetMapping("/deleteSpecValue/{id}/{specId}")
    public String deleteSpecValue(@PathVariable Long id, @PathVariable Long specId) {
        specService.deleteSpecValue(id);
        return "redirect:/spec/edit/" + specId;
    }
}