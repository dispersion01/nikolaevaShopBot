package org.co.nikolaeva.aa.bot;

import javax.imageio.IIOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigBot {
    private Properties configproperties = new Properties();

    public ConfigBot(String configfile) {
        try {
            InputStream input = new FileInputStream(configfile);
            configproperties.load(input);
        } catch (IIOException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProperty(String key) {
        return configproperties.getProperty(key);
    }
}
