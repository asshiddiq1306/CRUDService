/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import daoImp.ServiceDaoImp;
import java.util.HashMap;
import java.util.List;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import util.PropertiesUtil;

/**
 *
 * @author Asshiddiq
 */
@RestController
public class CRUDController {

    @Autowired
    ServiceDaoImp serviceDaoImp;

    PropertiesUtil pc = new PropertiesUtil();

    @RequestMapping(value = "/CreateData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public int CreateData(@RequestBody JSONObject param) throws Exception {
        HashMap mapObj = serviceDaoImp.ImageCount(param.get("IMAGE").toString().trim());
        int countImg = Integer.parseInt(mapObj.get("jumlahdata").toString().trim());
        if (countImg > 0) {
            return -77;
        }
        return serviceDaoImp.CreateData(param);
    }

    @RequestMapping(value = "/ListData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List ListData() throws Exception {
        return serviceDaoImp.ListData();
    }

    @RequestMapping(value = "/ReadData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap ReadData(
            @RequestParam(value = "no") String no
    ) throws Exception {
        return serviceDaoImp.ReadData(no);
    }

    @RequestMapping(value = "/UpdateDataWithImage", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public int UpdateDataWithImage(@RequestBody JSONObject param) throws Exception {
        HashMap mapObj = serviceDaoImp.ImageCount(param.get("IMAGE").toString().trim());
        int countImg = Integer.parseInt(mapObj.get("jumlahdata").toString().trim());
        if (countImg > 0) {
            return -77;
        }

        return serviceDaoImp.UpdateDataWithImage(param);
    }

    @RequestMapping(value = "/UpdateData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public int UpdateData(@RequestBody JSONObject param) throws Exception {
        return serviceDaoImp.UpdateData(param);
    }

    @RequestMapping(value = "/DeleteData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public int DeleteData(@RequestBody JSONObject param) throws Exception {
        return serviceDaoImp.DeleteData(param);
    }
}
