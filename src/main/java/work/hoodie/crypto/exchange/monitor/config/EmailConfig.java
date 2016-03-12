package work.hoodie.crypto.exchange.monitor.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    @Value("${${spring.profiles.active:local}.mail.defaultEncoding}")
    private String mailDefaultEncoding;
    @Value("${${spring.profiles.active:local}.mail.host}")
    private String mailHost;
    @Value("${${spring.profiles.active:local}.mail.port}")
    private int mailPort;
    @Value("${${spring.profiles.active:local}.mail.username}")
    private String mailUsername;
    @Value("${${spring.profiles.active:local}.mail.password}")
    private String mailPassword;
    @Value("${${spring.profiles.active:local}.mail.debug}")
    private String mailDebug;
    @Value("${${spring.profiles.active:local}.mail.smtp.auth}")
    private String mailSmtpAuth;
    @Value("${${spring.profiles.active:local}.mail.smtp.socketFactory.class}")
    private String mailSmtpSocketFactoryClass;
    @Value("${${spring.profiles.active:local}.mail.smtp.socketFactory.fallback}")
    private String mailSmtpSocketFactoryFallback;
    @Value("${${spring.profiles.active:local}.mail.smtp.ssl}")
    private String mailSmtpSsl;


    @Bean
    public JavaMailSender javaMailSender() {

        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();

        javaMailSenderImpl.setDefaultEncoding(mailDefaultEncoding);
        javaMailSenderImpl.setHost(mailHost);
        javaMailSenderImpl.setPort(mailPort);
        javaMailSenderImpl.setUsername(mailUsername);
        javaMailSenderImpl.setPassword(mailPassword);

        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.debug", StringUtils.defaultString(mailDebug));
        javaMailProperties.put("mail.smtp.auth", StringUtils.defaultString(mailSmtpAuth));
        javaMailProperties.put("mail.smtp.socketFactory.class", StringUtils.defaultString(mailSmtpSocketFactoryClass));
        javaMailProperties.put("mail.smtp.socketFactory.fallback", StringUtils.defaultString(mailSmtpSocketFactoryFallback));
        javaMailProperties.put("mail.smtp.ssl", StringUtils.defaultString(mailSmtpSsl));

        javaMailSenderImpl.setJavaMailProperties(javaMailProperties);

        return javaMailSenderImpl;
    }
}
