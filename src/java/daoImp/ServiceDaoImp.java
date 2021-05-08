/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoImp;

import dao.ServiceDao;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Asshiddiq
 */
@Component
public class ServiceDaoImp implements ServiceDao {

    @Autowired
    ComImplementation comImp;

    public HashMap setError(String message) {
        HashMap ret = new HashMap();
        ret.put("status", "error");
        ret.put("message", message);
        return ret;
    }

    @Override
    public int CreateData(JSONObject param) throws Exception {
        Session s = comImp.startQueue();
        try {
            String user_id = param.get("USER_ID").toString().trim();
            String trx_id = param.get("TRX_ID").toString().trim();
            String image = param.get("IMAGE").toString().trim();

            String sqlSelector = "INSERT INTO CRUD.dbo.Yokke"
                    + " (USER_ID, TRX_ID, IMAGE)"
                    + " VALUES"
                    + " (:user_id, :trx_id, :image)";

            Query query = s.createSQLQuery(sqlSelector)
                    .setParameter("user_id", user_id)
                    .setParameter("trx_id", trx_id)
                    .setParameter("image", image);
            comImp.createLogSyntaxSQL(query + " Param: "
                    + " user_id = " + user_id + " trx_id = "
                    + trx_id + " image = " + image);

            int ret = query.executeUpdate();
            if (ret == 0) {
                return -99;
            }
            comImp.commitQueue(s);
            return ret;

        } catch (Exception ex) {
            ex.printStackTrace();
            comImp.clearQueue(s);
            String isSuccess = "gagal : " + ex.getMessage();
            Throwable cause = ex.getCause();
            if (cause instanceof SQLException) {
                isSuccess += ", Cause by : " + cause.getMessage();
                comImp.createLogErrorSQL(isSuccess);
            }
            return -999;
        }
    }

    @Override
    public int UpdateDataWithImage(JSONObject param) throws Exception {
        Session s = comImp.startQueue();
        try {
            String user_id = param.get("USER_ID").toString().trim();
            String trx_id = param.get("TRX_ID").toString().trim();
            String image = param.get("IMAGE").toString().trim();
            String no = param.get("NO").toString().trim();

            String sqlQuery = "UPDATE CRUD.dbo.Yokke SET USER_ID = :user_id,"
                    + " TRX_ID = :trx_id , IMAGE = :image WHERE NO = :no";
            Query query = s.createSQLQuery(sqlQuery);
            query.setParameter("image", image);
            query.setParameter("user_id", user_id);
            query.setParameter("trx_id", trx_id);
            query.setParameter("no", no);

            comImp.createLogSyntaxSQL(query + " " + "Param: user_id = " + user_id
                    + ", trx_id =  " + trx_id + ", image = " + image + ", no = " + no);

            int ret = query.executeUpdate();
            if (ret == 0) {
                return -99;
            }
            comImp.commitQueue(s);
            return ret;
        } catch (Exception ex) {
            comImp.clearQueue(s);
            String isSuccess = "gagal : " + ex.getMessage();
            Throwable cause = ex.getCause();
            if (cause instanceof SQLException) {
                isSuccess += ", Cause by : " + cause.getMessage();
                comImp.createLogErrorSQL(isSuccess);
            }
            return -999;
        }
    }

    @Override
    public int UpdateData(JSONObject param) throws Exception {
        Session s = comImp.startQueue();
        try {
            String user_id = param.get("USER_ID").toString().trim();
            String trx_id = param.get("TRX_ID").toString().trim();
            String no = param.get("NO").toString().trim();

            String sqlQuery = "UPDATE CRUD.dbo.Yokke SET USER_ID = :user_id,"
                    + " TRX_ID = :trx_id WHERE NO = :no";
            Query query = s.createSQLQuery(sqlQuery);
            query.setParameter("user_id", user_id);
            query.setParameter("trx_id", trx_id);
            query.setParameter("no", no);

            comImp.createLogSyntaxSQL(query + " " + "Param: user_id = " + user_id
                    + ", trx_id =  " + trx_id + ", no = " + no);

            int ret = query.executeUpdate();
            if (ret == 0) {
                return -99;
            }
            comImp.commitQueue(s);
            return ret;
        } catch (Exception ex) {
            comImp.clearQueue(s);
            String isSuccess = "gagal : " + ex.getMessage();
            Throwable cause = ex.getCause();
            if (cause instanceof SQLException) {
                isSuccess += ", Cause by : " + cause.getMessage();
                comImp.createLogErrorSQL(isSuccess);
            }
            return -999;
        }
    }

    @Override
    public int DeleteData(JSONObject param) throws Exception {
        Session s = comImp.startQueue();
        try {
            String no = param.get("NO").toString().trim();
            String sqlQuery = "DELETE CRUD.dbo.Yokke WHERE NO = :no";
            Query query = s.createSQLQuery(sqlQuery)
                    .setParameter("no", no);

            comImp.createLogSyntaxSQL(query + " " + "Param: " + "NO " + no);
            int ret = query.executeUpdate();
            if (ret == 0) {
                return -99;
            }
            comImp.commitQueue(s);
            return ret;
        } catch (Exception ex) {
            comImp.clearQueue(s);
            String isSuccess = "gagal " + ex.getMessage();
            Throwable cause = ex.getCause();
            if (cause instanceof SQLException) {
                isSuccess += ", Cause by : " + cause.getMessage();
                comImp.createLogErrorSQL(isSuccess);
            }
            return -999;
        }
    }

    @Override
    public HashMap ReadData(String no) throws Exception {
        HashMap ret = new HashMap();

        String sqlSelector = "SELECT * FROM CRUD.dbo.Yokke WHERE NO = :no";
        Session s = comImp.startQueue();
        Query query = s.createSQLQuery(sqlSelector)
                .setParameter("no", no);
        comImp.createLogSyntaxSQL(query + " " + "Parameter: " + "NO: " + no);
        try {
            List returnList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
            if (s.isOpen()) {
                comImp.commitQueue(s);
            }
            ret.put("data", returnList.get(0));
            ret.put("status", "success");
            ret.put("message", "success get data");
            return ret;
        } catch (IndexOutOfBoundsException boundEx) {
            return this.setError("Data tidak ditemukan");
        } catch (Exception ex) {
            comImp.clearQueue(s);
            String isSuccess = "Gagal " + ex.getMessage();
            Throwable cause = ex.getCause();
            if (cause instanceof SQLException) {
                isSuccess += ", Cause by : " + cause.getMessage();
                comImp.createLogErrorSQL(isSuccess);
            }
            comImp.clearQueue(s);
            return this.setError(isSuccess);
        }
    }

    @Override
    public HashMap ImageCount(String image) throws Exception {
        HashMap ret = new HashMap();

        String sqlSelector = "SELECT * FROM CRUD.dbo.Yokke WHERE IMAGE = :image";
        Session s = comImp.startQueue();
        Query query = s.createSQLQuery(sqlSelector)
                .setParameter("image", image);
        comImp.createLogSyntaxSQL(query + " " + "Parameter: " + "Image Name: " + image);
        try {
            List returnList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
            if (s.isOpen()) {
                comImp.commitQueue(s);
            }
            ret.put("jumlahdata", returnList.size());
            ret.put("status", "success");
            ret.put("message", "success get data");
            return ret;
        } catch (IndexOutOfBoundsException boundEx) {
            return this.setError("Data tidak ditemukan");
        } catch (Exception ex) {
            comImp.clearQueue(s);
            String isSuccess = "Gagal " + ex.getMessage();
            Throwable cause = ex.getCause();
            if (cause instanceof SQLException) {
                isSuccess += ", Cause by : " + cause.getMessage();
                comImp.createLogErrorSQL(isSuccess);
            }
            comImp.clearQueue(s);
            return this.setError(isSuccess);
        }
    }

    @Override
    public List ListData() throws Exception {
        String sqlSelector = "SELECT * FROM CRUD.dbo.Yokke";
        Session s = comImp.startQueue();
        Query query = s.createSQLQuery(sqlSelector);
        comImp.createLogSyntaxSQL(query.toString().trim());
        try {
            List returnList = query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
            if (s.isOpen()) {
                comImp.commitQueue(s);
            }
            return returnList;
        } catch (Exception ex) {
            comImp.clearQueue(s);
            String isSuccess = "Gagal " + ex.getMessage();
            Throwable cause = ex.getCause();
            if (cause instanceof SQLException) {
                isSuccess += ", Cause by : " + cause.getMessage();
                comImp.createLogErrorSQL(isSuccess);
            }
            comImp.clearQueue(s);
            throw new Exception(isSuccess);
        }
    }
}
