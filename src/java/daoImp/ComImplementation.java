/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoImp;

import util.HibernateUtilSQLServer;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import util.PropertiesUtil;

/**
 *
 * @author Asshiddiq
 */
@Component
public class ComImplementation {

    PropertiesUtil pu = new PropertiesUtil();

    public void createLogSyntaxSQL(String strSQL) {
        String logDirektori = createDirectoryFile("logsqlx", null);
        String logFilename = logDirektori + "SQL-" + new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()) + ".txt";
        try {
            Writer writer;
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFilename, true), "UTF-8"));
            writer.append(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + " : " + strSQL);
            writer.append(System.lineSeparator());
            writer.close();
        } catch (IOException ex) {
        }
    }

    public void createLogErrorSQL(String strError) {
        String logDirektori = createDirectoryFile("logerrorx", null);
        File folder = new File(logDirektori);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length - Integer.parseInt(pu.getValueProp("jmlhdir", "ServiceLOG")); i++) {
            listOfFiles[i].delete();
        }
        String logFilename = logDirektori + "SQL-" + new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()) + ".txt";
        try {
            Writer writer;
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logFilename, true), "UTF-8"));
            writer.append(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + " : " + strError);
            writer.append(System.lineSeparator());
            writer.close();
        } catch (IOException ex) {
        }
    }

    private String createDirectoryFile(String dirName, String dirAdd) {
        String strDirektori = pu.getValueProp("hddrive", "ServiceLOG") + pu.getValueProp("maindir", "ServiceLOG");
        File dirFirst = new File(strDirektori);
        if (!dirFirst.exists()) {
            dirFirst.mkdir();
        }
        if (dirAdd == null) {
            strDirektori = strDirektori + pu.getValueProp(dirName, "ServiceLOG");
            File dirSecond = new File(strDirektori);
            if (!dirSecond.exists()) {
                dirSecond.mkdir();
            }
        } else {
            strDirektori = strDirektori + pu.getValueProp(dirName, "ServiceLOG") + dirAdd;
            File dirThird = new File(strDirektori);
            if (!dirThird.exists()) {
                dirThird.mkdirs();
            }
        }
        return strDirektori;
    }

    public Session startQueue() {
        Session session = HibernateUtilSQLServer.getSessionFactory().openSession();
        session.beginTransaction();
        return session;
    }

    public void commitQueue(Session session) {
        session.getTransaction().commit();
        session.clear();
        session.close();
    }

    public void clearQueue(Session session) {
        session.clear();
        session.close();
    }
}
