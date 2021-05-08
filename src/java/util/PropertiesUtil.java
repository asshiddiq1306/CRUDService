/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Asshiddiq
 */
public class PropertiesUtil {
    /**
     * function to get value from properties file.
     * @param keyword is key inside properties file.
     * @param filename is name of file.
     * @return value as a String. 
     */
    public String getValueProp(String keyword, String filename) {
        Properties prop = new Properties();
        String retVal="";
        String propFileName = "/config/" + filename +".properties";
        try {
            InputStream inputStream = getClass().getResourceAsStream(propFileName);
            prop.load(inputStream);
            retVal = prop.getProperty(keyword);
        } catch (IOException ex) {
            retVal= ex.getMessage();
            System.err.println(" Error Prop : " + ex.getMessage());
        }
        return retVal;
    }
}
