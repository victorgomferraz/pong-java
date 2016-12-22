package br.com.ufg.ap2.pingpong.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Properties {
    private static Properties instance = null;
    private java.util.Properties prop = null;

    public static Properties getInstance() {
        instance  = (instance!=null ? instance : new Properties());
        return instance;
    }

    private Properties() {
        java.util.Properties props = new java.util.Properties();
        FileInputStream file = null;
        try {
            file = new FileInputStream(
                    "app.properties");
            props.load(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        prop = props;
    }
    public java.util.Properties getProp(){
        return prop;
    }
}
