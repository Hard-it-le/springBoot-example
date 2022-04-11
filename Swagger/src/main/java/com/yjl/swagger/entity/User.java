package com.yjl.swagger.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/04/11
 * @ApiModel 注解用于实体类，表示对类进行说明，用于参数用实体类接收。
 * @ApiModelProperty 注解用于类中属性，表示对 model 属性的说明或者数据操作更改。
 */
@ApiModel(value = "用户实体类")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @ApiModelProperty(value = "用户唯一标识")
    private Long id;

    @ApiModelProperty(value = "用户姓名")
    private String username;

    @ApiModelProperty(value = "用户密码")
    private String password;
}
