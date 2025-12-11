package edu.ngd.platform.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.ngd.platform.mapper.ProductMapper;
import edu.ngd.platform.model.Product;
import edu.ngd.platform.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品服务实现类
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    
    @Override
    public Product getByCode(String code) {
        return baseMapper.selectByCode(code);
    }
    
    @Override
    public boolean shelfProduct(Long id, boolean isShelf) {
        Product product = this.getById(id);
        if (product != null) {
            product.setStatus(isShelf ? 1 : 0);
            return this.updateById(product);
        }
        return false;
    }
    
    @Override
    public boolean adjustStock(Long id, Integer quantity) {
        Product product = this.getById(id);
        if (product != null) {
            product.setStock(product.getStock() + quantity);
            return this.updateById(product);
        }
        return false;
    }
    
    @Override
    public List<Product> listByCondition(String keyword, Long categoryId) {
        // 使用MyBatis Plus的QueryWrapper构建查询条件
        com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<Product> queryWrapper = new com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<>();
        
        // 只查询上架商品（status=1）
        queryWrapper.eq("status", 1);
        
        // 根据关键词搜索（商品名称、编码或描述）
        if (keyword != null && !keyword.isEmpty()) {
            queryWrapper.and(wrapper -> wrapper.like("name", keyword)
                                             .or().like("code", keyword)
                                             .or().like("description", keyword));
        }
        
        // 根据分类ID筛选
        if (categoryId != null) {
            queryWrapper.eq("category_id", categoryId);
        }
        
        // 按照创建时间倒序排序
        queryWrapper.orderByDesc("create_time");
        
        return baseMapper.selectList(queryWrapper);
    }
}