package com.bjlemon.ucenter.service;

import com.bjlemon.ucenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bjlemon.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author blackcat
 * @since 2021-01-28
 */
public interface MemberService extends IService<UcenterMember> {

    //登录的方法
    String login(UcenterMember member);

    //注册的方法
    void register(RegisterVo registerVo);

    UcenterMember getOpenIdMember(String openid);
}
