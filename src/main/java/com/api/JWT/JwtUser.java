package com.api.JWT;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * token中的客户端信息
 *
 * @author 李星源
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtUser implements Serializable{
    private static final long serialVersionUID=8715844912534284943L;
    private String id;
    private String uname;
    private Long loginTime;//登录时间
}
