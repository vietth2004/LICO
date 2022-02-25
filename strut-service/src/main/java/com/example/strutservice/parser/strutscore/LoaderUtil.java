package com.example.strutservice.parser.strutscore;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by cuong on 4/7/2017.
 */
public class LoaderUtil {

    /**
     * Struts Resources Finder
     * @param resourceRoot
     * @param resourceName
     * @return
     * @throws IOException
     */
    public static Iterator<URL> getResources(String resourceRoot, String resourceName) throws IOException {
        if (resourceRoot == null || resourceName == null) {
            return new ArrayList<URL>().iterator();
        }

        URL url = null;
        File resourceFile = new File(resourceRoot + File.separator + resourceName);
        if (resourceFile.exists()) {
            url = resourceFile.toURI().toURL();
        }

        // find in jcia classpath (available jars: struts-core, tiles-plugin)
        if (url == null) {
            url = LoaderUtil.class.getClassLoader().getResource(resourceName);
        }

        return (url != null) ? new ArrayList<>(Arrays.asList(url)).iterator() : new ArrayList<URL>().iterator();
    }

    /**
     * Class finder, now we haven't supported yet so it is always true
     * @param className
     * @param callingClass
     * @return
     * @throws ClassNotFoundException
     */
    public static Class loadClass(String className, Class callingClass) throws ClassNotFoundException {
        return callingClass;
    }
}
