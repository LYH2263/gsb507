package com.longmao.demo.service;

import com.longmao.demo.entity.BorrowRecord;
import com.longmao.demo.entity.User;
import com.longmao.demo.mapper.BorrowRecordMapper;
import com.longmao.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 借阅业务核心逻辑层
 * 负责借书规则校验、还书流程处理及流水统计
 */
@Service
public class BorrowService {

    @Autowired
    private BorrowRecordMapper borrowRecordMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 获取指定用户的借阅流水
     * @param username 用户名
     * @return 借阅记录列表
     */
    public List<BorrowRecord> getMyBorrows(String username) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("系统用户未找到");
        }
        // 返回记录集
        return borrowRecordMapper.findByUserId(user.getId());
    }

    /**
     * 获取全站借阅流水列表
     * 管理员专用
     * @return 全量流水集
     */
    public List<BorrowRecord> getAllBorrows() {
        return borrowRecordMapper.findAll();
    }

    /**
     * 执行借书操作
     * 事务性操作，确保状态一致
     * @param username 借阅人账号
     * @param bookId 目标图书 ID
     */
    @Transactional
    public void borrowBook(String username, Long bookId) {
        // 定位用户
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("无法定位借阅人信息");
        }
        
        // 幂等性校验：防止重复借阅未归还的书籍
        List<BorrowRecord> existing = borrowRecordMapper.findBorrowedByUserIdAndBookId(user.getId(), bookId);
        if (!existing.isEmpty()) {
            throw new RuntimeException("您已借阅该书且未归还，请先处理前序记录");
        }

        // 全局库存校验：防止同一本书被多人同时借阅
        int activeBorrows = borrowRecordMapper.countActiveBorrowsByBookId(bookId);
        if (activeBorrows > 0) {
            throw new RuntimeException("该书已被借出，请等待归还");
        }

        // 构造流水记录
        BorrowRecord record = new BorrowRecord();
        record.setUserId(user.getId());
        record.setBookId(bookId);
        // 执行持久层写入
        borrowRecordMapper.insert(record);
    }

    /**
     * 执行还书操作
     * @param id 借阅流水 ID
     * @param username 当前用户
     */
    public void returnBook(Long id, String username) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("无法定位用户信息");
        }
        BorrowRecord record = borrowRecordMapper.findById(id);
        if (record == null) {
            throw new RuntimeException("借阅记录不存在");
        }
        if (!record.getUserId().equals(user.getId()) && !"ADMIN".equals(user.getRole())) {
            throw new RuntimeException("越权操作：只能归还自己的借阅记录");
        }
        borrowRecordMapper.returnBook(id);
    }
}
