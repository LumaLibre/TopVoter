package dev.jsinco.topvoter;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;

public class LogFilter extends AbstractFilter {

    private static final String PACKAGE = "dev.jsinco.topvoter.javalin";

    @Override
    public Result filter(LogEvent event) {
        if (event == null) {
            return Result.NEUTRAL;
        }

        return validateMessage(event.getLoggerName(), event.getLevel(), event.getMessage().getFormattedMessage(), event.getThrown());
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        return validateMessage(logger.getName(), level, msg.getFormattedMessage(), t);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
        return validateMessage(logger.getName(), level, msg, null);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        return validateMessage(logger.getName(), level, msg.toString(), t);
    }


    private Result validateMessage(String loggerName, Level level, String message, Throwable throwable) {
        if (message == null || !loggerName.startsWith(PACKAGE)) {
            return Result.NEUTRAL;
        }

        return switch (level.name()) {
            case "INFO" -> Result.DENY;
            default -> Result.NEUTRAL;
        };
    }
}
