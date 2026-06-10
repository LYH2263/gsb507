package com.longmao.demo.service;

import com.longmao.demo.entity.User;
import com.longmao.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private org.springframework.cache.CacheManager cacheManager;

    /**
     * 根据用户名加载用户核心数据
     * 用于 Spring Security 身份验证
     * @param username 用户名
     * @return 包含权限信息的 UserDetails 对象
     * @throws UsernameNotFoundException 如果用户未发现
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            log.warn("登录尝试失败：用户 {} 不存在", username);
            throw new UsernameNotFoundException("用户不存在");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

    /**
     * 获取全局用户清单
     * @return 用户实体列表
     */
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    /**
     * 业务逻辑：新增系统用户
     * 处理密码加盐加密及持久化
     * @param user 用户实体
     */
    public void createUser(User user) {
        log.info("开始创建用户：{}", user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
        log.info("用户 {} 创建成功", user.getUsername());
    }

    /**
     * 更新已有用户信息
     * 自动识别并加密新密码（若存在）
     * @param user 待更新用户对象
     */
    public void updateUser(User user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
             user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userMapper.update(user);
    }

    /**
     * 用户自助更新个人档案
     * 目前仅支持修改登录密码
     * @param username 目标用户名
     * @param newPassword 新明文密码
     */
    public void updateUserProfile(String username, String newPassword) {
        log.info("正在更新用户 {} 的个人信息", username);
        User user = userMapper.findByUsername(username);
        if (user == null) {
            log.error("更新档案失败：未找到用户 {}", username);
            throw new RuntimeException("用户不存在");
        }
        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
            int rows = userMapper.updateProfile(user);
            
            // 缓存清理：确保数据一致性
            try {
               if (cacheManager.getCache("users") != null) {
                   cacheManager.getCache("users").clear();
               }
            } catch (Exception e) {
                log.error("缓存清理异常", e);
            }
            log.info("用户 {} 档案更新成功，受影响行数：{}", username, rows);
        } else {
            log.warn("用户 {} 尝试提交空密码，已忽略", username);
        }
    }

    /**
     * 物理注销用户账户
     * 预置权限校验：禁止删除超级管理员
     * @param id 用户 ID
     */
    public void deleteUser(Long id) {
        User user = userMapper.findById(id);
        if (user != null && "ADMIN".equals(user.getRole())) {
            log.error("越权操作拦截：尝试删除管理员 ID {}", id);
            throw new RuntimeException("无法删除管理员账户");
        }
        userMapper.deleteById(id);
        log.info("用户 ID {} 已删除", id);
    }
}
