package edu.ngd.platform.service;

import edu.ngd.platform.model.Inventory;

import java.util.List;

/**
 * 库存服务接口
 */
public interface InventoryService {
    /**
     * 查询所有库存
     * @return 库存列表
     */
    List<Inventory> getAllInventories();
    
    /**
     * 根据ID获取库存
     * @param id 库存ID
     * @return 库存信息
     */
    Inventory getInventoryById(Long id);
    
    /**
     * 根据商品ID获取库存
     * @param productId 商品ID
     * @return 库存信息
     */
    Inventory getInventoryByProductId(Long productId);
    
    /**
     * 添加库存
     * @param inventory 库存信息
     * @return 是否添加成功
     */
    boolean addInventory(Inventory inventory);
    
    /**
     * 更新库存
     * @param inventory 库存信息
     * @return 是否更新成功
     */
    boolean updateInventory(Inventory inventory);
    
    /**
     * 删除库存
     * @param id 库存ID
     * @return 是否删除成功
     */
    boolean deleteInventory(Long id);
    
    /**
     * 调整库存
     * @param productId 商品ID
     * @param quantity 调整数量
     * @return 是否调整成功
     */
    boolean adjustInventory(Long productId, Integer quantity);
    
    /**
     * 锁定库存
     * @param productId 商品ID
     * @param quantity 锁定数量
     * @return 是否锁定成功
     */
    boolean lockInventory(Long productId, Integer quantity);
    
    /**
     * 释放库存
     * @param productId 商品ID
     * @param quantity 释放数量
     * @return 是否释放成功
     */
    boolean releaseInventory(Long productId, Integer quantity);
    
    /**
     * 扣减库存
     * @param productId 商品ID
     * @param quantity 扣减数量
     * @return 是否扣减成功
     */
    boolean deductInventory(Long productId, Integer quantity);
    
    /**
     * 获取库存预警列表
     * @return 库存预警列表
     */
    List<Inventory> getInventoryAlerts();
    
    /**
     * 批量导入库存
     * @param inventories 库存列表
     * @return 导入成功的数量
     */
    int batchImportInventories(List<Inventory> inventories);
    
    /**
     * 导出库存数据
     * @return 库存数据列表
     */
    List<Inventory> exportInventories();
}