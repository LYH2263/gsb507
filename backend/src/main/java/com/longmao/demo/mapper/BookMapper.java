package com.longmao.demo.mapper;

import com.longmao.demo.entity.Book;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
@CacheNamespace // 启用二级缓存
public interface BookMapper {

    @Select("SELECT * FROM sys_book")
    List<Book> findAll();

    @Select("SELECT * FROM sys_book WHERE title LIKE CONCAT('%', #{keyword}, '%') OR author LIKE CONCAT('%', #{keyword}, '%')")
    List<Book> findByKeyword(@Param("keyword") String keyword);

    @Select("SELECT * FROM sys_book WHERE id = #{id}")
    Book findById(Long id);

    @Insert("INSERT INTO sys_book(title, author, price, create_time) VALUES(#{title}, #{author}, #{price}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Book book);

    @Update("UPDATE sys_book SET title = #{title}, author = #{author}, price = #{price} WHERE id = #{id}")
    int update(Book book);

    @Delete("DELETE FROM sys_book WHERE id = #{id}")
    int deleteById(Long id);
}
