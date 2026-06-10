package com.longmao.demo.mapper;

import com.longmao.demo.entity.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * 用户管理持久层接口
 * 负责系统用户的 增、删、改、查 操作
 */
@Mapper
@CacheNamespace
public interface UserMapper {

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 用户对象或 null
     */
    @Select("SELECT * FROM sys_user WHERE username = #{username}")
    User findByUsername(String username);

    @Select("SELECT * FROM sys_user WHERE id = #{id}")
    User findById(Long id);

    /**
     * 获取系统所有用户列表
     * 管理员专用接口
     * @return 用户列表集
     */
    @Select("SELECT * FROM sys_user")
    List<User> findAll();

    /**
     * 新增用户信息
     * 自动处理主键自增并回填到对象 ID
     * @param user 用户实体
     * @return 受影响行数
     */
    @Insert("INSERT INTO sys_user(username, password, role, create_time) VALUES(#{username}, #{password}, #{role}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    /**
     * 更新用户信息
     * 目前支持更新 密码 和 角色
     * @param user 待更新用户实体
     * @return 受影响行数
     */
    @Update("UPDATE sys_user SET password = #{password}, role = #{role} WHERE id = #{id}")
    int update(User user);

    @Update("UPDATE sys_user SET password = #{password} WHERE id = #{id}")
    int updateProfile(User user);

    /**
     * 根据 ID 物理删除用户
     * @param id 用户唯一标识
     * @return 受影响行数
     */
    @Delete("DELETE FROM sys_user WHERE id = #{id}")
    int deleteById(Long id);
}
