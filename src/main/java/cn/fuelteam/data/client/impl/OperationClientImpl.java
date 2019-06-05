package cn.fuelteam.data.client.impl;

import org.apache.dubbo.config.annotation.Service;
import org.fuelteam.watt.lucky.mapper.BeanMapper;
import org.fuelteam.watt.result.Result;
import org.fuelteam.watt.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;

import cn.fuelteam.data.client.OperationClient;
import cn.fuelteam.data.dao.OperationDao;
import cn.fuelteam.data.domain.Operation;
import cn.fuelteam.data.dto.OperationDto;

@Service(version = "1.0.0", timeout = 1000)
public class OperationClientImpl implements OperationClient {

    @Autowired
    private OperationDao operationDao;

    public Result<OperationDto> findById(Long id) {
        Operation operation = operationDao.selectOne(new Operation().setId(id));
        if (operation == null) return new Result<OperationDto>().code(ResultCode.SUCCESS).data(null);
        return new Result<OperationDto>().code(ResultCode.SUCCESS).data(BeanMapper.map(operation, OperationDto.class));
    }

    public Result<Integer> insert(OperationDto operationDto) {
        Operation operation = BeanMapper.map(operationDto, Operation.class);
        Integer result = operationDao.insert(operation);
        return new Result<Integer>().code(ResultCode.SUCCESS).data(result);
    }
}
