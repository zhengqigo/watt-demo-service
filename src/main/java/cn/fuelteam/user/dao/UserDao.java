package cn.fuelteam.user.dao;

import cn.fuelteam.user.domain.User;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface UserDao extends Mapper<User>, MySqlMapper<User> {}
