package com.tiger.common.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description: 发送短信配置
 * @Auther: hubaocheng
 * @Date: 2023/07/17
 * @company：csht
 */

@Data
@Accessors(chain = true)
@Component
@ConfigurationProperties(prefix = "aliyuncs")
public class SmsMessageConfig {

    private String regionId;

    private String accessKeyId;

    private String secret;

    private String sysDomain;

    private String signName;

    private String product;

    private String endpointName;
}
