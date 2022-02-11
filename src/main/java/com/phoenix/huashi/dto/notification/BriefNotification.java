package com.phoenix.huashi.dto.notification;
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
@ApiModel("BriefNotification 通知公告简要信息")
public class BriefNotification implements Serializable {

    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("信息来源")
    private String source;

    @ApiModelProperty("发布日期")
    private String publishDate;



}
