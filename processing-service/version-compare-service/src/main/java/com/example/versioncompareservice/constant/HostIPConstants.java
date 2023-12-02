package com.example.versioncompareservice.constant;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.versioncompareservice.constant.HostConstant.API_GATEWAY;
import static com.example.versioncompareservice.constant.HostConstant.CIA_SERVICE;
import static com.example.versioncompareservice.constant.HostConstant.FILE_SERVICE;
import static com.example.versioncompareservice.constant.HostConstant.GITHUB_SERVICE;
import static com.example.versioncompareservice.constant.HostConstant.JAVA_SERVICE;
import static com.example.versioncompareservice.constant.HostConstant.JSF_SERVICE;
import static com.example.versioncompareservice.constant.HostConstant.PARSER_SERVICE;
import static com.example.versioncompareservice.constant.HostConstant.PROJECT_SERVICE;
import static com.example.versioncompareservice.constant.HostConstant.SPRING_SERVICE;
import static com.example.versioncompareservice.constant.HostConstant.STRUT_SERVICE;
import static com.example.versioncompareservice.constant.HostConstant.USER_SERVICE;
import static com.example.versioncompareservice.constant.HostConstant.UTEST_SERVICE;
import static com.example.versioncompareservice.constant.HostConstant.UTILITY_SERVICE;
import static com.example.versioncompareservice.constant.HostConstant.VERSION_COMPARE_SERVICE;

@Component
public class HostIPConstants {

    @Autowired
    private EurekaClient eurekaClient;

    public String getApiGatewayIp() {
        return eurekaClient.getApplication(API_GATEWAY).getInstances().get(0).getIPAddr();
    }

    public String getCiaServiceIp() {
        return eurekaClient.getApplication(CIA_SERVICE).getInstances().get(0).getIPAddr();
    }

    public String getFileServiceIp() {
        return eurekaClient.getApplication(FILE_SERVICE).getInstances().get(0).getIPAddr();
    }

    public String getJavaServiceIp() {
        return eurekaClient.getApplication(JAVA_SERVICE).getInstances().get(0).getIPAddr();
    }

    public String getParserServiceIp() {
        return eurekaClient.getApplication(PARSER_SERVICE).getInstances().get(0).getIPAddr();
    }

    public String getProjectServiceIp() {
        return eurekaClient.getApplication(PROJECT_SERVICE).getInstances().get(0).getIPAddr();
    }

    public String getSpringServiceIp() {
        return eurekaClient.getApplication(SPRING_SERVICE).getInstances().get(0).getIPAddr();
    }

    public String getUserServiceIp() {
        return eurekaClient.getApplication(USER_SERVICE).getInstances().get(0).getIPAddr();
    }

    public String getUtilityServiceIp() {
        return eurekaClient.getApplication(UTILITY_SERVICE).getInstances().get(0).getIPAddr();
    }

    public String getVersionCompareServiceIp() {
        return eurekaClient.getApplication(VERSION_COMPARE_SERVICE).getInstances().get(0).getIPAddr();
    }

    public String getXmlServiceIp() {
        return eurekaClient.getApplication(JSF_SERVICE).getInstances().get(0).getIPAddr();
    }

    public String getGithubServiceIp() {
        return eurekaClient.getApplication(GITHUB_SERVICE).getInstances().get(0).getIPAddr();
    }

    public String getStrutServiceIp() {
        return eurekaClient.getApplication(STRUT_SERVICE).getInstances().get(0).getIPAddr();
    }

    public String getUnitTestingIP() {
        return eurekaClient.getApplication(UTEST_SERVICE).getInstances().get(0).getIPAddr();
    }
}
