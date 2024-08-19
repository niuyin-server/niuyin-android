package com.roydon.niuyin.http.response.member;

import com.roydon.niuyin.http.response.entity.Member;
import com.roydon.niuyin.http.response.entity.MemberInfo;

public class AppMemberInfoVO extends Member {

    // 用户详情
    private MemberInfo memberInfo;

    // 是否关注
    private Boolean weatherFollow;

    // 获赞
    private Long likeCount;

    // 关注
    private Long followCount;

    // 粉丝
    private Long fansCount;

    public AppMemberInfoVO(MemberInfo memberInfo, Boolean weatherFollow, Long likeCount, Long followCount, Long fansCount) {
        this.memberInfo = memberInfo;
        this.weatherFollow = weatherFollow;
        this.likeCount = likeCount;
        this.followCount = followCount;
        this.fansCount = fansCount;
    }

    public MemberInfo getMemberInfo() {
        return memberInfo;
    }

    public void setMemberInfo(MemberInfo memberInfo) {
        this.memberInfo = memberInfo;
    }

    public Boolean getWeatherFollow() {
        return weatherFollow;
    }

    public void setWeatherFollow(Boolean weatherFollow) {
        this.weatherFollow = weatherFollow;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Long followCount) {
        this.followCount = followCount;
    }

    public Long getFansCount() {
        return fansCount;
    }

    public void setFansCount(Long fansCount) {
        this.fansCount = fansCount;
    }
}
