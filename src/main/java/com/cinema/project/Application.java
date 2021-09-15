package com.cinema.project;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.ServletException;
import java.io.File;
import java.util.logging.Logger;

public class Application {

    private static final Logger logger = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) throws ServletException, LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.addWebapp("/app", new File("webapp").getAbsolutePath());
        tomcat.start();
        logger.info("start application --> " + tomcat);
        tomcat.getServer().await();
    }
}
