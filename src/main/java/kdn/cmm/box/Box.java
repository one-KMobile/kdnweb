package kdn.cmm.box;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import kdn.cmm.util.StringUtil;

import org.apache.commons.lang.StringUtils;

/**
 * <PRE>
 * Box 
 * </PRE>
 */
public class Box extends Hashtable
{
	/** SerialVersionUID */
	private static final long serialVersionUID = -3331697294570864271L;
	
	/**
	 * get String Value
	 * @param obj
	 * @return String
	 */
	private String getStringValue(Object obj){
		String reval = null;
		if(obj != null)
		{
			if(obj instanceof String){
				reval = (String)obj;
			}
			else if(obj instanceof String[]){
				String[] valarr =  (String[])obj;
				if(valarr != null && valarr.length > 0 ) reval = valarr[0]; 
			}
			else if(obj instanceof int[]){
				int [] valarr = (int[])obj;
				if(valarr != null && valarr.length > 0 ) reval = String.valueOf(valarr[0]);
			}
			else if(obj instanceof java.util.Date){
				reval = StringCoreUtils.getDateFullStr((java.util.Date)obj);
			}
			else {
				reval = obj.toString();
			}
		}else{
			reval = "";
		}
		return reval;
	}
	
	/**
	 * get Object
	 * @param id
	 * @param defaultObject
	 * @return Object
	 */
	public Object get(Object id,Object defaultObject){
	    Object obj = super.get(id);
	    if(obj == null) return defaultObject;
	    return obj;
	}
	
	/**
	 * get String
	 * @param columnName
	 * @return String
	 */
	public String getString(String id){
		Object obj = super.get(id);
		return getStringValue(obj);
	}
	
	/**
	 * get String 
	 * @param id
	 * @param defaultStr
	 * @return String
	 */
	public String getString(String id,String defaultStr){
	    Object obj = super.get(id);
	    if(obj == null){
	        return defaultStr;
	    }
	    return getStringValue(obj);
	}
	
	/**
	 * get Int
	 * @param id
	 * @return int
	 */
	public int getInt(String id){
	    return getInt(id,0);
	}
	
	/**
	 * get Int
	 * @param id
	 * @param defaultValue
	 * @return int
	 */
	public int getInt(String id,int defaultValue){
		int re = defaultValue;
		
		Object obj = super.get(id);
		if(obj == null) return re;
		if( obj instanceof Number ){
		    return ((Number)obj).intValue();
		}
		try{
			re = Integer.parseInt(getStringValue(obj));
		}catch(Exception ex){
		}
		return re;
	}
	
	/**
	 * get long
	 * @param id
	 * @return long
	 */
	public long getLong(String id){
	    return getLong(id,0);
	}
	
	/**
	 * get long
	 * @param id
	 * @param defaultValue
	 * @return long
	 */
	public long getLong(String id,long defaultValue){
		long re = defaultValue;
		
		Object obj = super.get(id);
		if(obj == null) return re;
		if( obj instanceof Number ){
		    return ((Number)obj).longValue();
		}
		try{
			re = Long.parseLong(getStringValue(obj));
		}catch(Exception ex){
		}
		return re;
	}
	
	/**
	 * get Double
	 * @param id
	 * @return double
	 */
	public double getDouble(String id){
	    double re = 0.0;
	    
		Object obj = super.get(id);
		if(obj == null) return re;
		if( obj instanceof Number ){
		    return ((Number)obj).doubleValue();
		}
		
		try{
			re = Double.parseDouble(getStringValue(obj));
		}catch(Exception ex){
		}
		return re;
	}
	
	/**
	 * get String Array
	 * @param id
	 * @return String []
	 */
	public String [] getStringArray(String id){
		Object obj = super.get(id);
		String [] reval = null;
		if(obj != null){
			if( obj instanceof String[]){
				reval = (String[])obj;
			} else {
				reval = new String[1];
				reval[0] = obj.toString();
			}
		}
		return reval;
	}
	
	/**
	 * isNotEmpty
	 * @param id
	 * @return boolean
	 */
	public boolean isNotEmpty(String id){
		String value = getString(id);
		return StringUtils.isNotEmpty(value);
	}
	
	/**
	 * isEmpty
	 * @param id
	 * @return boolean
	 */
	public boolean isEmpty(String id){
	    String value = getString(id);
	    return StringUtils.isEmpty(value);
	}
	
	/**
	 * value Equals
	 * @param id
	 * @param value
	 * @return boolean
	 */
	public boolean valueEquals(String id,String value){
		String value1 = getString(id);
		return StringUtils.equals(value1,value);
	}
	
	/**
	 * get Date String
	 * @param id
	 * @return String
	 */
	public String getDateString(String id){
		return StringCoreUtils.getDateString(Constants.DATE_FORMAT_MODE_NORMAL,get(id),null);
	}
	
	/**
	 * get Date String
	 * @param id
	 * @param defaultStr
	 * @return String
	 */
	public String getDateString(String id,String defaultStr){
		return StringCoreUtils.getDateString(Constants.DATE_FORMAT_MODE_NORMAL,get(id),defaultStr);
	}
	
	/**
	 * get Date Full String
	 * @param id
	 * @return String
	 */
	public String getDateFullString(String id){
		return StringCoreUtils.getDateString(Constants.DATE_FORMAT_MODE_FULL,get(id),"");
	}
	
	/**
	 * get Date Full String
	 * @param id
	 * @return String
	 */
	public String getDateMonthString(String id){
		return StringCoreUtils.getDateString(Constants.DATE_FORMAT_MODE_MONTH,get(id),"");
	}
	
	/**
	 * get Money String
	 * @param id
	 * @return String
	 */
	public String getMoneyString(String id){
		return StringCoreUtils.getMoneyStr(getString(id));
	}
	
	/**
     * add Data<br>
     * @param key
     * @param value
     */
    public void addData(String key,Object value){
    	put(key,value);
    }
    
    /**
     * sumInt
     * @param key
     * @param value
     */
    public void sum(String key, long value){
        long v = getLong(key,0);
        put(key,String.valueOf(v+value));
    }
    
    /**
     * put
     * @param key
     * @param value
     */
    public synchronized Object put(Object obj, Object obj1)
    {
        if(obj1 == null)
            obj1 = "";
        Object obj2 = null;
        if(obj1 instanceof Box)
        {
            Object obj3 = super.get(obj);
            if(obj3 != null)
            {
                Object obj4 = null;
                if(obj3 instanceof Box)
                {
                    obj4 = new ArrayList();
                    ((List) (obj4)).add(obj3);
                } else
                if(obj3 instanceof List)
                    obj4 = (List)obj3;
                else
                    obj4 = new ArrayList();
                ((List) (obj4)).add(obj1);
                obj2 = super.put(obj, obj4);
            } else
            {
                obj2 = super.put(obj, obj1);
            }
        } else
        {
            obj2 = super.put(obj, obj1);
        }
        return obj2;
    }
    
    public String toJSONOnlyData(List list)
    {
        StringBuffer stringbuffer = new StringBuffer();
        stringbuffer.append("[");
        if(list != null ){
            for(int i = 0; i < list.size(); i++)
            {
                Box box1 = (Box)list.get(i);
                stringbuffer.append(box1.getJSON()).append(",\n");
            }
        }else{
            Box box = new Box();
            stringbuffer.append(box.getJSON()).append(",\n");
        }
        if(stringbuffer.length() > 2){
        	stringbuffer.delete(stringbuffer.length() - 2, stringbuffer.length());
        }
        stringbuffer.append("]");
        return stringbuffer.toString();
    }
    
    public String getJSON()
    {
        StringBuffer stringbuffer = new StringBuffer();
        Enumeration<String> enumeration = keys();
        stringbuffer.append("{");
        do{
            if(!enumeration.hasMoreElements()){
                break;
            }
            String s1 = (String)enumeration.nextElement();
            	String value = null;
            	if(get(s1) instanceof Integer || get(s1) instanceof Long  || get(s1) instanceof Float || get(s1) instanceof BigDecimal || get(s1) instanceof Boolean || get(s1) instanceof  Timestamp || get(s1) instanceof  Date ){
            		value = ""+get(s1);
            	}else if(get(s1) instanceof List){
            		value = toJSONOnlyData((List)get(s1));
            	}else{
            		value = ""+get(s1);
            	}
            	
                String s2 = StringUtil.replaceStringAll2(value, "\r\n", " ");
                s2 = StringUtil.replaceStringAll2(s2, "\r", " ");
                //s2 = Util.replaceStringAll2(s2, "\n", "\n");
                //s2 = Util.replaceStringAll2(s2, "\"", "\\\"");
                //System.out.println("[ 포함 : "+s2.indexOf("["));
                //System.out.println("] 포함 : "+s2.indexOf("]"));
                if(s2.indexOf("[") == 0 && s2.indexOf("]") != -1){  
                	stringbuffer.append("\"").append(s1).append("\":").append(s2).append(",");
                }else{
                	stringbuffer.append("\"").append(s1).append("\":\"").append(s2).append("\",");
                }
                
        } while(true);
        stringbuffer.delete(stringbuffer.length() - 1, stringbuffer.length());
        stringbuffer.append("}");
        return stringbuffer.toString();
    }
    
    public String getParam()
    {
    	StringBuffer stringbuffer = null; 
    	if(keys().hasMoreElements() == true){
    		stringbuffer = new StringBuffer();
		    Enumeration<String> enumeration = keys();
		    do{
		        if(!enumeration.hasMoreElements()){
		            break;
		        }
		        String s1 = (String)enumeration.nextElement();
		        	String value = null;
		        	if(get(s1) instanceof Integer || get(s1) instanceof Long  || get(s1) instanceof Float || get(s1) instanceof BigDecimal || get(s1) instanceof Boolean || get(s1) instanceof  Timestamp){
		        		value = ""+get(s1);
		        	}else if(get(s1) instanceof List){
		        		//value = toJSONOnlyData((List)get(s1));
		        	}else{
		        		value = getString(s1);
		        	}
		        	/*
		            String s2 = Util.replaceStringAll2(value, "\r\n", " ");
		            s2 = Util.replaceStringAll2(s2, "\r", " ");
		            s2 = Util.replaceStringAll2(s2, "\n", " ");
		            */
		        	if(value != null && !"".equals(value) && !"null".equals(value) && !"photo".equals(s1) ){
		        		stringbuffer.append(s1).append("=").append(value).append("&");
		        	}
		    } while(true);
		    if(stringbuffer.length() >= 1){
		    	stringbuffer.delete(stringbuffer.length() - 1, stringbuffer.length());
		    }
    	}else{
    		return ""; 
    	}
        return stringbuffer.toString();
    }
    
}