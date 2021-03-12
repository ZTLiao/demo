package com.ztliao.shardingjdbcdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ztliao.shardingjdbcdemo.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @author: liaozetao
 * @date: 2021/3/12 9:42 PM
 * @description:
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
