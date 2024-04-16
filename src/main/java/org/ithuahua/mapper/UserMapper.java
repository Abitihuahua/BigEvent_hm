package org.ithuahua.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.ithuahua.pojo.User;

@Mapper
public interface UserMapper {

//    根据用户名查询是否注册
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);
//    添加注册信息
    @Insert("insert into user(username,password,create_time,update_time)"
    +"values(#{username},#{password},now(),now())")
    void add(String username, String password);
    //更新
    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id=#{id}")
    void update(User user);

    @Update("update user set user_pic=#{avatarUrl},update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl, Integer id);

    @Update("update user set password=#{md5String},update_time=now() where id=#{id}")
    void rePwd(String md5String, Integer id);
}
