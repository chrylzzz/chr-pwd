package com.chryl.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chryl.mapper.UserMapper;
import com.chryl.po.User;
import org.springframework.stereotype.Service;

/**
 * Created by Chr.yl on 2020/12/10.
 *
 * @author Chr.yl
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

}
