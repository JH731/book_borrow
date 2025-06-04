package com.example.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "borrow.jwt")
@Data
public class JwtProperties {
    /**
     *管理端员工生成jwt令牌相关配置
     */
    private String adminSecretKey;
    private Long adminTtl;
    private String adminTokenName;

    /**
     *用户端用户生成jwt令牌相关配置
     */
    private String userSecretKey;
    private Long userTtl;
    private String userTokenName;

    /**
     * 统一的jwt令牌配置
     */
    private String SecretKey;
    private Long Ttl;
    private String tokenName;
}
