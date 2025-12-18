package edu.ngd.platform.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.util.List;

/**
 * 订单实体类
 */
@TableName("`order`")
public class Order {
    private Long id;
    private String orderNo;
    private Long userId;
    @TableField(exist = false)
    private String userName;
    private Double totalAmount;
    @TableField(exist = false)
    private Double actualAmount;
    private Integer status;
    @TableField(exist = false)
    private Integer paymentMethod;
    @TableField(exist = false)
    private String paymentNo;
    @TableField(exist = false)
    private Date paymentTime;
    @TableField(exist = false)
    private Integer shippingMethod;
    @TableField(exist = false)
    private String shippingAddress;
    @TableField(exist = false)
    private Date shippingTime;
    @TableField(exist = false)
    private Date receiveTime;
    @TableField(exist = false)
    private String remark;
    private Date createTime;
    private Date updateTime;
    @TableField(exist = false)
    private String createBy;
    @TableField(exist = false)
    private String updateBy;
    @TableField(exist = false)
    private List<OrderItem> orderItems;
    
    // getter和setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getOrderNo() {
        return orderNo;
    }
    
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public Double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public Double getActualAmount() {
        return actualAmount;
    }
    
    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public Integer getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public String getPaymentNo() {
        return paymentNo;
    }
    
    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }
    
    public Date getPaymentTime() {
        return paymentTime;
    }
    
    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }
    
    public Integer getShippingMethod() {
        return shippingMethod;
    }
    
    public void setShippingMethod(Integer shippingMethod) {
        this.shippingMethod = shippingMethod;
    }
    
    public String getShippingAddress() {
        return shippingAddress;
    }
    
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
    public Date getShippingTime() {
        return shippingTime;
    }
    
    public void setShippingTime(Date shippingTime) {
        this.shippingTime = shippingTime;
    }
    
    public Date getReceiveTime() {
        return receiveTime;
    }
    
    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
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
    
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}