package edu.ngd.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.ngd.platform.model.Product;
import java.util.List;

/**
 * 商品服务接口
 */
public interface ProductService extends IService<Product> {
    /**
     * 根据商品编码获取商品
     * @param code 商品编码
     * @return 商品信息
     */
    Product getByCode(String code);
    
    /**
     * 商品上下架
     * @param id 商品ID
     * @param isShelf 是否上架
     * @return 是否操作成功
     */
    boolean shelfProduct(Long id, boolean isShelf);
    
    /**
     * 调整商品库存
     * @param id 商品ID
     * @param quantity 调整数量
     * @return 是否调整成功
     */
    boolean adjustStock(Long id, Integer quantity);
    
    /**
     * 根据条件查询商品列表
     * @param keyword 搜索关键词
     * @param categoryId 分类ID
     * @return 商品列表
     */
    List<Product> listByCondition(String keyword, Long categoryId);
}