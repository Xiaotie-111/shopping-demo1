package edu.ngd.platform.service;

import edu.ngd.platform.model.Spec;
import edu.ngd.platform.model.SpecValue;

import java.util.List;

/**
 * 规格服务接口
 */
public interface SpecService {
    /**
     * 添加规格
     * @param spec 规格信息
     * @return 是否添加成功
     */
    boolean addSpec(Spec spec);
    
    /**
     * 更新规格
     * @param spec 规格信息
     * @return 是否更新成功
     */
    boolean updateSpec(Spec spec);
    
    /**
     * 删除规格
     * @param id 规格ID
     * @return 是否删除成功
     */
    boolean deleteSpec(Long id);
    
    /**
     * 批量删除规格
     * @param ids 规格ID列表
     * @return 删除成功的数量
     */
    int batchDeleteSpec(List<Long> ids);
    
    /**
     * 根据ID获取规格
     * @param id 规格ID
     * @return 规格信息
     */
    Spec getSpecById(Long id);
    
    /**
     * 获取所有规格
     * @return 规格列表
     */
    List<Spec> getAllSpecs();
    
    /**
     * 更新规格状态
     * @param id 规格ID
     * @param status 状态
     * @return 是否更新成功
     */
    boolean updateSpecStatus(Long id, Integer status);
    
    /**
     * 添加规格值
     * @param specValue 规格值信息
     * @return 是否添加成功
     */
    boolean addSpecValue(SpecValue specValue);
    
    /**
     * 更新规格值
     * @param specValue 规格值信息
     * @return 是否更新成功
     */
    boolean updateSpecValue(SpecValue specValue);
    
    /**
     * 删除规格值
     * @param id 规格值ID
     * @return 是否删除成功
     */
    boolean deleteSpecValue(Long id);
    
    /**
     * 批量删除规格值
     * @param ids 规格值ID列表
     * @return 删除成功的数量
     */
    int batchDeleteSpecValue(List<Long> ids);
    
    /**
     * 根据规格ID获取规格值
     * @param specId 规格ID
     * @return 规格值列表
     */
    List<SpecValue> getSpecValuesBySpecId(Long specId);
    
    /**
     * 根据ID获取规格值
     * @param id 规格值ID
     * @return 规格值信息
     */
    SpecValue getSpecValueById(Long id);
}