package edu.ngd.platform.model;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;

/**
 * 收藏实体类
 */
@TableName("favorite")
public class Favorite {
    private Long id;
    private Long userId;
    private Long productId;
    private Date createTime;
    
    // getter和setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getProductId() {
        return productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}