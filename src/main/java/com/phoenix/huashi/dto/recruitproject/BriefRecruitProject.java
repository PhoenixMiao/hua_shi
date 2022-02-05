package com.phoenix.huashi.dto.recruitproject;
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
@ApiModel("BriefRecruitProject 招募项目简要信息")

public class BriefRecruitProject {
    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("项目名称")
    private String name;

    @ApiModelProperty("标签一")
    private String tag1;
    @ApiModelProperty("标签二")
    private String tag2;
    @ApiModelProperty("标签三")
    private String tag3;

    @ApiModelProperty("要求简述")
    private String brief_demand;

    @ApiModelProperty("状态")
    private Integer status;
}
