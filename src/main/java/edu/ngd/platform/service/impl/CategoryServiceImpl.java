package edu.ngd.platform.service.impl;

import edu.ngd.platform.model.Category;
import edu.ngd.platform.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 分类服务实现类
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    
    // 模拟数据库存储，实际项目中应该使用数据库
    private static final ConcurrentHashMap<Long, Category> CATEGORY_MAP = new ConcurrentHashMap<>();
    private static Long CURRENT_ID = 1L;
    
    // 初始化一些测试数据
    static {
        Category root1 = new Category();
        root1.setId(CURRENT_ID++);
        root1.setName("电子产品");
        root1.setParentId(0L);
        root1.setLevel(1);
        root1.setSortOrder(1);
        root1.setStatus(1);
        root1.setCreateTime(new Date());
        root1.setUpdateTime(new Date());
        root1.setCreateBy("admin");
        root1.setUpdateBy("admin");
        CATEGORY_MAP.put(root1.getId(), root1);
        
        Category child1 = new Category();
        child1.setId(CURRENT_ID++);
        child1.setName("手机");
        child1.setParentId(root1.getId());
        child1.setLevel(2);
        child1.setSortOrder(1);
        child1.setStatus(1);
        child1.setCreateTime(new Date());
        child1.setUpdateTime(new Date());
        child1.setCreateBy("admin");
        child1.setUpdateBy("admin");
        CATEGORY_MAP.put(child1.getId(), child1);
        
        Category child2 = new Category();
        child2.setId(CURRENT_ID++);
        child2.setName("电脑");
        child2.setParentId(root1.getId());
        child2.setLevel(2);
        child2.setSortOrder(2);
        child2.setStatus(1);
        child2.setCreateTime(new Date());
        child2.setUpdateTime(new Date());
        child2.setCreateBy("admin");
        child2.setUpdateBy("admin");
        CATEGORY_MAP.put(child2.getId(), child2);
        
        Category root2 = new Category();
        root2.setId(CURRENT_ID++);
        root2.setName("服装");
        root2.setParentId(0L);
        root2.setLevel(1);
        root2.setSortOrder(2);
        root2.setStatus(1);
        root2.setCreateTime(new Date());
        root2.setUpdateTime(new Date());
        root2.setCreateBy("admin");
        root2.setUpdateBy("admin");
        CATEGORY_MAP.put(root2.getId(), root2);
    }
    
    @Override
    public boolean addCategory(Category category) {
        category.setId(CURRENT_ID++);
        category.setCreateTime(new Date());
        category.setUpdateTime(new Date());
        CATEGORY_MAP.put(category.getId(), category);
        return true;
    }
    
    @Override
    public boolean updateCategory(Category category) {
        Category existing = CATEGORY_MAP.get(category.getId());
        if (existing == null) {
            return false;
        }
        category.setUpdateTime(new Date());
        CATEGORY_MAP.put(category.getId(), category);
        return true;
    }
    
    @Override
    public boolean deleteCategory(Long id) {
        // 检查是否有子分类
        List<Category> children = getCategoriesByParentId(id);
        if (!children.isEmpty()) {
            return false;
        }
        return CATEGORY_MAP.remove(id) != null;
    }
    
    @Override
    public int batchDeleteCategory(List<Long> ids) {
        int count = 0;
        for (Long id : ids) {
            if (deleteCategory(id)) {
                count++;
            }
        }
        return count;
    }
    
    @Override
    public Category getCategoryById(Long id) {
        return CATEGORY_MAP.get(id);
    }
    
    @Override
    public List<Category> getAllCategories() {
        return new ArrayList<>(CATEGORY_MAP.values());
    }
    
    @Override
    public List<Category> getCategoriesByParentId(Long parentId) {
        return CATEGORY_MAP.values().stream()
                .filter(category -> category.getParentId().equals(parentId))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Category> getCategoryTree() {
        // 先获取所有一级分类
        List<Category> rootCategories = getCategoriesByParentId(0L);
        // 递归构建分类树
        for (Category root : rootCategories) {
            buildCategoryTree(root);
        }
        return rootCategories;
    }
    
    // 递归构建分类树
    private void buildCategoryTree(Category parent) {
        List<Category> children = getCategoriesByParentId(parent.getId());
        if (!children.isEmpty()) {
            // 实际项目中应该有一个children字段来存储子分类
            // 这里简化处理，只构建层级关系
            for (Category child : children) {
                buildCategoryTree(child);
            }
        }
    }
    
    @Override
    public boolean updateCategoryStatus(Long id, Integer status) {
        Category category = CATEGORY_MAP.get(id);
        if (category == null) {
            return false;
        }
        category.setStatus(status);
        category.setUpdateTime(new Date());
        CATEGORY_MAP.put(id, category);
        return true;
    }
}