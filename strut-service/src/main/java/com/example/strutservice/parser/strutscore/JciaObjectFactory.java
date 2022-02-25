package com.example.strutservice.parser.strutscore;

import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.UnknownHandler;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.InterceptorConfig;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.conversion.TypeConverter;
import com.opensymphony.xwork2.factory.*;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.opensymphony.xwork2.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 * Created by jcia on 07/04/2017.
 */
public class JciaObjectFactory extends ObjectFactory {

    private static final Logger logger = LogManager.getLogger(JciaObjectFactory.class);

    private ObjectFactory objectFactory;

    public JciaObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    /**
     * Utility method to obtain the class matched to className. Caches look ups so that subsequent
     * lookups will be faster.
     *
     * @param className The fully qualified name of the class to return
     * @return The class itself
     * @throws ClassNotFoundException if class not found in classpath
     */
    public Class getClassInstance(String className) throws ClassNotFoundException {
        return LoaderUtil.loadClass(className, this.getClass());
    }

    @Override
    public void setClassLoader(ClassLoader cl) {
        objectFactory.setClassLoader(cl);
    }

    @Override
    public void setContainer(Container container) {
        objectFactory.setContainer(container);
    }

    @Override
    public void setActionFactory(ActionFactory actionFactory) {
        objectFactory.setActionFactory(actionFactory);
    }

    @Override
    public void setResultFactory(ResultFactory resultFactory) {
        objectFactory.setResultFactory(resultFactory);
    }

    @Override
    public void setInterceptorFactory(InterceptorFactory interceptorFactory) {
        objectFactory.setInterceptorFactory(interceptorFactory);
    }

    @Override
    public void setValidatorFactory(ValidatorFactory validatorFactory) {
        objectFactory.setValidatorFactory(validatorFactory);
    }

    @Override
    public void setConverterFactory(ConverterFactory converterFactory) {
        objectFactory.setConverterFactory(converterFactory);
    }

    @Override
    public void setUnknownHandlerFactory(UnknownHandlerFactory unknownHandlerFactory) {
        objectFactory.setUnknownHandlerFactory(unknownHandlerFactory);
    }

    @Override
    public boolean isNoArgConstructorRequired() {
        return objectFactory.isNoArgConstructorRequired();
    }

    @Override
    public Object buildAction(String actionName, String namespace, ActionConfig config, Map<String, Object> extraContext) throws Exception {
        return objectFactory.buildAction(actionName, namespace, config, extraContext);
    }

    @Override
    public Object buildBean(Class clazz, Map<String, Object> extraContext) throws Exception {
        return objectFactory.buildBean(clazz, extraContext);
    }

    @Override
    public Object buildBean(String className, Map<String, Object> extraContext) throws Exception {
        return objectFactory.buildBean(className, extraContext);
    }

    @Override
    public Object buildBean(String className, Map<String, Object> extraContext, boolean injectInternal) throws Exception {
        return objectFactory.buildBean(className, extraContext, injectInternal);
    }

    @Override
    public Interceptor buildInterceptor(InterceptorConfig interceptorConfig, Map<String, String> interceptorRefParams) throws ConfigurationException {
        return objectFactory.buildInterceptor(interceptorConfig, interceptorRefParams);
    }

    @Override
    public Result buildResult(ResultConfig resultConfig, Map<String, Object> extraContext) throws Exception {
        return objectFactory.buildResult(resultConfig, extraContext);
    }

    @Override
    public Validator buildValidator(String className, Map<String, Object> params, Map<String, Object> extraContext) throws Exception {
        return objectFactory.buildValidator(className, params, extraContext);
    }

    @Override
    public TypeConverter buildConverter(Class<? extends TypeConverter> converterClass, Map<String, Object> extraContext) throws Exception {
        return objectFactory.buildConverter(converterClass, extraContext);
    }

    @Override
    public UnknownHandler buildUnknownHandler(String unknownHandlerName, Map<String, Object> extraContext) throws Exception {
        return objectFactory.buildUnknownHandler(unknownHandlerName, extraContext);
    }
    
    
}
