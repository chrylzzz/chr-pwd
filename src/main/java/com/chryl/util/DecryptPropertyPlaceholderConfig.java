//package com.chryl.util;
//
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
//import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
//import org.springframework.core.env.ConfigurablePropertyResolver;
//
//import java.util.Properties;
//
///**
// * 配置文件密码处理:配置文件解密核心类
// * Created by Chr.yl on 2020/12/7.
// *
// * @author Chr.yl
// */
//public class DecryptPropertyPlaceholderConfig extends PropertyPlaceholderConfigurer {
////public class DecryptPropertyPlaceholderConfig extends PropertySourcesPlaceholderConfigurer {
////    @Override
////    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, ConfigurablePropertyResolver propertyResolver) throws BeansException {
////        propertyResolver.getProperty()
////        super.processProperties(beanFactoryToProcess, propertyResolver);
////    }
//
//    @Override
//    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
//
//        try {
//            String pwdEncrypt = props.getProperty("spring.datasource.password");
//            String pwd = DESUtil.decrypt(pwdEncrypt, "chryl0527");
//            props.setProperty("spring.datasource.password", pwd);
//        } catch (Exception e) {
//            System.out.println("---------------" + e);
//        }
//        super.processProperties(beanFactoryToProcess, props);
//    }
//}
