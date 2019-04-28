package com.safesoft.springboot.basessm.dao;


import com.safesoft.springboot.basessm.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author jay.zhou
 * @date 2019/4/25
 * @time 8:36
 */
@Repository
@Mapper
public interface UserDao {

    /**
     * 根据主键获取实体
     *
     * @param id 主键id
     * @return 实体
     */
    UserEntity selectByPrimaryKey(@Param("id") Long id);

    /**
     * 新增
     *
     * @param userEntity 用户实体
     * @return 是否成功
     */
    Boolean insert(UserEntity userEntity);
}
