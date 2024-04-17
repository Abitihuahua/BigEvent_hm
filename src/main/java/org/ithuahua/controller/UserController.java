package org.ithuahua.controller;

import org.ithuahua.pojo.Result;
import org.ithuahua.pojo.User;
import org.ithuahua.utils.Md5Util;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.ithuahua.service.UserService;
import org.ithuahua.utils.JwtUtil;
import org.ithuahua.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    //        校验用户名长度
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username,
                           @Pattern(regexp = "^\\S{5,16}$") String password){
            //        查询用户是否注册
            User u = userService.findByUserName(username);
            if (u==null){
//            注册用户
                userService.register(username,password);
                return Result.success();
            }else{
//            占用
                return Result.error("用户名已被占用");
            }
    }

    @PostMapping("/login")
    //        校验用户名长度
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username,
                           @Pattern(regexp = "^\\S{5,16}$") String password){
//            根据用户名查询user
        User loginUser = userService.findByUserName(username);
//            判断是否查询到
        if (loginUser == null){
            return Result.error("用户名错误");
        }
//            判断密码是否正确  loginUser对象中的password是密文
        if (Md5Util.getMD5String(password).equals(loginUser.getPassword())){
//            登录成功
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    @GetMapping("/userInfo")
    public Result<User> userInfo(){
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");

        User user = userService.findByUserName(username);
        return Result.success(user);
    }

    @PostMapping("/update")
    public Result update(@RequestBody @Validated User user){
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/rePwd")
    public Result rePwd(@RequestBody Map<String,String> params){
        //校验参数
        String oldPassword = params.get("old_password");
        String newPassword = params.get("new_password");
        String rePassword = params.get("re_password");
        //添加校验
        if (!StringUtils.hasLength(oldPassword) ||
                !StringUtils.hasLength(newPassword) ||
                !StringUtils.hasLength(rePassword)){
            return Result.error("缺少必要的参数");
        }

        //原密码是否正确
        Map<String,Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User loginuser = userService.findByUserName(username);
        if (!loginuser.getPassword().equals(Md5Util.getMD5String(oldPassword))){
            return Result.error("原密码不正确");
        }
        //判断两次的输入密码是否一致
        if(!rePassword.equals(newPassword)){
            return Result.error("两次填写的新密码不一样");
        }
        userService.rePwd(newPassword);
        return Result.success();
    }

}
