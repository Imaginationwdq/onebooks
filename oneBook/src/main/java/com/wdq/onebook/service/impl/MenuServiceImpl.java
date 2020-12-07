package com.wdq.onebook.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdq.onebook.common.utils.Constant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.common.utils.Query;

import com.wdq.onebook.dao.MenuDao;
import com.wdq.onebook.entity.MenuEntity;
import com.wdq.onebook.service.MenuService;


@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuDao, MenuEntity> implements MenuService {

    /**
     * 获取树形菜单（目前只是查询两层）
     * @param params
     * @return
     */
    @Override
    public PageUtils queryTreeListPage(Map<String, Object> params) {
        IPage<MenuEntity> page = this.page(
                new Query<MenuEntity>().getPage(params),
                new QueryWrapper<MenuEntity>()
        );
        //将菜单列表拼装成前端需要的格式
        List<MenuEntity> menuList = page.getRecords();
        List<Map<String,Object>> menuelist = new ArrayList<Map<String, Object>>();
        for (int i=0; i < menuList.size();i++) {
            MenuEntity menu = menuList.get(i);
            Integer parent = menu.getParent();
            if (parent == Constant.Menu.PARENT_DEFAULT.getIndex()) {
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("id",menu.getMenuId().toString());
                map.put("authName",menu.getAuthName());
                map.put("path",menu.getPath());
                map.put("iconId",menu.getIconId());
                List<Map<String,Object>> children = new ArrayList<Map<String, Object>>();
                for (int j=0; j < menuList.size();j++){
                    MenuEntity menu2 = menuList.get(j);
                    if (!menu2.getParent().equals(menu.getMenuId())) continue;
                    Map<String,Object> map2 = new HashMap<String, Object>();
                    map2.put("id",menu2.getMenuId().toString());
                    map2.put("authName",menu2.getAuthName());
                    map2.put("path",menu2.getPath());
                    map2.put("iconId",menu2.getIconId());
                    children.add(map2);
                    menuList.remove(j);
                    j--;
                }
                map.put("children",children);
                menuList.remove(i);
                i--;
                menuelist.add(map);
            }
        }
        IPage page1 = new Page();
        page1.setRecords(menuelist);
        page1.setTotal(page.getTotal());
        page1.setSize(page.getSize());
        page1.setCurrent(page.getCurrent());
        return new PageUtils(page1);
    }

    /**
     * 获取菜单列表
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MenuEntity> page = this.page(
                new Query<MenuEntity>().getPage(params),
                new QueryWrapper<MenuEntity>()
        );
        return new PageUtils(page);
    }

}