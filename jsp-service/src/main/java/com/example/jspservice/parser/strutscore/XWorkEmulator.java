package com.example.jspservice.parser.strutscore;

import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.providers.XWorkConfigurationProvider;
import com.opensymphony.xwork2.inject.Container;

/**
 * XWork Emulator
 * Created by cuong on 4/6/2017.
 */
public class XWorkEmulator {

    protected ConfigurationManager configurationManager;
    protected Configuration configuration;
    protected Container container;

    protected String classPath;

    public XWorkEmulator(String classPath) {
        this.classPath = classPath;
        configurationManager = new ConfigurationManager();
        configurationManager.addContainerProvider(new XWorkConfigurationProvider());
        configuration = configurationManager.getConfiguration();
    }

}
