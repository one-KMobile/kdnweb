package kdn.cmm.box;

import java.util.Enumeration;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * <PRE>
 * BoxUtils
 * </PRE>
 */
public class BoxUtil
{
	/**
	 * get Box
	 * @param request
	 * @return Box
	 */
	public static BoxParam getBox(HttpServletRequest request){
		BoxParam box = new BoxParam();
		Enumeration en = request.getParameterNames();
		while(en.hasMoreElements()){
			String key  = (String)en.nextElement();
			String []value = request.getParameterValues(key);
			System.out.println("KEY : " + key + "VALUE : "+value[0]);
			if(value.length > 1){
				box.put(key,value);
			} else {
				box.put(key,value[0]);
			}
		}
		return box;
	}
	
	/**
	 * getBox
	 * @param list
	 * @param id
	 * @param value
	 * @return Box
	 */
	public static Box getBox(List list,String id,String value){
		if(list!= null && list.size() > 0 ){
		    for(int i=0;i<list.size();i++){ 
		        Box box = (Box)list.get(i);
				if( StringUtils.equals(box.getString(id),value) ){
				    return box;
				}
		    }
		}
	    return null;
	}
}