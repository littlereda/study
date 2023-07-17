package com.tiger.common.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONString;
import cn.hutool.json.JSONUtil;
import cn.hutool.json.serialize.JSONArraySerializer;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.tiger.common.config.SmsMessageConfig;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author hbc
 * @description: 短信通知发送
 * @date 2023/7/17 11:24
 */
@Service
@Slf4j
public class SmsUtil {

    @Resource
    private SmsMessageConfig smsConfig;

    public String sendSms(String phoneNumber, String message, String code) throws ClientException {

        // 创建DefaultAcsClient实例并初始化
        IClientProfile profile = DefaultProfile.getProfile(smsConfig.getRegionId(), smsConfig.getAccessKeyId(), smsConfig.getSecret());
        DefaultProfile.addEndpoint(smsConfig.getEndpointName(), smsConfig.getRegionId(), smsConfig.getProduct(), smsConfig.getSysDomain());
        DefaultAcsClient client = new DefaultAcsClient(profile);

        // 构造请求对象
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phoneNumber);  // 设置手机号码
        request.setSignName(smsConfig.getSignName());  // 设置短信签名，需提前在阿里云控制台申请
        request.setTemplateCode(code);  // 设置短信模板Code，需提前在阿里云控制台申请
        request.setTemplateParam(message);  // 设置短信模板参数

        // 发送请求并处理响应
        SendSmsResponse response = client.getAcsResponse(request);
        if ("OK".equals(response.getCode())) {
            log.info("短信发送成功");
        } else {
            log.info("短信发送失败：" + response.getMessage());
        }
        return JSONUtil.toJsonStr(response);
    }
}
