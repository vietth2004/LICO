package com.example.jspservice.parser.strutscore;

import com.opensymphony.xwork2.config.RuntimeConfiguration;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.PackageConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Created by cuong on 4/7/2017.
 */
public class JciaStrutsEmulator extends StrutsEmulator {

    private static final Logger logger = LogManager.getLogger(JciaStrutsEmulator.class);

    private RuntimeConfiguration runtimeConfiguration;

    public JciaStrutsEmulator(String classPath) {
        super(classPath);
        runtimeConfiguration = configurationManager.getConfiguration().getRuntimeConfiguration();
    }

    public Map<String, Map<String, ActionConfig>> getActionConfigs() {
        return runtimeConfiguration.getActionConfigs();
    }

    public Map<String, PackageConfig> getPackageConfigs() {
        return configuration.getPackageConfigs();
    }
}
