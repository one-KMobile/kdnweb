package kdn.cmm.util;

import net.sf.json.JSONSerializer; 

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
 
/**
 * @version <tt>Revision: 1.0</tt> 2013. 8. 22.  
 * @author <a href="mailto:hejin@nkia.co.kr">hejin</a>
 */
public class JSONResponseUtil{
     
    /**
     * JSON View 화면 처리를 위해 JSON변환 후 ResponseEntity로 반환.
     * @param obj
     * @return
     */
    public static ResponseEntity<String> getJSONResponse(Object obj){
    	//JSONSerializer jsn = new JSONSerializer();
        String json = new JSONSerializer().toJSON(obj).toString();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity(json, responseHeaders, HttpStatus.OK);
    }
     
    /**
     * JSON View 화면 처리를 위해 JSON변환 후 ResponseEntity로 반환.
     * @param obj
     * @return
     */
    public static String getJSONString(Object obj){
        return new JSONSerializer().toJSON(obj).toString();
    }
}
