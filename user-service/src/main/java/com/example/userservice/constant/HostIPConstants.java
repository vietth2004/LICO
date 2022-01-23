package com.example.userservice.constant;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.userservice.constant.HostConstant.*;

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
        return eurekaClient.getApplication(XML_SERVICE).getInstances().get(0).getIPAddr();
    }

    public String getGithubServiceIp() {
        return eurekaClient.getApplication(GITHUB_SERVICE).getInstances().get(0).getIPAddr();
    }
}
