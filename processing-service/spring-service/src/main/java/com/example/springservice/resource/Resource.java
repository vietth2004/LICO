package com.example.springservice.resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Resource {

    public static final List<String> SPRING_ANNOTATION_QUALIFIED_NAME = new ArrayList<>(
            Arrays.asList(
                    "org.springframework.data.mongodb.repository.MongoRepository",
                    "org.springframework.web.bind.annotation.RestController",
                    "org.springframework.data.repository.Repository",
                    "org.springframework.stereotype.Controller",
                    "org.springframework.stereotype.Service",
                    "org.springframework.stereotype.Repository",
                    "org.springframework.stereotype.Component",
                    "org.springframework.context.annotation.Configuration",
                    "org.springframework.context.annotation.Bean",
                    "org.springframework.boot.autoconfigure.SpringBootApplication",
                    "org.springframework.context.ApplicationContext"));

    public static final List<String> SPRING_CONTROLLER_ANNOTATION_QUALIFIED_NAME = new ArrayList<>(
            Arrays.asList(
                    "org.springframework.web.bind.annotation.RequestMapping",
                    "org.springframework.web.bind.annotation.GetMapping",
                    "org.springframework.web.bind.annotation.PostMapping",
                    "org.springframework.web.bind.annotation.DeleteMapping",
                    "org.springframework.web.bind.annotation.PatchMapping",
                    "org.springframework.web.bind.annotation.PutMapping"));

    public static final List<String> SPRING_REPOSITORY_INTERFACE_QUALIFIED_NAME = new ArrayList<>(
            Arrays.asList(
                    "org.springframework.data.repository.Repository",
                    "org.springframework.data.jpa.repository.JpaRepository",
                    "org.springframework.data.repository.CrudRepository",
                    "org.springframework.data.repository.PagingAndSortingRepository",
                    "org.springframework.data.mongodb.repository.MongoRepository"
            ));

    public static final List<String> SPRING_MVC_SERVICE_QUALIFIED_NAME = new ArrayList<>(
            Arrays.asList(
                    "org.springframework.stereotype.Service"
            ));

    public static final List<String> SPRING_CONTROLLER_QUALIFIED_NAME = Collections.singletonList("org.springframework.stereotype.Controller");

    public static final List<String> SPRING_MVC_REPOSITORY_QUALIFIED_NAME = Collections.singletonList("org.springframework.stereotype.Repository");

    public static final List<String> SPRING_MVC_CONTROLLER_QUALIFIED_NAME = new ArrayList<>(
            Arrays.asList(
                    "org.springframework.web.bind.annotation.RestController",
                    "org.springframework.stereotype.Controller"
            ));

    public static final List<String> SPRING_INITIALIZR_QUALIFIED_NAME = new ArrayList<>(
            Arrays.asList(
                    "org.springframework.web.servlet.support.AbstractDispatcherServletInitializer",
                    "org.springframework.web.context.AbstractContextLoaderInitializer",
                    "org.springframework.web.WebApplicationInitializer"
            ));

    public static final List<String> SPRING_JUNIT_TEST_QUALIFIED_NAME = new ArrayList<>(
            Arrays.asList(
                    "org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig"
            ));

    /**
     * Spring Security Annotation
     **/
    public static final List<String> SPRING_SECURITY_ANNOTATION_QUALIFIED_NAME = new ArrayList<>(
            Arrays.asList(
                    "org.springframework.context.annotation.Configuration",
                    "org.springframework.security.config.annotation.web.configuration.EnableWebSecurity",
                    "org.springframework.social.config.annotation.EnableSocial",
                    "org.springframework.context.annotation.PropertySource",
                    "org.springframework.security.access.prepost.PreAuthorize",
                    "org.springframework.web.bind.annotation.CrossOrigin",
                    "org.springframework.security.access.prepost.PostAuthorize",
                    "org.springframework.security.access.annotation",
                    "org.springframework.social.config.annotation.SocialConfigurer",
                    "org.springframework.social.google.connect.GoogleConnectionFactory",
                    "org.springframework.social.google.connect.FacebookConnectionFactory",
                    "org.springframework.social.google.connect.GenericOAuth2ConnectionFactory",
                    "org.springframework.social.connect.support.OAuth2ConnectionFactory",
                    "org.springframework.social.connect.support.OAuth1ConnectionFactory",
                    "org.springframework.social.oauth1.GenericOAuth1ConnectionFactory",
                    "org.springframework.security.access.prepost.PreAuthorize",
                    "org.springframework.security.access.prepost.PostAuthorize",
                    "org.springframework.security.access.annotation",
                    "org.springframework.web.bind.annotation.CrossOrigin",
                    "org.springframework.social.connect.ConnectionSignUp",
                    "org.springframework.social.security.SocialUserDetails",
                    "org.springframework.security.core.userdetails.User",
                    "org.springframework.social.security.SocialUserDetails",
                    "org.springframework.security.provisioning.MutableUserDetails",
                    "org.springframework.security.ldap.userdetails.LdapUserDetails",
                    "org.springframework.security.core.CredentialsContainer",
                    "org.springframework.social.security.SocialUserDetails",
                    "org.springframework.security.core.Authentication"
            ));

    public static final List<String> SPRING_SECURITY_OAUTH2_CONFIG_ANNOTATION = new ArrayList<>(
            Arrays.asList(
                    "org.springframework.social.google.connect.GoogleConnectionFactory",
                    "org.springframework.social.google.connect.FacebookConnectionFactory",
                    "org.springframework.social.google.connect.GenericOAuth2ConnectionFactory",
                    "org.springframework.social.connect.support.OAuth2ConnectionFactory"
            )
    );

    public static final List<String> SPRING_SECURITY_OAUTH1_CONFIG_ANNOTATION = new ArrayList<>(
            Arrays.asList(
                    "org.springframework.social.connect.support.OAuth1ConnectionFactory",
                    "org.springframework.social.oauth1.GenericOAuth1ConnectionFactory"
            )
    );

    public static final List<String> SPRING_SECURITY_ROLE_CONFIG_ANNOTATIONS = new ArrayList<>(
            Arrays.asList(
                    "org.springframework.security.access.prepost.PreAuthorize",
                    "org.springframework.security.access.prepost.PostAuthorize",
                    "org.springframework.security.access.annotation"
            )
    );

    public static final List<String> SPRING_SECURITY_CORS_CONFIG = new ArrayList<>(
            Arrays.asList(
                    "org.springframework.web.bind.annotation.CrossOrigin"
            )
    );

    public static final List<String> SPRING_ANNOTATION_SIMPLE_NAME = new ArrayList<>(
            Arrays.asList(
                    ".MongoRepository",
                    ".RestController",
                    ".Repository",
                    ".Controller",
                    ".Service",
                    ".Repository",
                    ".Component",
                    ".Configuration",
                    ".Bean",
                    ".SpringBootApplication",
                    ".ApplicationContext"));

    public static final List<String> SPRING_REPOSITORY_INTERFACE_SIMPLE_NAME = new ArrayList<>(
            Arrays.asList(
                    "Repository",
                    "JpaRepository",
                    "CrudRepository",
                    "PagingAndSortingRepository",
                    ".repository.MongoRepository"
            ));

    public static final List<String> SPRING_MVC_CONTROLLER_SIMPLE_NAME = new ArrayList<>(
            Arrays.asList(
                    ".RestController",
                    ".Controller"
            ));

    public static final List<String> SPRING_MVC_SERVICE_SIMPLE_NAME = new ArrayList<>(
            Arrays.asList(
                    ".Service"
            ));

    public static final List<String> SPRING_MVC_REPOSITORY_SIMPLE_NAME = new ArrayList<>(
            Arrays.asList(
                    ".Repository"
            ));

    public static final List<String> SPRING_CONFIGURATION = new ArrayList<>(
            Arrays.asList(
                    "xmlns:context: http://www.springframework.org/schema/context",
                    "xmlns:tx: http://www.springframework.org/schema/tx",
                    "xmlns: http://www.springframework.org/schema/beans",
                    "xmlns:p: http://www.springframework.org/schema/p",
                    "xmlns:jpa: http://www.springframework.org/schema/data/jpa",
                    "xmlns:loc: http://struts.apache.org/xwork/location",
                    "xsi:schemaLocation: http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd   http://www.springframework.org/schema/data/jpa http://www.springframework.org/schema/data/jpa/spring-jpa.xsd   http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd   http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd",
                    "xmlns:xsi: http://www.w3.org/2001/XMLSchema-instance"
            )
    );
}
