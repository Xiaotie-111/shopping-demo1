package edu.ngd.platform.model;

import java.util.Date;

/**
 * 库存实体类
 */
public class Inventory {
    private Long id;
    private Long productId;
    private String productName;
    private Integer currentStock;
    private Integer lockedStock;
    private Integer availableStock;
    private Integer minThreshold;
    private Integer maxThreshold;
    private Integer status;
    private Date createTime;
    private Date updateTime;
    private String createBy;
    private String updateBy;
    
    // 构造方法
    public Inventory() {
    }
    
    // getter和setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getProductId() {
        return productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public Integer getCurrentStock() {
        return currentStock;
    }
    
    public void setCurrentStock(Integer currentStock) {
        this.currentStock = currentStock;
    }
    
    public Integer getLockedStock() {
        return lockedStock;
    }
    
    public void setLockedStock(Integer lockedStock) {
        this.lockedStock = lockedStock;
    }
    
    public Integer getAvailableStock() {
        return availableStock;
    }
    
    public void setAvailableStock(Integer availableStock) {
        this.availableStock = availableStock;
    }
    
    public Integer getMinThreshold() {
        return minThreshold;
    }
    
    public void setMinThreshold(Integer minThreshold) {
        this.minThreshold = minThreshold;
    }
    
    public Integer getMaxThreshold() {
        return maxThreshold;
    }
    
    public void setMaxThreshold(Integer maxThreshold) {
        this.maxThreshold = maxThreshold;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public String getCreateBy() {
        return createBy;
    }
    
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    
    public String getUpdateBy() {
        return updateBy;
    }
    
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}