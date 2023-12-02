package com.example.jspservice.parser.strutscore;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.DispatcherErrorHandler;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuong on 4/6/2017.
 */
public class StrutsEmulator extends XWorkEmulator {

    private static final Logger logger = LogManager.getLogger(StrutsEmulator.class);

    protected Dispatcher dispatcher;

    /**
     * Sets up the configuration settings, XWork configuration, and
     * message resources
     */
    public StrutsEmulator(String classPath) {
        super(classPath);
        dispatcher = initDispatcher();
    }


    protected Dispatcher initDispatcher() {
        Dispatcher du = initDispatcher(null, null);
        configurationManager = du.getConfigurationManager();
        configuration = configurationManager.getConfiguration();
        container = configuration.getContainer();
        return du;
    }

    protected Dispatcher initDispatcher(ServletContext ctx, Map<String, String> params) {
        if (params == null) {
            params = new HashMap<>();
        }
        Dispatcher du = new DispatcherWrapper(ctx, params, classPath);
        du.init();
        JciaDispatcher.setInstance(du);

        return du;
    }

    protected static class DispatcherWrapper extends JciaDispatcher {

        public DispatcherWrapper(ServletContext ctx, Map<String, String> params, String classPath) {
            super(ctx, params, classPath);
            super.setDispatcherErrorHandler(new MockErrorHandler());
        }

        @Override
        public void setDispatcherErrorHandler(DispatcherErrorHandler errorHandler) {
            // ignore
        }
    }

    protected static class MockErrorHandler implements DispatcherErrorHandler {
        public void init(ServletContext ctx) {
            // ignore
        }

        public void handleError(HttpServletRequest request, HttpServletResponse response, int code, Exception e) {
            System.out.println("Dispatcher#sendError: " + code);
            e.printStackTrace(System.out);
        }
    }
}
