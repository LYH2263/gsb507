package com.longmao.demo.service;

import com.github.pagehelper.PageInfo;
import com.longmao.demo.entity.Book;
import com.longmao.demo.mapper.BookMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookMapper bookMapper;

    private Book testBook;

    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setId(1L);
        testBook.setTitle("Test Book");
        testBook.setAuthor("Test Author");
        testBook.setPrice(new java.math.BigDecimal("29.99"));
    }

    @Test
    void testGetBooksWithoutQuery() {
        List<Book> books = new ArrayList<>();
        books.add(testBook);
        when(bookMapper.findAll()).thenReturn(books);

        PageInfo<Book> result = bookService.getBooks(1, 10, null);

        assertNotNull(result);
        assertEquals(1, result.getList().size());
        assertEquals("Test Book", result.getList().get(0).getTitle());
        verify(bookMapper, times(1)).findAll();
        verify(bookMapper, never()).findByKeyword(anyString());
    }

    @Test
    void testGetBooksWithQuery() {
        List<Book> books = new ArrayList<>();
        books.add(testBook);
        when(bookMapper.findByKeyword("Test")).thenReturn(books);

        PageInfo<Book> result = bookService.getBooks(1, 10, "Test");

        assertNotNull(result);
        assertEquals(1, result.getList().size());
        assertEquals("Test Book", result.getList().get(0).getTitle());
        verify(bookMapper, times(1)).findByKeyword("Test");
        verify(bookMapper, never()).findAll();
    }

    @Test
    void testCreateBook() {
        when(bookMapper.insert(any(Book.class))).thenReturn(1);
        bookService.createBook(testBook);
        verify(bookMapper, times(1)).insert(testBook);
    }

    @Test
    void testUpdateBook() {
        when(bookMapper.update(any(Book.class))).thenReturn(1);
        bookService.updateBook(testBook);
        verify(bookMapper, times(1)).update(testBook);
    }

    @Test
    void testDeleteBook() {
        when(bookMapper.deleteById(anyLong())).thenReturn(1);
        bookService.deleteBook(1L);
        verify(bookMapper, times(1)).deleteById(1L);
    }
}
