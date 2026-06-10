package com.longmao.demo.controller;

import com.github.pagehelper.PageInfo;
import com.longmao.demo.common.Result;
import com.longmao.demo.entity.Book;
import com.longmao.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * 图书管理控制器
 * 提供基于 RESTful 规范的图书资源操作接口
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 分页查询图书列表
     * @param page 当前页码，默认为 1
     * @param size 每页记录数，默认为 10
     * @return 封装了分页信息的统一结果集
     */
    @GetMapping
    public Result<PageInfo<Book>> list(@RequestParam(defaultValue = "1") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(required = false) String query) {
        // 调用 Service 层获取分页数据（支持通过关键字查询）
        return Result.success(bookService.getBooks(page, size, query));
    }

    /**
     * 创建新图书资源
     * 需管理员权限
     * @param book 图书实体数据
     * @return 操作成功信息
     */
    @PostMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public Result<String> create(@Valid @RequestBody Book book) {
        bookService.createBook(book);
        return Result.success("图书创建成功");
    }

    /**
     * 更新现有图书信息
     * 需提供图书 ID 及其余修改字段
     * @param book 图书实体
     * @return 操作成功信息
     */
    @PutMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public Result<String> update(@Valid @RequestBody Book book) {
        bookService.updateBook(book);
        return Result.success("图书更新成功");
    }

    /**
     * 移除特定图书资源
     * 物理删除
     * @param id 目标图书 ID
     * @return 操作成功信息
     */
    @DeleteMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public Result<String> delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return Result.success("图书删除成功");
    }
}
