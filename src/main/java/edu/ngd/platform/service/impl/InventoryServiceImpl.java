package edu.ngd.platform.service.impl;

import edu.ngd.platform.model.Inventory;
import edu.ngd.platform.service.InventoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 库存服务实现类
 */
@Service
public class InventoryServiceImpl implements InventoryService {
    
    // 模拟数据库存储，实际项目中应该使用数据库
    private static final ConcurrentHashMap<Long, Inventory> INVENTORY_MAP = new ConcurrentHashMap<>();
    private static Long CURRENT_INVENTORY_ID = 1L;
    
    // 初始化一些测试数据
    static {
        Inventory inventory1 = new Inventory();
        inventory1.setId(CURRENT_INVENTORY_ID++);
        inventory1.setProductId(1L);
        inventory1.setProductName("iPhone 15 Pro");
        inventory1.setCurrentStock(100);
        inventory1.setLockedStock(0);
        inventory1.setAvailableStock(100);
        inventory1.setMinThreshold(10);
        inventory1.setMaxThreshold(200);
        inventory1.setStatus(1);
        inventory1.setCreateTime(new Date());
        inventory1.setUpdateTime(new Date());
        inventory1.setCreateBy("admin");
        inventory1.setUpdateBy("admin");
        INVENTORY_MAP.put(inventory1.getId(), inventory1);
        
        Inventory inventory2 = new Inventory();
        inventory2.setId(CURRENT_INVENTORY_ID++);
        inventory2.setProductId(2L);
        inventory2.setProductName("MacBook Pro 14");
        inventory2.setCurrentStock(50);
        inventory2.setLockedStock(0);
        inventory2.setAvailableStock(50);
        inventory2.setMinThreshold(5);
        inventory2.setMaxThreshold(100);
        inventory2.setStatus(1);
        inventory2.setCreateTime(new Date());
        inventory2.setUpdateTime(new Date());
        inventory2.setCreateBy("admin");
        inventory2.setUpdateBy("admin");
        INVENTORY_MAP.put(inventory2.getId(), inventory2);
    }
    
    @Override
    public List<Inventory> getAllInventories() {
        return new ArrayList<>(INVENTORY_MAP.values());
    }
    
    @Override
    public Inventory getInventoryById(Long id) {
        return INVENTORY_MAP.get(id);
    }
    
    @Override
    public Inventory getInventoryByProductId(Long productId) {
        return INVENTORY_MAP.values().stream()
                .filter(inventory -> inventory.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }
    
    @Override
    public boolean addInventory(Inventory inventory) {
        inventory.setId(CURRENT_INVENTORY_ID++);
        inventory.setCreateTime(new Date());
        inventory.setUpdateTime(new Date());
        // 计算可用库存
        inventory.setAvailableStock(inventory.getCurrentStock() - inventory.getLockedStock());
        INVENTORY_MAP.put(inventory.getId(), inventory);
        return true;
    }
    
    @Override
    public boolean updateInventory(Inventory inventory) {
        Inventory existing = INVENTORY_MAP.get(inventory.getId());
        if (existing == null) {
            return false;
        }
        inventory.setUpdateTime(new Date());
        // 计算可用库存
        inventory.setAvailableStock(inventory.getCurrentStock() - inventory.getLockedStock());
        INVENTORY_MAP.put(inventory.getId(), inventory);
        return true;
    }
    
    @Override
    public boolean deleteInventory(Long id) {
        return INVENTORY_MAP.remove(id) != null;
    }
    
    @Override
    public boolean adjustInventory(Long productId, Integer quantity) {
        Inventory inventory = getInventoryByProductId(productId);
        if (inventory == null) {
            return false;
        }
        // 调整库存
        inventory.setCurrentStock(inventory.getCurrentStock() + quantity);
        // 重新计算可用库存
        inventory.setAvailableStock(inventory.getCurrentStock() - inventory.getLockedStock());
        inventory.setUpdateTime(new Date());
        INVENTORY_MAP.put(inventory.getId(), inventory);
        return true;
    }
    
    @Override
    public boolean lockInventory(Long productId, Integer quantity) {
        Inventory inventory = getInventoryByProductId(productId);
        if (inventory == null || inventory.getAvailableStock() < quantity) {
            return false;
        }
        // 锁定库存
        inventory.setLockedStock(inventory.getLockedStock() + quantity);
        // 重新计算可用库存
        inventory.setAvailableStock(inventory.getCurrentStock() - inventory.getLockedStock());
        inventory.setUpdateTime(new Date());
        INVENTORY_MAP.put(inventory.getId(), inventory);
        return true;
    }
    
    @Override
    public boolean releaseInventory(Long productId, Integer quantity) {
        Inventory inventory = getInventoryByProductId(productId);
        if (inventory == null || inventory.getLockedStock() < quantity) {
            return false;
        }
        // 释放库存
        inventory.setLockedStock(inventory.getLockedStock() - quantity);
        // 重新计算可用库存
        inventory.setAvailableStock(inventory.getCurrentStock() - inventory.getLockedStock());
        inventory.setUpdateTime(new Date());
        INVENTORY_MAP.put(inventory.getId(), inventory);
        return true;
    }
    
    @Override
    public boolean deductInventory(Long productId, Integer quantity) {
        Inventory inventory = getInventoryByProductId(productId);
        if (inventory == null || inventory.getLockedStock() < quantity) {
            return false;
        }
        // 扣减库存
        inventory.setCurrentStock(inventory.getCurrentStock() - quantity);
        inventory.setLockedStock(inventory.getLockedStock() - quantity);
        // 重新计算可用库存
        inventory.setAvailableStock(inventory.getCurrentStock() - inventory.getLockedStock());
        inventory.setUpdateTime(new Date());
        INVENTORY_MAP.put(inventory.getId(), inventory);
        return true;
    }
    
    @Override
    public List<Inventory> getInventoryAlerts() {
        return INVENTORY_MAP.values().stream()
                .filter(inventory -> inventory.getCurrentStock() <= inventory.getMinThreshold())
                .collect(Collectors.toList());
    }
    
    @Override
    public int batchImportInventories(List<Inventory> inventories) {
        int count = 0;
        for (Inventory inventory : inventories) {
            if (addInventory(inventory)) {
                count++;
            }
        }
        return count;
    }
    
    @Override
    public List<Inventory> exportInventories() {
        return getAllInventories();
    }
}