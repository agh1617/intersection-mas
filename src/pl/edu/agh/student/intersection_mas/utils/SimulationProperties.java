package pl.edu.agh.student.intersection_mas.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by maciek on 17.06.16.
 */
public class SimulationProperties {
    private static final String FILE_NAME = "config.properties";
    private static SimulationProperties simulationProperties = new SimulationProperties();
    private Properties properties;

    private SimulationProperties() {
        readProperties();
    }

    private void readProperties() {
        InputStream inputStream = null;
        try {
            String filename = "logs/config_" + new SimpleDateFormat("yyyyMMddhhmmss'.log'").format(new Date());
            Files.copy(Paths.get(FILE_NAME), Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);

            this.properties = new Properties();

            inputStream = new FileInputStream(FILE_NAME);
            this.properties.load(inputStream);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static SimulationProperties getInstance() {
        return simulationProperties;
    }

    public String get(String key) {
        return (String) this.properties.get(key);
    }
}
