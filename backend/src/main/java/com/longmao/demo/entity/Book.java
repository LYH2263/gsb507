package com.longmao.demo.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    
    @NotBlank(message = "书名不能为空")
    private String title;
    
    @NotBlank(message = "作者不能为空")
    private String author;
    
    @NotNull(message = "价格不能为空")
    @PositiveOrZero(message = "价格不能为负数")
    private BigDecimal price;
    
    private Date createTime;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
}
