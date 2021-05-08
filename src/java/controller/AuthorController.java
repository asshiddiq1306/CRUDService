/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.HashMap;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Asshiddiq
 */
@RestController
public class AuthorController {
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HashMap masterApps(
    ) throws Exception {
       HashMap hmInfo = new HashMap();
       hmInfo.put("Apps", "CRUDService");
       hmInfo.put("Version", "1.0");
       hmInfo.put("Author", "Asshiddiq");
       return hmInfo;
    }
}
