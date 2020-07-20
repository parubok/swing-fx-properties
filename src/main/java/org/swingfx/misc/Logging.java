package org.swingfx.misc;

import java.util.logging.Logger;

public final class Logging {

    private static final Logger logger = Logger.getLogger("org.swingfx");

    public static Logger getLogger() {
        return logger;
    }
}
