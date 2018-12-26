package cn.fuelteam.user.client.impl;

import org.fuelteam.watt.lucky.utils.BeanMapper;
import org.fuelteam.watt.result.Result;
import org.fuelteam.watt.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;

import cn.fuelteam.user.client.UserClient;
import cn.fuelteam.user.dao.UserDao;
import cn.fuelteam.user.domain.User;
import cn.fuelteam.user.dto.UserDto;

@Service(version = "1.0.0", timeout = 1000)
public class UserClientImpl implements UserClient {

    @Autowired
    private UserDao userDao;

    public Result<UserDto> findById(Long id) {
        User user = userDao.selectOne(new User().setId(id));
        if (user == null) return new Result<UserDto>().code(ResultCode.SUCCESS).data(null);
        return new Result<UserDto>().code(ResultCode.SUCCESS).data(BeanMapper.map(user, UserDto.class));
    }

    public Result<Integer> insert(UserDto userDto) {
        User user = BeanMapper.map(userDto, User.class);
        Integer result = userDao.insert(user);
        return new Result<Integer>().code(ResultCode.SUCCESS).data(result);
    }
}
