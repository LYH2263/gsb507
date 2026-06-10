package com.longmao.demo.controller;

import com.longmao.demo.common.Result;
import com.longmao.demo.entity.User;
import com.longmao.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<List<User>> list() {
        return Result.success(userService.getAllUsers());
    }

    /**
     * 用户注册接口
     * 公开访问，无需权限
     * @param user 用户注册信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<String> register(@Valid @RequestBody User user) {
        user.setRole("USER"); // 默认角色为普通用户
        userService.createUser(user);
        return Result.success("注册成功");
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> create(@Valid @RequestBody User user) {
        userService.createUser(user);
        return Result.success("用户创建成功");
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> update(@Valid @RequestBody User user) {
        userService.updateUser(user);
        return Result.success("用户更新成功");
    }

    @PutMapping("/profile")
    public Result<String> updateProfile(@RequestBody User user) {
        String username = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updateUserProfile(username, user.getPassword());
        return Result.success("个人信息更新成功");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<String> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return Result.success("用户删除成功");
    }
}
