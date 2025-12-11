package edu.ngd.platform.service.impl;

import edu.ngd.platform.model.Spec;
import edu.ngd.platform.model.SpecValue;
import edu.ngd.platform.service.SpecService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 规格服务实现类
 */
@Service
public class SpecServiceImpl implements SpecService {
    
    // 模拟数据库存储，实际项目中应该使用数据库
    private static final ConcurrentHashMap<Long, Spec> SPEC_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Long, SpecValue> SPEC_VALUE_MAP = new ConcurrentHashMap<>();
    private static Long CURRENT_SPEC_ID = 1L;
    private static Long CURRENT_SPEC_VALUE_ID = 1L;
    
    // 初始化一些测试数据
    static {
        // 创建颜色规格
        Spec colorSpec = new Spec();
        colorSpec.setId(CURRENT_SPEC_ID++);
        colorSpec.setName("颜色");
        colorSpec.setSortOrder(1);
        colorSpec.setStatus(1);
        colorSpec.setCreateTime(new Date());
        colorSpec.setUpdateTime(new Date());
        colorSpec.setCreateBy("admin");
        colorSpec.setUpdateBy("admin");
        SPEC_MAP.put(colorSpec.getId(), colorSpec);
        
        // 添加颜色规格值
        String[] colors = {"红色", "蓝色", "黑色", "白色"};
        for (String color : colors) {
            SpecValue specValue = new SpecValue();
            specValue.setId(CURRENT_SPEC_VALUE_ID++);
            specValue.setSpecId(colorSpec.getId());
            specValue.setValue(color);
            specValue.setSortOrder(1);
            specValue.setStatus(1);
            specValue.setCreateTime(new Date());
            specValue.setUpdateTime(new Date());
            specValue.setCreateBy("admin");
            specValue.setUpdateBy("admin");
            SPEC_VALUE_MAP.put(specValue.getId(), specValue);
        }
        
        // 创建尺寸规格
        Spec sizeSpec = new Spec();
        sizeSpec.setId(CURRENT_SPEC_ID++);
        sizeSpec.setName("尺寸");
        sizeSpec.setSortOrder(2);
        sizeSpec.setStatus(1);
        sizeSpec.setCreateTime(new Date());
        sizeSpec.setUpdateTime(new Date());
        sizeSpec.setCreateBy("admin");
        sizeSpec.setUpdateBy("admin");
        SPEC_MAP.put(sizeSpec.getId(), sizeSpec);
        
        // 添加尺寸规格值
        String[] sizes = {"S", "M", "L", "XL", "XXL"};
        for (String size : sizes) {
            SpecValue specValue = new SpecValue();
            specValue.setId(CURRENT_SPEC_VALUE_ID++);
            specValue.setSpecId(sizeSpec.getId());
            specValue.setValue(size);
            specValue.setSortOrder(1);
            specValue.setStatus(1);
            specValue.setCreateTime(new Date());
            specValue.setUpdateTime(new Date());
            specValue.setCreateBy("admin");
            specValue.setUpdateBy("admin");
            SPEC_VALUE_MAP.put(specValue.getId(), specValue);
        }
    }
    
    @Override
    public boolean addSpec(Spec spec) {
        spec.setId(CURRENT_SPEC_ID++);
        spec.setCreateTime(new Date());
        spec.setUpdateTime(new Date());
        SPEC_MAP.put(spec.getId(), spec);
        return true;
    }
    
    @Override
    public boolean updateSpec(Spec spec) {
        Spec existing = SPEC_MAP.get(spec.getId());
        if (existing == null) {
            return false;
        }
        spec.setUpdateTime(new Date());
        SPEC_MAP.put(spec.getId(), spec);
        return true;
    }
    
    @Override
    public boolean deleteSpec(Long id) {
        // 检查是否有规格值
        List<SpecValue> specValues = getSpecValuesBySpecId(id);
        if (!specValues.isEmpty()) {
            return false;
        }
        return SPEC_MAP.remove(id) != null;
    }
    
    @Override
    public int batchDeleteSpec(List<Long> ids) {
        int count = 0;
        for (Long id : ids) {
            if (deleteSpec(id)) {
                count++;
            }
        }
        return count;
    }
    
    @Override
    public Spec getSpecById(Long id) {
        Spec spec = SPEC_MAP.get(id);
        if (spec != null) {
            // 加载规格值
            List<SpecValue> specValues = getSpecValuesBySpecId(id);
            spec.setSpecValues(specValues);
        }
        return spec;
    }
    
    @Override
    public List<Spec> getAllSpecs() {
        List<Spec> specs = new ArrayList<>(SPEC_MAP.values());
        // 加载每个规格的规格值
        for (Spec spec : specs) {
            List<SpecValue> specValues = getSpecValuesBySpecId(spec.getId());
            spec.setSpecValues(specValues);
        }
        return specs;
    }
    
    @Override
    public boolean updateSpecStatus(Long id, Integer status) {
        Spec spec = SPEC_MAP.get(id);
        if (spec == null) {
            return false;
        }
        spec.setStatus(status);
        spec.setUpdateTime(new Date());
        SPEC_MAP.put(id, spec);
        return true;
    }
    
    @Override
    public boolean addSpecValue(SpecValue specValue) {
        specValue.setId(CURRENT_SPEC_VALUE_ID++);
        specValue.setCreateTime(new Date());
        specValue.setUpdateTime(new Date());
        SPEC_VALUE_MAP.put(specValue.getId(), specValue);
        return true;
    }
    
    @Override
    public boolean updateSpecValue(SpecValue specValue) {
        SpecValue existing = SPEC_VALUE_MAP.get(specValue.getId());
        if (existing == null) {
            return false;
        }
        specValue.setUpdateTime(new Date());
        SPEC_VALUE_MAP.put(specValue.getId(), specValue);
        return true;
    }
    
    @Override
    public boolean deleteSpecValue(Long id) {
        return SPEC_VALUE_MAP.remove(id) != null;
    }
    
    @Override
    public int batchDeleteSpecValue(List<Long> ids) {
        int count = 0;
        for (Long id : ids) {
            if (deleteSpecValue(id)) {
                count++;
            }
        }
        return count;
    }
    
    @Override
    public List<SpecValue> getSpecValuesBySpecId(Long specId) {
        return SPEC_VALUE_MAP.values().stream()
                .filter(specValue -> specValue.getSpecId().equals(specId))
                .collect(Collectors.toList());
    }
    
    @Override
    public SpecValue getSpecValueById(Long id) {
        return SPEC_VALUE_MAP.get(id);
    }
}