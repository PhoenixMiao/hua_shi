package com.phoenix.huashi.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("SearchRecord 搜索记录")
public class SearchRecord implements Serializable {
    @Id
    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户id")
    private Long user_id;

    @ApiModelProperty("搜索内容")
    private String contents;

    @ApiModelProperty("搜索时间")
    private String create_time;
}
