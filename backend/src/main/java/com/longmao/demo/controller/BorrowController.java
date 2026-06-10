package com.longmao.demo.controller;

import com.longmao.demo.common.Result;
import com.longmao.demo.entity.BorrowRecord;
import com.longmao.demo.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 借阅管理控制器
 * 提供用户借阅流水查询、借书、还书等业务接口
 */
@RestController
@RequestMapping("/api/borrows")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    /**
     * 获取当前登录用户的借阅历史
     * @return 个人借阅记录列表
     */
    @GetMapping("/my")
    public Result<List<BorrowRecord>> myBorrows() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return Result.success(borrowService.getMyBorrows(username));
    }

    /**
     * 管理员：获取全站所有借阅历史
     * @return 全量借阅记录集
     */
    @GetMapping("/all")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public Result<List<BorrowRecord>> allBorrows() {
        return Result.success(borrowService.getAllBorrows());
    }

    /**
     * 借阅图书申请
     * @param bookId 目标图书 ID
     * @return 借阅结果
     */
    @PostMapping("/{bookId}")
    public Result<String> borrow(@PathVariable Long bookId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            borrowService.borrowBook(username, bookId);
            return Result.success("借阅成功");
        } catch (RuntimeException e) {
            return Result.error(500, e.getMessage());
        }
    }

    /**
     * 归还已借阅图书
     * @param id 借阅记录流水 ID
     * @return 归还结果
     */
    @PutMapping("/return/{id}")
    public Result<String> returnBook(@PathVariable Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            borrowService.returnBook(id, username);
            return Result.success("归还成功");
        } catch (RuntimeException e) {
            return Result.error(500, e.getMessage());
        }
    }
}
