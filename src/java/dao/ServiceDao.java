/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONObject;

/**
 *
 * @author Asshiddiq
 */
public interface ServiceDao {

    public int CreateData(JSONObject param) throws Exception;

    public int UpdateDataWithImage(JSONObject param) throws Exception;

    public int UpdateData(JSONObject param) throws Exception;

    public int DeleteData(JSONObject param) throws Exception;

    public HashMap ReadData(String no) throws Exception;

    public List ListData() throws Exception;

    public HashMap ImageCount(String image) throws Exception;
}
