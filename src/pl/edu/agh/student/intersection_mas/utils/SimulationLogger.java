package pl.edu.agh.student.intersection_mas.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Created by maciek on 16.06.16.
 */
public class SimulationLogger {
    private Logger logger;

    public SimulationLogger(String name) {
        FileHandler fh;
        String filename = name + "_" + new SimpleDateFormat("yyyyMMddhhmm'.log'").format(new Date());
        logger = Logger.getLogger(name);

        try {
            fh = new FileHandler(filename);
            logger.addHandler(fh);
            fh.setFormatter(new SimulationLogFormatter());
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.setUseParentHandlers(false);
    }

    public void info(String message) {
        this.logger.info(message);
    }
}

class SimulationLogFormatter extends Formatter {

    @Override
    public String format(LogRecord record) {
        return record.getMessage() + "\n";
    }
}
