package cn.fuelteam.data.dao;

import cn.fuelteam.data.domain.Operation;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

public interface OperationDao extends Mapper<Operation>, MySqlMapper<Operation> {}
