package edu.ngd.platform.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.ngd.platform.model.Product;

/**
 * 商品Mapper接口
 */
public interface ProductMapper extends BaseMapper<Product> {
    /**
     * 根据商品编码获取商品
     * @param code 商品编码
     * @return 商品信息
     */
    Product selectByCode(String code);
}