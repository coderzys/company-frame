package com.coder.lesson.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @类名 TestReqVO
 * @描述 TODO
 * @创建人 张全蛋
 * @创建日期 2020/2/19 12:49
 * @版本 1.0
 **/
@Data
public class TestReqVO {

    @NotBlank(message = "name 不能为空")
    @ApiModelProperty(value = "名称")
    private String name;

    @NotNull(message = "age 不能为空")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @NotEmpty(message = "id 集合不能为空")
    @ApiModelProperty(value = "id集合")
    private List<String> ids;

}
