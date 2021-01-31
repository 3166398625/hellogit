package com.bjlemon.ucenter.controller;


import com.bjlemon.commonutils.JwtUtils;
import com.bjlemon.commonutils.R;
import com.bjlemon.commonutils.UcenterMemberOrder;
import com.bjlemon.ucenter.entity.UcenterMember;
import com.bjlemon.ucenter.entity.vo.RegisterVo;
import com.bjlemon.ucenter.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author blackcat
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/educenter/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    //登录
    @PostMapping("/login")
    public R loginUser(@RequestBody UcenterMember ucenterMember) {
        //member对象封装手机号和密码
        //调用service方法实现登录
        //返回token值，使用jwt生成
        String token = memberService.login(ucenterMember);
        return R.ok().data("token",token);
    }

    //注册
    @PostMapping("/register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        UcenterMember ucenterMember = memberService.getById(memberId);
        return R.ok().data("userInfo", ucenterMember);
    }

    //根据用户id获取用户信息
    @PostMapping("/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id) {
        UcenterMember member = memberService.getById(id);
        //把member对象里面值复制给UcenterMemberOrder对象
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,ucenterMemberOrder);
        return ucenterMemberOrder;
    }
}

