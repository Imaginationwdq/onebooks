package com.wdq.onebook.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wdq.onebook.components.TokenManager;
import com.wdq.onebook.entity.*;
import com.wdq.onebook.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wdq.onebook.common.utils.PageUtils;
import com.wdq.onebook.common.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 * 用户的图书列表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:48
 */
@RestController
@RequestMapping("onebook/booklist")
public class BookListController {
    @Autowired
    private BookListService bookListService;

    @Autowired
    private BookDetailService bookDetailService;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private UsersService usersService;

    @Autowired
    private BookParameterService bookParameterService;

    @Autowired
    private BookPropertyService bookPropertyService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request) throws ParseException {
        String username = tokenManager.getUserInfoFromToken(request.getHeader("token"));
        if (username.contains("用户") || username.contains("会员")) {
            UsersEntity user = usersService.getOne(new QueryWrapper<UsersEntity>().eq("username", username));
            params.put("userId", user.getUserId());
        }

        PageUtils page = bookListService.queryPage(params);
        List<BookListEntity> list = (List<BookListEntity>) page.getList();

        String title = (String) params.get("title");

        for (int i = 0; i < list.size(); i++) {
            BookListEntity bookListEntity = list.get(i);
            BookDetailEntity bookDetailEntity = bookDetailService.getById(bookListEntity.getBookId());
            if (bookDetailEntity.getTitle().contains(title)) {
                bookListEntity.setBookDetail(bookDetailEntity);
            } else {
                list.remove(i);
                i--;
            }
        }

        return R.ok().put("page", page);
    }

    @PostMapping("/removeById")
    public R removeById(@RequestParam Map<String, Object> params){
        String bookId = (String)params.get("bookId");
        boolean b = bookDetailService.remove(new QueryWrapper<BookDetailEntity>().eq("book_id", bookId));
        if (!b) {
            return R.error("删除失败");
        }
        b = bookListService.remove(new QueryWrapper<BookListEntity>().eq("book_id", bookId));
        if (!b) {
            return R.error("删除失败");
        }
        b = bookParameterService.remove(new QueryWrapper<BookParameterEntity>().eq("book_id", bookId));
        if (!b) {
            return R.error("删除失败");
        }
        b = bookPropertyService.remove(new QueryWrapper<BookPropertyEntity>().eq("book_id", bookId));
        if (!b) {
            return R.error("删除失败");
        }
        return R.ok("删除成功");
    }

}
