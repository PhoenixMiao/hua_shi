package com.phoenix.huashi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("User 用户")
public class User {
    @Id
    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("真实姓名")
    private String realname;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("年级")
    private String grade;

    @ApiModelProperty("专业")
    private String major;

    @ApiModelProperty("擅长领域")
    private String specialize;

    @ApiModelProperty("联系方式")
    private String contactInformation;

    @ApiModelProperty("个人简介")
    private String introduction;
}
