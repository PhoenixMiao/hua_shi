package com.phoenix.huashi.controller.request;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("UpdateUserByIdRequest 更新用户信息")
public class UpdateUserByIdRequest {
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("性别")
    private int gender;
    @ApiModelProperty("电话号码")
    private String telephone;
    @ApiModelProperty("真实姓名")
    private String name;
    @ApiModelProperty("头像")
    private String portrait;
    @ApiModelProperty("学校")
    private String school;
    @ApiModelProperty("学院")
    private String department;
    @ApiModelProperty("专业")
    private String major;
    @ApiModelProperty("年级")
    private String grade;
    @ApiModelProperty("QQ号")
    private String QQ;
    @ApiModelProperty("微信号")
    private String wechatNum;
    @ApiModelProperty("简历文本")
    private String resume;
    @ApiModelProperty("简历附件")
    private String attachment;
}
