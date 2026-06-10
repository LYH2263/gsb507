package com.longmao.demo.mapper;

import com.longmao.demo.entity.BorrowRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface BorrowRecordMapper {

    @Insert("INSERT INTO sys_borrow_record(user_id, book_id, status) VALUES(#{userId}, #{bookId}, 'BORROWED')")
    void insert(BorrowRecord record);

    @Update("UPDATE sys_borrow_record SET status = 'RETURNED', return_time = NOW() WHERE id = #{id}")
    void returnBook(Long id);

    @Select("SELECT * FROM sys_borrow_record WHERE id = #{id}")
    @Results({
        @Result(property = "id", column = "id", id = true),
        @Result(property = "bookId", column = "book_id"),
        @Result(property = "book", column = "book_id", one = @One(select = "com.longmao.demo.mapper.BookMapper.findById", fetchType = FetchType.LAZY))
    })
    BorrowRecord findById(Long id);

    @Select("SELECT r.*, b.title as bookTitle, b.author as bookAuthor, u.username as username FROM sys_borrow_record r " +
            "LEFT JOIN sys_book b ON r.book_id = b.id " +
            "LEFT JOIN sys_user u ON r.user_id = u.id " +
            "WHERE r.user_id = #{userId} " +
            "ORDER BY r.borrow_time DESC")
    List<BorrowRecord> findByUserId(Long userId);

    @Select("SELECT COUNT(*) FROM sys_borrow_record WHERE book_id = #{bookId} AND status = 'BORROWED'")
    int countActiveBorrowsByBookId(Long bookId);

    @Select("SELECT r.*, b.title as bookTitle, b.author as bookAuthor, u.username as username FROM sys_borrow_record r " +
            "LEFT JOIN sys_book b ON r.book_id = b.id " +
            "LEFT JOIN sys_user u ON r.user_id = u.id " +
            "ORDER BY r.borrow_time DESC")
    List<BorrowRecord> findAll();

    @Select("SELECT * FROM sys_borrow_record WHERE user_id = #{userId} AND book_id = #{bookId} AND status = 'BORROWED'")
    List<BorrowRecord> findBorrowedByUserIdAndBookId(Long userId, Long bookId);
}
