package com.tandaima.tool.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @author zbrcel@gmail.com
 * @description 邮件通用工具类
 */
public class EmailUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailUtils.class);

    /** 开启授权码的邮箱 */
    private final String MY_EMAIL;
    /** 授权码 */
    private final String AUTHORIZATION_CODE;
    /** SMTP服务器地址 */
    private final String SMTP_EMAIL;
    /** 主题名 */
    private final String PROJECT;

    public EmailUtils() throws Exception {
        throw new Exception("not param exception");
    }

    public EmailUtils(String myEmail,String authorizationCode,String smtpEmail,String projectName) {
        this.MY_EMAIL = myEmail;
        this.AUTHORIZATION_CODE = authorizationCode;
        this.SMTP_EMAIL = smtpEmail;
        this.PROJECT = projectName;
    }


    /**
     * 发送验证码
     */
    public  void send(String toEmail,String content){
        content = getCodeHTML(toEmail,content,PROJECT);
        //邮件主题
        String subject = "【"+PROJECT+"】 验证码";
        try {
            sendEmail(toEmail,PROJECT,subject,content,toEmail);
        } catch (Exception e) {
            LOGGER.error("发送163邮件异常",e);
        }
    }

    /**
     * 发送邮件
     */
    private void sendEmail(String toEmail,String personal,String subject,String content,String toPersonal) throws Exception {
        //创建连接邮件服务器的参数配置 参数配置
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", SMTP_EMAIL);// 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");// 需要请求认证
        props.setProperty("mail.transport.protocol", "smtp");
        //根据配置创建会话对象和邮件服务器交互
        Session session = Session.getInstance(props);
        session.setDebug(false);// 设置为debug模式, 可以查看详细的发送日志
        //创建邮件
        MimeMessage message = createEmail(session, MY_EMAIL, toEmail,personal,subject,content,toPersonal);
        //使用Session获取邮件传输对象
        Transport transport = session.getTransport();
        //使用邮箱账号和密码连接邮件服务器
        transport.connect(MY_EMAIL, AUTHORIZATION_CODE);
        //发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        //关闭连接
        transport.close();
    }

    /**
     * 创建邮件
     */
    private  MimeMessage createEmail(Session session, String sendMail, String receiveMail,String personal,String subject,String content,String toPersonal) throws Exception {
        //创建一封邮件
        MimeMessage message = new MimeMessage(session);
        //发件人
        message.setFrom(new InternetAddress(sendMail, personal, "UTF-8"));
        //收件人
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, toPersonal, "UTF-8"));
        //邮件主题
        message.setSubject(subject, "UTF-8");
        //邮件正文
        message.setContent(content, "text/html;charset=UTF-8");
        //设置发件时间
        message.setSentDate(new Date());
        //保存设置
        message.saveChanges();
        return message;
    }

    private  String getCodeHTML(String email,String content,String projectName){
        return "<!DOCTYPE html>" +
                "<html><head><meta charset='UTF-8'></head><body>" +
                "<div style='width: 500px;margin: 0 auto;box-shadow: #ccc 0px 10px 30px 5px;'>" +
                "<div style='padding: 15px;background: #08acee;text-align:center;'>" +
                "<div style='text-align: left;font-size:25px;font-weight: 600;letter-spacing:-0.5px;line-height:100%;'>" + projectName + "</div>" +
                "<div style='font-size: 16px;'>" +
                "<span>本邮件是" + projectName + "发出的邮件，请勿回复.</span><br />" +
                "<span>This email is sent by " + projectName + ", please do not reply.</span>" +
                "</div>" +
                "</div>" +
                "<div class='sy-mail-content'>" +
                "<div style='text-align: center;border-bottom:2px solid #ccc;padding-bottom: 10px;'>" +
                "<span  style='font-size:25px;font-weight: 600;'>发一份临时邮件，请查收</span><br>" +
                "<span  style='font-size:16px;'>Send a temporary email, please check it.</span>" +
                "</div>" + "<div style='padding: 15px;border-bottom: 1px dashed #ccc;'>" +
                "<div style='padding-bottom: 15px;'>" +
                "您好" +
                "<span style='color:#08acee;font-weight:700;'>" +
                email +
                "</span><br>" +
                "<font>Hi <span style='color:#08acee;font-weight:700;'>" +
                email + "</span></font>" +
                "</div>" +
                "<p style='color:#08acee;font-size:18px;font-weight:700;text-align: left;'>" +
                content +
                "<p style='margin-left: 20px;font-size:12px;color:#ccc;'>为了保障您的账号的安全性，请在15分钟内完成验证</p>" +
                "</p>" +
                "</div></div></div></body></html>";
    }
}
