package com.joelkuvlo.sampleprojectperson.logs;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppLogger.class);

    public static void logInfo(String message) {
        LOGGER.info(message);
    }

    public static void logError(String message, Throwable e) {
        LOGGER.error(message, e);
    }
}
