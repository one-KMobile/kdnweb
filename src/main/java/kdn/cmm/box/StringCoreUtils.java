package kdn.cmm.box;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * <PRE>
 * StringCoreUtils
 * </PRE>
 */
public class StringCoreUtils
{
	/**
	 * get Null <br>
	 * @param str
	 * @return String
	 */
	public static String getString(Object obj){
		String str = null;
		if(obj instanceof String){
			str = (String)obj;
		}
		else {
			str = obj != null ? obj.toString() : "";
		}
		if(str != null && str.length() == 0 ) return null;
		return str;
	}
	
	/**
	 * get NumString <BR>
	 * @return String
	 * @see getFloatString(String)
	 */
	public static String getNumString(String str){
		if(str == null) return null;
		StringBuffer sb = new StringBuffer();
		char [] ca = str.toCharArray();
		for(int i=0;i<ca.length;i++){
			if(  ca[i] >= '0' && ca[i] <= '9'  ){
				sb.append(ca[i]);
				continue;
			}
		}
		return sb.toString();
	}
	
	/**
	 * get Float String <BR>
	 * @return String
	 */
	public static String getFloatString(String str){
		if(str == null) return null;
		StringBuffer sb = new StringBuffer();
		char [] ca = str.trim().toCharArray();
		boolean dot = false;
		for(int i=0;i<ca.length;i++){
			if( i == 0 && ca[i] == '-' ){
				sb.append(ca[i]);
				continue;
			}
			if(  ca[i] >= '0' && ca[i] <= '9'  ){
				sb.append(ca[i]);
				continue;
			}
			if( dot == false && ca[i] == '.' ){
				sb.append(ca[i]);
				dot = true;
				continue;
			}
		}
		return sb.toString();
	}
	
	/**
	 * Replace String Buffer
	 * @param sb
	 * @param id
	 * @param replace
	 * @return StringBuffer
	 */
	public static StringBuffer replaceBuffer(StringBuffer sb,String id,String replace){
		if(replace == null) return sb;
		
		int inx = -1;
		while( (inx=sb.indexOf(id)) > -1){
			sb.replace(inx,inx+id.length(),replace);
		}
		return sb;
	}
	
	/**
	 * Replace String
	 * @param src
	 * @param value
	 * @param replace
	 * @return StringBuffer
	 */
	public static String replaceString(String src,String value,String replace){
		if(src == null || value == null || replace == null) return src; 
		return replaceBuffer(new StringBuffer(src),value,replace).toString();
	}
	
	/**
	 * get Date
	 * @param str
	 * @return Date
	 */
	public static Date getDate(String str){
		if(str == null) return null;
		String num = StringCoreUtils.getNumString(str);
		if(num == null || num.length() == 0) return null;
		
		java.util.Date date = null;

		if(num.length() == 8){
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.IN_DATE_FORMAT);
			try{ date = sdf.parse(num); }catch(ParseException pex){}
		} else if(num.length() == 14){
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.IN_DATE_FORMAT_FULL);
			try{ date = sdf.parse(num);}catch(ParseException pex){}
		} else if(num.length() == 6){
			SimpleDateFormat sdf = new SimpleDateFormat(Constants.IN_DATE_FORMAT_MONTH);
			try{ date = sdf.parse(num); }catch(ParseException pex){}
		}
		return date != null ? new Date(date.getTime()) : null;
	}
	
	/**
	 * get Int
	 * @param str
	 * @return int
	 */
	public static int getInt(String str){
		if(str == null) return 0;
		String num = StringCoreUtils.getFloatString(str);
		return Integer.parseInt(num);
	}
	
	/**
	 * get Int
	 * @param str
	 * @param defaultValue
	 * @return int
	 */
	public static int getInt(String str,int defaultValue){
		if(str == null || str.length() == 0) return defaultValue;
		return getInt(str);
	}
	
	/**
	 * get long
	 * @param str
	 * @return long
	 */
	public static long getLong(String str){
		if(str == null) return 0;
		String num = StringCoreUtils.getFloatString(str);
		return Long.parseLong(num);
	}
	
	/**
	 * get long
	 * @param str
	 * @param defaultValue
	 * @return long
	 */
	public static long getLong(String str,long defaultValue){
		if(str == null || str.trim().length() == 0) return defaultValue;
		return getLong(str);
	}
	
	/**
	 * get Money Str
	 * @param str
	 * @return String
	 */
	public static String getMoneyStr(String str){
		long v = getLong(str,0);
		DecimalFormat df = new DecimalFormat("#,###"); 
		return df.format(v);
	}
	
	/**
	 * get Date Str
	 * @return String
	 */
	public static String getDateStr(){
		return getDateStr(new java.util.Date());
	}
	
	/**
	 * get Date Full Str
	 * @return String
	 */
	public static String getDateFullStr(){
		return getDateFullStr(new java.util.Date());
	}
	
	/**
	 * get Date String
	 * @param date
	 * @return String
	 */
	public static String getDateStr(java.util.Date date){
		if(date == null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
		return sdf.format(date);
	}
	
	/**
	 * get Date String
	 * @param date
	 * @return String
	 */
	public static String getDateFullStr(java.util.Date date){
		if(date == null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_FULL);
		return sdf.format(date);
	}
	
	/**
	 * get Date String
	 * @param date
	 * @return String
	 */
	public static String getDateMonthStr(java.util.Date date){
		if(date == null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT_MONTH);
		return sdf.format(date);
	}	
	
	/**
	 * get Fixed Length String
	 * @param val
	 * @param len
	 * @return String
	 */
	public static String getFixLengthStr(int val,int len){
		StringBuffer sb = new StringBuffer();
		sb.append(val);
		int size = len - sb.length();
		for(int i=0;i<size;i++) sb.insert(0,'0');
		return sb.toString();
	}
		
	/**
	 * get Date String
	 * DATE_FORMAT_MODE_NORMAL - NORMAL Format
	 * DATE_FORMAT_MODE_FULL - FULL Format
	 * DATE_FORMAT_MODE_MONTH - Month Format
	 * @param formatMode
	 * @param obj
	 * @param defaultStr
	 * @return String
	 */
	public static String getDateString(int formatMode,Object obj,String defaultStr){
		String re = null;
		java.util.Date d = null;
		if(obj != null){
			if(obj instanceof java.util.Date){
				d = (java.util.Date)obj;
			} else if( obj instanceof String){
				d = StringCoreUtils.getDate((String)obj);
			} else {
				re = obj.toString();
			}
		}
		
		if( d != null)
		{
			switch(formatMode){
				case Constants.DATE_FORMAT_MODE_NORMAL:
					re = getDateStr(d);
					break;
				case Constants.DATE_FORMAT_MODE_FULL:
					re = getDateFullStr(d);
					break;
				case Constants.DATE_FORMAT_MODE_MONTH:
					re = getDateMonthStr(d);
					break;
				default :
					re = getDateStr(d);
			}
		}
		
		if(re == null) re = defaultStr;
		return re;
	}
}