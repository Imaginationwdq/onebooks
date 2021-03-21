package com.wdq.onebook.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.spire.ms.System.Collections.ArrayList;
import com.wdq.onebook.common.utils.R;
import com.wdq.onebook.components.TokenManager;
import com.wdq.onebook.entity.ParameterEntity;
import com.wdq.onebook.service.ParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * 动态参数表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:47
 */
@RestController
@RequestMapping("onebook/parameter")
public class ParameterController {
    @Autowired
    private ParameterService parameterService;

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
        List<ParameterEntity> parameterList = parameterService.list(new QueryWrapper<ParameterEntity>().eq("category_id", categoryId));
        if (parameterList == null || parameterList.size() == 0) {
            return R.ok();
        }
        List<ParameterEntity> parameterEntityList = new ArrayList();
        for (ParameterEntity parameterEntity : parameterList) {
            try {
                String parameterTag = parameterEntity.getParameterTag();
                if (!parameterTag.equals("")) {
                    String[] split = parameterTag.split(",");
                    parameterEntity.setParameterTagArray(split);
                }
            } catch (Exception ignored) {
            }
            parameterEntityList.add(parameterEntity);
        }
        return R.ok().put("data", parameterEntityList);
    }

    /**
     * 添加标签
     * @param params
     * @return
     */
    @PostMapping("/updateTag")
    public R UpdateTag(@RequestParam Map<String, Object> params) {
        int parameterId = Integer.parseInt((String) params.get("parameterId"));
        int categoryId = Integer.parseInt((String) params.get("categoryId"));
        String parameterTag = (String) params.get("parameterTag");
        String parameterName = (String) params.get("parameterName");
        ParameterEntity parameterEntity = new ParameterEntity();
        parameterEntity.setCategoryId(categoryId);
        parameterEntity.setParameterName(parameterName);
        parameterEntity.setParameterTag(parameterTag);
        parameterEntity.setParameterId(parameterId);
        boolean update = parameterService.update(parameterEntity, new UpdateWrapper<ParameterEntity>().eq("parameter_id", parameterId));
        if (!update) {
            return R.error("添加失败");
        }
        return R.ok("添加Tag成功");
    }

    /**
     * 添加参数
     * @param params
     * @return
     */
    @PostMapping("/addParameter")
    public R AddParameter(@RequestParam Map<String, Object> params){
        int categoryId = Integer.parseInt((String) params.get("categoryId"));
        String parameterName = (String) params.get("parameterName");
        // 判断参数名称是否已存在
        List<ParameterEntity> parameterList = parameterService.list(new QueryWrapper<ParameterEntity>()
                .eq("category_id",categoryId));
        for (ParameterEntity entity : parameterList) {
            if(entity.getParameterName().equals(parameterName)){
                return R.error("参数名称已存在");
            }
        }
        ParameterEntity parameterEntity = new ParameterEntity();
        parameterEntity.setParameterName(parameterName);
        parameterEntity.setCategoryId(categoryId);
        boolean save = parameterService.save(parameterEntity);
        if(!save){
            return R.error("添加参数失败");
        }
        return R.ok("添加参数成功");
    }

    /**
     * 修改参数
     * @param params
     * @return
     */
    @PostMapping("/updateParameter")
    public R UpdateParameter(@RequestParam Map<String, Object> params){
        String parameterName = (String) params.get("parameterName");
        String parameterId = (String) params.get("parameterId");
        String categoryId = (String) params.get("categoryId");
        // 判断参数是否改变了
        ParameterEntity parameterEntity = parameterService.getOne(new QueryWrapper<ParameterEntity>()
                .eq("parameter_id", parameterId));
        if(parameterEntity == null){
            return R.error("修改失败");
        }
        if (parameterEntity.getParameterName().equals(parameterName)) {
            return R.error("参数名称和原来一致");
        }
        // 判断参数名称是否已存在
        List<ParameterEntity> parameterList = parameterService.list(new QueryWrapper<ParameterEntity>()
                .eq("category_id",categoryId));
        for (ParameterEntity entity : parameterList) {
            if(entity.getParameterName().equals(parameterName)){
                return R.error("参数名称已存在");
            }
        }
        parameterEntity.setParameterName(parameterName);
        boolean update = parameterService.update(parameterEntity, new UpdateWrapper<ParameterEntity>()
                .eq("parameter_id", parameterId));
        if(!update){
            return R.error("修改失败");
        }
        return R.ok("修改成功");
    }

    /**
     * 删除参数
     * @param params
     * @return
     */
    @PostMapping("/deleteParameter")
    public R DeleteParameter(@RequestParam Map<String, Object> params){
        String parameterId = (String) params.get("parameterId");
        boolean b = parameterService.removeById(parameterId);
        if(!b){
            return R.error("删除失败");
        }
        return R.ok("删除成功");
    }
}
