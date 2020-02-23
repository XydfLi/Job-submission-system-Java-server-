package com.api.model;

import lombok.*;

/**
 * 用于修改密码
 *
 * @author 李星源
 * @version 1.0
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpPassword {
    private String oldPassword;//原密码
    private String newPassword;//新密码
}
