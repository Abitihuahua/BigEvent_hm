package org.ithuahua.service.impl;

import org.ithuahua.pojo.User;
import org.ithuahua.service.UserService;
import org.ithuahua.utils.Md5Util;
import org.ithuahua.mapper.UserMapper;
import org.ithuahua.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserName(String username) {
        User u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
//        加密
        String md5String = Md5Util.getMD5String(password);
//        添加
        userMapper.add(username,md5String);
    }

    @Override
    public void update(User user) {
//        将当前时间设置为跟新时间
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updateAvatar(avatarUrl,id);
    }

    @Override
    public void rePwd(String newPassword) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.rePwd(Md5Util.getMD5String(newPassword),id);
    }
}
