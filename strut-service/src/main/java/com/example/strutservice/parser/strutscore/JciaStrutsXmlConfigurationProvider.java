package com.example.strutservice.parser.strutscore;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.inject.ContainerBuilder;
import com.opensymphony.xwork2.inject.Context;
import com.opensymphony.xwork2.inject.Factory;
import com.opensymphony.xwork2.util.location.LocatableProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by cuong on 4/7/2017.
 */
public class JciaStrutsXmlConfigurationProvider extends JciaXmlConfigurationProvider {

    private static final Logger logger = LogManager.getLogger(JciaStrutsXmlConfigurationProvider.class);

    private File baseDir = null;
    private String filename;
    private String reloadKey;
    private ServletContext servletContext;

    /**
     * Constructs the configuration provider
     *
     * @param filename The filename to look for
     * @param errorIfMissing If we should throw an exception if the file can't be found
     * @param ctx Our ServletContext
     */
    public JciaStrutsXmlConfigurationProvider(String filename, boolean errorIfMissing, ServletContext ctx, String classPath) {
        super(filename, errorIfMissing, classPath);
        this.servletContext = ctx;
        this.filename = filename;
        reloadKey = "configurationReload-"+filename;
        Map<String,String> dtdMappings = new HashMap<String,String>(getDtdMappings());
        dtdMappings.put("-//Apache Software Foundation//DTD Struts Configuration 2.0//EN", "struts-2.0.dtd");
        dtdMappings.put("-//Apache Software Foundation//DTD Struts Configuration 2.1//EN", "struts-2.1.dtd");
        dtdMappings.put("-//Apache Software Foundation//DTD Struts Configuration 2.1.7//EN", "struts-2.1.7.dtd");
        dtdMappings.put("-//Apache Software Foundation//DTD Struts Configuration 2.3//EN", "struts-2.3.dtd");
        dtdMappings.put("-//Apache Software Foundation//DTD Struts Configuration 2.5//EN", "struts-2.5.dtd");
        setDtdMappings(dtdMappings);
        baseDir = new File(classPath);
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.config.providers.XmlConfigurationProvider#register(com.opensymphony.xwork2.inject.ContainerBuilder, java.util.Properties)
     */
    @Override
    public void register(ContainerBuilder containerBuilder, LocatableProperties props) throws ConfigurationException {
        if (servletContext != null && !containerBuilder.contains(ServletContext.class)) {
            containerBuilder.factory(ServletContext.class, new Factory<ServletContext>() {
                public ServletContext create(Context context) throws Exception {
                    return servletContext;
                }
            });
        }
        super.register(containerBuilder, props);
    }

    /* (non-Javadoc)
     * @see com.opensymphony.xwork2.config.providers.XmlConfigurationProvider#init(com.opensymphony.xwork2.config.Configuration)
     */
    @Override
    public void loadPackages() {
        ActionContext ctx = ActionContext.getContext();
        ctx.put(reloadKey, Boolean.TRUE);
        try {
            super.loadPackages();
        } catch (ConfigurationException e) {
            logger.error("Error when build struts configuration");
            e.printStackTrace();
        }
    }

    /**
     * Look for the configuration file on the classpath and in the file system
     *
     * @param fileName The file name to retrieve
     * @see com.opensymphony.xwork2.config.providers.XmlConfigurationProvider#getConfigurationUrls
     */
    @Override
    protected Iterator<URL> getConfigurationUrls(String fileName) throws IOException {
        URL url = null;
        if (baseDir != null) {
            url = findInFileSystem(fileName);
            if (url == null) {
                Iterator<URL> urls1 = super.getConfigurationUrls(fileName);
                return urls1;
            }
        }
        if (url != null) {
            List<URL> list = new ArrayList<>();
            list.add(url);
            return list.iterator();
        } else {
            Iterator<URL> urls1 = super.getConfigurationUrls(fileName);
            return urls1;
        }
    }

    protected URL findInFileSystem(String fileName) throws IOException {
        URL url = null;
        File file = new File(fileName);

        // Trying relative path to original file
        if (!file.exists()) {
            file = new File(baseDir, fileName);
        }
        if (file.exists()) {
            try {
                url = file.toURI().toURL();
            } catch (MalformedURLException e) {
                throw new IOException("Unable to convert "+file+" to a URL");
            }
        }
        return url;
    }

    /**
     * Overrides needs reload to ensure it is only checked once per request
     */
    @Override
    public boolean needsReload() {
        ActionContext ctx = ActionContext.getContext();
        if (ctx != null) {
            return ctx.get(reloadKey) == null && super.needsReload();
        } else {
            return super.needsReload();
        }

    }

    public String toString() {
        return ("Struts XML configuration provider ("+filename+")");
    }
}
