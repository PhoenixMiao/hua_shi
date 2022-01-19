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
    @ApiModelProperty("注册时间")
    private String create_time;
    @ApiModelProperty("昵称")
    private String nickname;
    @ApiModelProperty("性别")
    private Integer gender;
    @ApiModelProperty("头像")
    private String portrait;

    public SessionData(User user){
        AssertUtil.notNull(user, CommonErrorCode.USER_NOT_EXIST);
//        if(user == null){
//            create_time = TimeUtil.getCurrentTimestamp();
//            nickname = "小镇用户";
//            gender = 0;
//            campus = CampusEnum.ZHONG_BEI.getName();
//            signature = "ta很懒，还没有签名哦~";
//            schoolAuth = 0;
//            return;
//        }

//        id = user.getId();
//        create_time = user.getCreate_time();
//        nickname = user.getNickname();
//        gender = user.getGender();
//        portrait = user.getPortrait();
    }
}
