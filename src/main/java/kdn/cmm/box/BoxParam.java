package kdn.cmm.box;

import org.apache.commons.lang.StringUtils;

/**
 * <PRE>
 * BoxParam
 * </PRE>
 */ 
public class BoxParam extends Box
{
	/** Serial Version ID */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public BoxParam(){
	}
	
	/**
	 * Constructor
	 * @param id
	 * @param value
	 */
	public BoxParam(String id,String value){
	    this.put(id,value);
	}
	
	/**
	 * set Default
	 * @param id
	 * @param value
	 */
	public void setDefault(String id,String value){
		if(isNotEmpty(id)==false) put(id, value);
	}
		
	/**
	 * put String
	 * @param id
	 * @param value
	 */
	public void putString(String id,int value){
		put(id,String.valueOf(value));
	}
	
	/**
	 * put
	 * @param id
	 * @param value
	 * @param defaultValue
	 */
	public void put(String id,String value,String defaultValue){
		if(StringUtils.isEmpty(value)) put(id,defaultValue);
		else put(id,value);
	}
	
	/**
	 * get Num String
	 * @param id
	 * @return String
	 */
	public String getNumString(String id){
	    return StringCoreUtils.getNumString(getString(id));
	}
	
    /**
     * containsOfArray
     * @param id
     * @param value
     * @return boolean
     */
    public boolean containsOfArray(String id,String value){
        String [] arr = getStringArray(id);
        if(arr != null){
            for(int i=0;i<arr.length;i++){
                if(StringUtils.equals(value,arr[i])) return true;
            }
        }
        return false;
    }
}