package com.joelkuvlo.sampleprojectperson;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppLogger.class);

    public static Logger getLogger() {
        return LOGGER;
    }
}
