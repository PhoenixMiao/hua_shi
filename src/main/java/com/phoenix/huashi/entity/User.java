package com.phoenix.huashi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("User 用户")
public class User implements Serializable {
    @Id
    @ApiModelProperty("用户id")
    private Long id;

    @Id
    @ApiModelProperty("创赛号")
    private String chuangNum;

    @ApiModelProperty("会话id")
    private String sessionId;

    @ApiModelProperty("用户唯一标识")
    private String openId;

    @ApiModelProperty("unionid")
    private String unionId;

    @ApiModelProperty("会话密钥")
    private String sessionKey;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("真实姓名")
    private String name;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("学校")
    private String school;

    @ApiModelProperty("学院")
    private String department;

    @ApiModelProperty("专业")
    private String major;

    @ApiModelProperty("年级")
    private String grade;

    @ApiModelProperty("手机号")
    private String telephone;

    @ApiModelProperty("QQ号")
    private String QQ;

    @ApiModelProperty("微信号")
    private String wechatNum;

    @ApiModelProperty("头像")
    private String portrait;

    @ApiModelProperty("简历文本")
    private String resume;

    @ApiModelProperty("简历附件1")
    private String attachment;

    @ApiModelProperty("简历附件2")
    private String attachment2;

    @ApiModelProperty("简历附件3")
    private String attachment3;

    @ApiModelProperty("学号")
    private String studentNumber;

    public User(String sessionId, String openId, String unionId, String sessionKey, String createTime,String nickname) {
        this.sessionId = sessionId;
        this.openId = openId;
        this.unionId = unionId;
        this.sessionKey = sessionKey;
        this.createTime = createTime;
        this.nickname = nickname;
    }
}
