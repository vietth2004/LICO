package com.example.strutservice.parser.strutscore;

import com.opensymphony.xwork2.config.providers.XmlConfigurationProvider;
import org.apache.struts2.dispatcher.Dispatcher;

import javax.servlet.ServletContext;
import java.util.Map;

/**
 * Created by cuong on 4/7/2017.
 */
public class JciaDispatcher extends Dispatcher {

    protected String classPath;

    /**
     * Create the Dispatcher instance for a given ServletContext and set of initialization parameters.
     *
     * @param servletContext Our servlet context
     * @param initParams     The set of initialization parameters
     */
    public JciaDispatcher(ServletContext servletContext, Map<String, String> initParams, String classPath) {
        super(servletContext, initParams);
        this.classPath = classPath;
    }

    protected XmlConfigurationProvider createStrutsXmlConfigurationProvider(String filename, boolean errorIfMissing, ServletContext ctx) {
        return new JciaStrutsXmlConfigurationProvider(filename, errorIfMissing, ctx, classPath);
    }

    @Override
    protected XmlConfigurationProvider createXmlConfigurationProvider(String filename, boolean errorIfMissing) {
        return new JciaXmlConfigurationProvider(filename, errorIfMissing, classPath);
    }
}
