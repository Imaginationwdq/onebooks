package com.wdq.onebook.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wdq.onebook.common.utils.DateUtils;
import com.wdq.onebook.components.TokenManager;
import com.wdq.onebook.entity.*;
import com.wdq.onebook.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wdq.onebook.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * 图书详情表
 *
 * @author wdq
 * @email bestriven666@outlook.com
 * @date 2020-11-16 19:20:48
 */
@RestController
@RequestMapping("onebook/bookdetail")
public class BookDetailController {
    @Autowired
    private BookDetailService bookDetailService;

    @Autowired
    private BookParameterService bookParameterService;

    @Autowired
    private BookPropertyService bookPropertyService;

    @Autowired
    private BookListService bookListService;

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private UsersService usersService;

    /**
     * 上传照片
     */
    @PostMapping("/uploade")
    public R Upload(@RequestParam(value = "file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            System.out.println("文件为空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "D://temp-rainy//"; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI4Fyn7XUM54taNPEggjkx";
        String accessKeySecret = "AFJufOvaKeCie9PmthohuUBcu0xom4";
        String bucketName = "onebook-wdq";
        // <yourObjectName>表示删除OSS文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String objectName = "book/" + fileName;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(file.getBytes()));
        // 关闭OSSClient。
        ossClient.shutdown();
        return R.ok().put("url", objectName);
    }

    /**
     * 添加图书
     */
    @PostMapping("/addBook")
    public R AddBook(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        String categoryId = (String) params.get("categoryId");
        String[] split = categoryId.split(",");
        int id = Integer.parseInt(split[split.length - 1]);
        String title = (String) params.get("title");
        String author = (String) params.get("author");
        String price = (String) params.get("price");
        String remake = (String) params.get("remake");
        String pics = (String) params.get("pics");
        String num = (String) params.get("num");
        String[] picSplit = pics.split(",");
        BookDetailEntity bookDetailEntity = new BookDetailEntity();
        bookDetailEntity.setCategoryId(id);
        bookDetailEntity.setTitle(title);
        bookDetailEntity.setPrice(Double.parseDouble(price));
        bookDetailEntity.setAuthor(author);
        bookDetailEntity.setRemake(remake);
        bookDetailEntity.setFrontimg(picSplit[0]);
        bookDetailEntity.setSideimg(picSplit[1]);
        bookDetailEntity.setBackimg(picSplit[2]);
        boolean save = bookDetailService.save(bookDetailEntity);
        if (!save) {
            return R.error("添加失败！");
        }
        BookDetailEntity book = bookDetailService.getOne(new QueryWrapper<BookDetailEntity>()
                .eq("frontImg", picSplit[0]));

        String parameter = (String) params.get("parameter");
        String property = (String) params.get("property");
        String[] parameterArray = parameter.split("-wdqnb-");
        String[] propertyArray = property.split("-wdqnb-");
        for (String entity : parameterArray) {
            String[] split1 = entity.split("-9wdqnb9-");
            int parameterId = Integer.parseInt(split1[0]);
            String tag = split1[1];
            BookParameterEntity bookParameterEntity = new BookParameterEntity();
            bookParameterEntity.setBookId(book.getBookId());
            bookParameterEntity.setParameterId(parameterId);
            bookParameterEntity.setParameterTag(tag);
            save = bookParameterService.save(bookParameterEntity);
            if (!save) {
                return R.error("添加失败！");
            }
        }

        for (String entity : propertyArray) {
            String[] split1 = entity.split("-9wdqnb9-");
            int propertyId = Integer.parseInt(split1[0]);
            String tag = split1[1];
            BookPropertyEntity bookPropertyEntity = new BookPropertyEntity();
            bookPropertyEntity.setBookId(book.getBookId());
            bookPropertyEntity.setPropertyId(propertyId);
            bookPropertyEntity.setPropertyTag(tag);
            save = bookPropertyService.save(bookPropertyEntity);
            if (!save) {
                return R.error("添加失败！");
            }
        }

        String username = tokenManager.getUserInfoFromToken(request.getHeader("token"));
        UsersEntity user = usersService.getOne(new QueryWrapper<UsersEntity>().eq("username", username));

        BookListEntity bookListEntity = new BookListEntity();
        bookListEntity.setBookId(book.getBookId());
        bookListEntity.setUserId(user.getUserId());
        bookListEntity.setNum(Integer.parseInt(num));
        save = bookListService.save(bookListEntity);
        if (!save) {
            return R.error("添加失败！");
        }
        return R.ok("添加成功");
    }

    /**
     * 更新图书信息
     * @param params
     * @return
     */
    @PostMapping("/updateBookDetail")
    public R UpdateBookDetail(@RequestParam Map<String, Object> params) {
        String title = (String) params.get("title");
        String bookId = (String) params.get("bookId");
        String price = (String) params.get("price");
        String author = (String) params.get("author");
        String categoryId = (String) params.get("categoryId");
        String[] split = categoryId.split(",");
        String id = split[split.length - 1];

        BookDetailEntity bookDetailEntity = bookDetailService.getById(bookId);
        bookDetailEntity.setTitle(title);
        bookDetailEntity.setPrice(Double.parseDouble(price));
        bookDetailEntity.setAuthor(author);
        bookDetailEntity.setCategoryId(Integer.parseInt(id));
        bookDetailEntity.setUpdateTime(DateUtils.getCurrentDate());

        boolean b = bookDetailService.updateById(bookDetailEntity);
        if(!b){
            return R.error("修改失败");
        }
        return R.ok("修改成功");
    }
}
