package com.wdq.onebook.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.spire.ms.System.Collections.ArrayList;
import com.wdq.onebook.common.utils.R;
import com.wdq.onebook.components.TokenManager;
import com.wdq.onebook.entity.PropertyEntity;
import com.wdq.onebook.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 静态属性表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:47
 */
@RestController
@RequestMapping("onebook/property")
public class PropertyController {
    @Autowired
    private PropertyService propertyService;

    @Autowired
    TokenManager tokenManager;

    /**
     * 列表
     * @param params
     * @return
     */
    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        int categoryId = Integer.parseInt((String) params.get("categoryId"));
        List<PropertyEntity> propertyList = propertyService.list(new QueryWrapper<PropertyEntity>()
                .eq("category_id", categoryId));
        if (propertyList == null || propertyList.size() == 0) {
            return R.ok();
        }
        List<PropertyEntity> PropertyEntityList = new ArrayList();
        for (PropertyEntity PropertyEntity : propertyList) {
            try {
                String propertyTag = PropertyEntity.getPropertyTag();
                if (!propertyTag.equals("")) {
                    String[] split = propertyTag.split(",");
                    PropertyEntity.setPropertyTagArray(split);
                }
            } catch (Exception ignored) {
            }
            PropertyEntityList.add(PropertyEntity);
        }
        return R.ok().put("data", PropertyEntityList);
    }

    /**
     * 添加标签
     * @param params
     * @return
     */
    @PostMapping("/updateTag")
    public R UpdateTag(@RequestParam Map<String, Object> params) {
        int propertyId = Integer.parseInt((String) params.get("propertyId"));
        int categoryId = Integer.parseInt((String) params.get("categoryId"));
        String propertyTag = (String) params.get("propertyTag");
        String propertyName = (String) params.get("propertyName");
        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setCategoryId(categoryId);
        propertyEntity.setPropertyName(propertyName);
        propertyEntity.setPropertyTag(propertyTag);
        propertyEntity.setPropertyId(propertyId);
        boolean update = propertyService.update(propertyEntity, new UpdateWrapper<PropertyEntity>()
                .eq("property_id", propertyId));
        if (!update) {
            return R.error("更新失败");
        }
        return R.ok();
    }

    /**
     * 添加静态属性
     * @param params
     * @return
     */
    @PostMapping("/addProperty")
    public R AddProperty(@RequestParam Map<String, Object> params){
        int categoryId = Integer.parseInt((String) params.get("categoryId"));
        String propertyName = (String) params.get("propertyName");
        // 判断参数名称是否已存在
        List<PropertyEntity> propertyList = propertyService.list(new QueryWrapper<PropertyEntity>()
                .eq("category_id",categoryId));
        for (PropertyEntity entity : propertyList) {
            if(entity.getPropertyName().equals(propertyName)){
                return R.error("参数名称已存在");
            }
        }
        PropertyEntity propertyEntity = new PropertyEntity();
        propertyEntity.setPropertyName(propertyName);
        propertyEntity.setCategoryId(categoryId);
        boolean save = propertyService.save(propertyEntity);
        if(!save){
            return R.error("添加参数失败");
        }
        return R.ok("添加参数成功");
    }

    /**
     * 修改静态属性
     * @param params
     * @return
     */
    @PostMapping("/updateProperty")
    public R UpdateProperty(@RequestParam Map<String, Object> params){
        String propertyName = (String) params.get("propertyName");
        String propertyId = (String) params.get("propertyId");
        String categoryId = (String) params.get("categoryId");
        // 判断参数是否改变了
        PropertyEntity propertyEntity = propertyService.getOne(new QueryWrapper<PropertyEntity>()
                .eq("property_id", propertyId));
        if(propertyEntity == null){
            return R.error("修改失败");
        }
        if (propertyEntity.getPropertyName().equals(propertyName)) {
            return R.error("参数名称和原来一致");
        }
        // 判断参数名称是否已存在
        List<PropertyEntity> propertyList = propertyService.list(new QueryWrapper<PropertyEntity>()
                .eq("category_id",categoryId));
        for (PropertyEntity entity : propertyList) {
            if(entity.getPropertyName().equals(propertyName)){
                return R.error("参数名称已存在");
            }
        }
        propertyEntity.setPropertyName(propertyName);
        boolean update = propertyService.update(propertyEntity, new UpdateWrapper<PropertyEntity>()
                .eq("property_id", propertyId));
        if(!update){
            return R.error("修改失败");
        }
        return R.ok("修改成功");
    }

    /**
     * 删除静态属性
     * @param params
     * @return
     */
    @PostMapping("/deleteProperty")
    public R DeleteProperty(@RequestParam Map<String, Object> params){
        String propertyId = (String) params.get("propertyId");
        boolean b = propertyService.removeById(propertyId);
        if(!b){
            return R.error("删除失败");
        }
        return R.ok("删除成功");
    }
}
