package cn.fuelteam.data.client.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.fuelteam.watt.lucky.utils.BeanMapper;
import org.fuelteam.watt.result.Result;
import org.fuelteam.watt.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;

import cn.fuelteam.data.client.OperationClient;
import cn.fuelteam.data.dao.OperationDao;
import cn.fuelteam.data.domain.Operation;
import cn.fuelteam.data.dto.OperationDto;

@Service(version = "1.0.0", protocol = { "dubbo", "rest" }, registry = "${dubbo.registry.id}", timeout = 1000)
@Path("operation")
@Produces({ ContentType.APPLICATION_JSON_UTF_8 })
@Consumes({ MediaType.APPLICATION_JSON })
public class OperationClientImpl implements OperationClient {

    @Autowired
    private OperationDao operationDao;

    @GET
    @Path("/findById")
    public Result<OperationDto> findById(@QueryParam("id") Long id) {
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
