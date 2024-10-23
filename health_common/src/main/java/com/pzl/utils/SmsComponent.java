package com.pzl.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;

import java.util.Random;

// 导入对应SMS模块的client

/**
 * 腾讯云短信
 */
public class SmsComponent {
    //腾讯云账户密钥对 secretId 和 secretKey
    private String secretId="";
    private String secretKey="";
    //SDKAppID
    private String appid="";
    //短信签名内容
    private String sign="ABC管理系统公众号";
    //模板 ID
    private String templateId="";

    public  void sendSmsCode(String phone, String code) {
        try {
            //实例化一个认证对象，入参需要传入腾讯云账户密钥对 secretId 和 secretKey
            Credential cred = new Credential(secretId, secretKey);
            // 实例化 SMS 的 client 对象第二个参数是地域信息，可以直接填写字符串 ap-guangzhou，或者引用预设的常量
            SmsClient client = new SmsClient(cred, "");
            //实例化一个请求对象，根据调用的接口和实际情况，可以进一步设置请求参数
            SendSmsRequest req = new SendSmsRequest();
            //短信应用 ID: 在 [短信控制台] 添加应用后生成的实际 SDKAppID，例如1400006666
            req.setSmsSdkAppid(appid);
            /* 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名，可登录 [短信控制台] 查看签名信息 */
            req.setSign(sign);
            /* 模板 ID: 必须填写已审核通过的模板 ID，可登录 [短信控制台] 查看模板 ID */
            req.setTemplateID(templateId);
            /* 模板参数: 若无模板参数，则设置为空*/
            String[] templateParams = {code,"5"};
            req.setTemplateParamSet(templateParams);
            /* 下发手机号码，采用 e.164 标准，+[国家或地区码][手机号]
             * 例如+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号*/
            phone = "+86" + phone;
            String[] phoneNumbers = {phone};
            req.setPhoneNumberSet(phoneNumbers);
            /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
             * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
            SendSmsResponse res = client.SendSms(req);
            // 输出 JSON 格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(res));
            // 可以取出单个值，您可以通过官网接口文档或跳转到 response 对象的定义处查看返回字段的定义
            System.out.println(res.getRequestId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    public static void main(String[] args) {
        SmsComponent sc=new SmsComponent();
        String code = SmsComponent.randomCode();
        sc.sendSmsCode("",code);
        System.out.println(code+"验证码发送成功！！！");
    }
}
