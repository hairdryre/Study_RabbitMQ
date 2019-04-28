package com.safesoft.springboot.basessm.service;


import com.safesoft.springboot.basessm.entity.UserEntity;

/**
 * @author jay.zhou
 * @date 2019/4/25
 * @time 8:46
 */
public interface UserService {
    /**
     * 根据主键获取实体
     *
     * @param id 主键id
     * @return 实体
     */
    UserEntity selectByPrimaryKey(Long id);

    /**
     * 新增
     *
     * @param userEntity 用户实体
     * @return 是否成功
     */
    Boolean insert(UserEntity userEntity);

}
