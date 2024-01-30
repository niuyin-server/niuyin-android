package com.roydon.niuyin.http.response;

import com.roydon.niuyin.http.response.entity.Member;
import com.roydon.niuyin.http.response.entity.MemberInfo;

public class MemberInfoVO extends Member {

    // 用户详情
    private MemberInfo memberInfo;

    public MemberInfo getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }

    @Override
    public String toString() {
        return "MemberInfoVO{" +
                "memberInfo=" + memberInfo +
                '}';
    }
}