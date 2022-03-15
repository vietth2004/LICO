package com.example.strutsservice.constant;

/**
 * Created by cuong on 3/25/2017.
 */
public class StrutsClassType {

    /**
     * Struts filters
     */
    public static final String STRUTS_PREPARE_AND_EXECUTE_FILTER
            = "org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter";
    public static final String STRUTS_PREPARE_AND_EXECUTE_FILTER_NG
            = "org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter";
    public static final String STRUTS_FILTER_DISPATCHER
            = "org.apache.struts2.dispatcher.FilterDispatcher";


    /**
     * Tiles Plugin Listener
     */
    public static final String STRUTS_TILES_LISTENER = "org.apache.struts2.tiles.StrutsTilesListener";

    /**
     * Default action
     */
    public static final String STRUTS_ACTION_SUPPORT = "com.opensymphony.xwork2.ActionSupport";
    public static final String DEFAULT_ACTION_SUPPORT = "org.apache.struts2.dispatcher.DefaultActionSupport";

    /**
     * Result Type
     */
    public static final String STRUTS_RESULT_SUPPORT = "org.apache.struts2.result.StrutsResultSupport";

    /**
     * Result types from plugin
     */
    public static final String TITLES_RESULT_TYPE = "org.apache.struts2.views.tiles.TilesResult";

    /**
     * 11 default result types
     */
    public static final String ACTION_CHAIN_RESULT_TYPE = "com.opensymphony.xwork2.ActionChainResult";
    public static final String SERVLET_DISPATCHER_RESULT_TYPE = "org.apache.struts2.result.ServletDispatcherResult";
    public static final String SERVLET_ACTION_REDIRECT_RESULT_TYPE = "org.apache.struts2.result.ServletActionRedirectResult";
    public static final String HTTP_HEADER_RESULT_TYPE = "org.apache.struts2.result.HttpHeaderResult";
    public static final String STREAM_RESULT_TYPE = "org.apache.struts2.result.StreamResult";
    public static final String XSLT_RESULT_TYPE = "org.apache.struts2.views.xslt.XSLTResult";
    public static final String FREE_MARKER_RESULT_TYPE = "org.apache.struts2.views.freemarker.FreemarkerResult";
    public static final String SERVLET_REDIRECT_RESULT_TYPE = "org.apache.struts2.result.ServletRedirectResult";
    public static final String VELOCITY_RESULT_TYPE = "org.apache.struts2.result.VelocityResult";
    public static final String PLAIN_TEXT_RESULT_TYPE = "org.apache.struts2.result.PlainTextResult";
    public static final String POST_BACK_RESULT_TYPE = "org.apache.struts2.result.PostbackResult";


}
