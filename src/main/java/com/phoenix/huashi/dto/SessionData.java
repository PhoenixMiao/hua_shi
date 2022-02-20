package com.phoenix.huashi.dto;

import com.phoenix.huashi.common.CommonErrorCode;
import com.phoenix.huashi.entity.User;
import com.phoenix.huashi.util.AssertUtil;
import com.phoenix.huashi.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * session缓存实体
 *
 * @author yan on 2020-02-27
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("SessionData 会话实体")
public class SessionData implements Serializable {

    /**
     * {@link User}
     */
    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("创赛号")
    private String chuangNum;

    @ApiModelProperty("会话id")
    private String sessionId;

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

    @ApiModelProperty("resume")
    private String attachment;

    public SessionData(User user) {
        AssertUtil.notNull(user, CommonErrorCode.USER_NOT_EXIST);
        this.id = user.getId();
        this.chuangNum = user.getChuangNum();
        this.sessionId = user.getSessionId();
        this.createTime = user.getCreateTime();
        this.nickname = user.getNickname();
        this.name = user.getName();
        this.gender = user.getGender();
        this.school = user.getSchool();
        this.department = user.getDepartment();
        this.major = user.getMajor();
        this.grade = user.getGrade();
        this.telephone = user.getTelephone();
        this.QQ = user.getQQ();
        this.wechatNum = user.getWechatNum();
        this.portrait = user.getPortrait();
        this.resume = user.getResume();
        this.attachment = user.getAttachment();
    }
}
