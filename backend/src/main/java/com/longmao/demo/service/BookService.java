package com.longmao.demo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.longmao.demo.entity.Book;
import com.longmao.demo.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 图书业务逻辑层
 * 封装分页算法与图书 CRUD 业务校验
 */
@Service
public class BookService {

    @Autowired
    private BookMapper bookMapper;

    /**
     * 分页检索图书列表
     * @param page 起始页
     * @param size 页面规模
     * @return 包含书籍列表及总页数的分页对象
     */
    @Cacheable(value = "books", key = "#page + '-' + #size + '-' + (#query ?: '')")
    public PageInfo<Book> getBooks(int page, int size, String query) {
        // 开启 PageHelper 分页拦截器
        PageHelper.startPage(page, size);
        List<Book> list;
        if (query != null && !query.trim().isEmpty()) {
            list = bookMapper.findByKeyword(query.trim());
        } else {
            list = bookMapper.findAll();
        }
        // 自动提取分页元数据
        return new PageInfo<>(list);
    }

    @CacheEvict(value = "books", allEntries = true)
    public void createBook(Book book) {
        bookMapper.insert(book);
    }

    @CacheEvict(value = "books", allEntries = true)
    public void updateBook(Book book) {
        bookMapper.update(book);
    }

    @CacheEvict(value = "books", allEntries = true)
    public void deleteBook(Long id) {
        bookMapper.deleteById(id);
    }
}
