package com.nerdery.snafoo.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Convenience interface providing a default logger.
 */
public interface Logging {
    default Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
