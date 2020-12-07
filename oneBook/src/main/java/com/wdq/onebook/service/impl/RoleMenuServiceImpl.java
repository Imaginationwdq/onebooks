package com.wdq.onebook.service.impl;

import com.wdq.onebook.common.utils.Constant;
import com.wdq.onebook.entity.vo.MenuRolemenuVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.common.utils.Query;

import com.wdq.onebook.dao.RoleMenuDao;
import com.wdq.onebook.entity.RoleMenuEntity;
import com.wdq.onebook.service.RoleMenuService;


@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuDao, RoleMenuEntity> implements RoleMenuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RoleMenuEntity> page = this.page(
                new Query<RoleMenuEntity>().getPage(params),
                new QueryWrapper<RoleMenuEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 根据roleId查询权限树形列表
     * @param roleId
     * @return
     */
    @Override
    public List<MenuRolemenuVO> getRoleMenuTreeList(Integer roleId) {
        List<MenuRolemenuVO> roleMenuTreeList = this.baseMapper.getRoleMenuTreeList(roleId);
        List<MenuRolemenuVO> treeList = new ArrayList<MenuRolemenuVO>();
        for (int i=0;i<roleMenuTreeList.size();i++){
            //一级菜单
            MenuRolemenuVO menuRolemenuVO = roleMenuTreeList.get(i);
            //先找到顶级菜单
            if (menuRolemenuVO.getParent().equals(Constant.Menu.PARENT_DEFAULT.getIndex())){
                //保存一级菜单的children
                List<MenuRolemenuVO> children1 = new ArrayList<MenuRolemenuVO>();
                for (int j=0;j<roleMenuTreeList.size();j++){
                    //二级菜单
                    MenuRolemenuVO menuRolemenuVO2 = roleMenuTreeList.get(j);
                    //一级菜单的menueId
                    Integer menuId1 = menuRolemenuVO.getMenuId();
                    //找到顶级菜单的menuId=parent的菜单
                    if (menuRolemenuVO2.getParent().equals(menuId1)) {
                        //保存二级菜单的children
                        List<MenuRolemenuVO> children2 = new ArrayList<MenuRolemenuVO>();
                        for (int k=0;k<roleMenuTreeList.size();k++){
                            //三级菜单
                            MenuRolemenuVO menuRolemenuVO3 = roleMenuTreeList.get(k);
                            //二级菜单的menueId
                            Integer menuId2 = menuRolemenuVO2.getMenuId();
                            if (menuRolemenuVO3.getParent().equals(menuId2)) {
                                children2.add(menuRolemenuVO3);
                                roleMenuTreeList.remove(k);
                                k--;
                            }
                        }
                        menuRolemenuVO2.setChildren(children2);
                        children1.add(menuRolemenuVO2);
                        roleMenuTreeList.remove(j);
                        j--;
                    }
                }
                menuRolemenuVO.setChildren(children1);
            }
            treeList.add(menuRolemenuVO);
            roleMenuTreeList.remove(i);
            i--;
        }
        return treeList;
    }
}