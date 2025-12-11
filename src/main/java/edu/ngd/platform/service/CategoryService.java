package edu.ngd.platform.service;

import edu.ngd.platform.model.Category;

import java.util.List;

/**
 * 分类服务接口
 */
public interface CategoryService {
    /**
     * 添加分类
     * @param category 分类信息
     * @return 是否添加成功
     */
    boolean addCategory(Category category);
    
    /**
     * 更新分类
     * @param category 分类信息
     * @return 是否更新成功
     */
    boolean updateCategory(Category category);
    
    /**
     * 删除分类
     * @param id 分类ID
     * @return 是否删除成功
     */
    boolean deleteCategory(Long id);
    
    /**
     * 批量删除分类
     * @param ids 分类ID列表
     * @return 删除成功的数量
     */
    int batchDeleteCategory(List<Long> ids);
    
    /**
     * 根据ID获取分类
     * @param id 分类ID
     * @return 分类信息
     */
    Category getCategoryById(Long id);
    
    /**
     * 获取所有分类
     * @return 分类列表
     */
    List<Category> getAllCategories();
    
    /**
     * 根据父ID获取子分类
     * @param parentId 父分类ID
     * @return 子分类列表
     */
    List<Category> getCategoriesByParentId(Long parentId);
    
    /**
     * 获取分类树结构
     * @return 分类树结构
     */
    List<Category> getCategoryTree();
    
    /**
     * 更新分类状态
     * @param id 分类ID
     * @param status 状态
     * @return 是否更新成功
     */
    boolean updateCategoryStatus(Long id, Integer status);
}