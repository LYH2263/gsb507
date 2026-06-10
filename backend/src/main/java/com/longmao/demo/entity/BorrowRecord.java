package com.longmao.demo.entity;

import java.util.Date;

public class BorrowRecord {
    private Long id;
    private Long userId;
    private Long bookId;
    private Date borrowTime;
    private Date returnTime;
    private String status;
    
    // Additional fields for display
    private String bookTitle;
    private String bookAuthor;
    private String username;
    private Book book; // 用于展示延迟加载效果

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public Date getBorrowTime() { return borrowTime; }
    public void setBorrowTime(Date borrowTime) { this.borrowTime = borrowTime; }

    public Date getReturnTime() { return returnTime; }
    public void setReturnTime(Date returnTime) { this.returnTime = returnTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public String getBookAuthor() { return bookAuthor; }
    public void setBookAuthor(String bookAuthor) { this.bookAuthor = bookAuthor; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
}
