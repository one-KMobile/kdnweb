package kdn.aria;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import kdn.cmm.box.Box;


/**
 * AIRA algorithm to encrypt or decrypt the data is the class that provides the ability to.
 * @author shson
 *
 */
public class Cipher {
	
	/**
	 * ARIA encryption algorithm block size
	 */
	private static final int ARIA_BLOCK_SIZE = 16;
	private static String masterKeyEc = "";
	
	/**
	 * ARIA algorithm to encrypt the data.
	 * @param data Target Data
	 * @param key Masterkey
	 * @param keySize Masterkey Size
	 * @param charset Data character set
	 * @return Encrypted data
	 * @throws InvalidKeyException If the Masterkey is not valid
	 */
	public static String encrypt(String data, byte[] key, int keySize, String charset)
	throws UnsupportedEncodingException, InvalidKeyException {
		
		byte[] encrypt = null;
		if( charset == null ) {
			encrypt = BlockPadding.getInstance().addPadding(data.getBytes(), ARIA_BLOCK_SIZE);
		} else {
			encrypt = BlockPadding.getInstance().addPadding(data.getBytes(charset), ARIA_BLOCK_SIZE);
		}
		
		ARIAEngine engine = new ARIAEngine(keySize);
		engine.setKey(key);
		engine.setupEncRoundKeys();
		
		int blockCount = encrypt.length / ARIA_BLOCK_SIZE;
		for( int i = 0; i < blockCount; i++ ) {
			
			byte buffer[] = new byte[ARIA_BLOCK_SIZE];
			System.arraycopy(encrypt, (i * ARIA_BLOCK_SIZE), buffer, 0, ARIA_BLOCK_SIZE);
			
			buffer = engine.encrypt(buffer, 0);
			System.arraycopy(buffer, 0, encrypt, (i * ARIA_BLOCK_SIZE), buffer.length);
		}
		
		return Base64.toString(encrypt);
	}
	
	/**
	 * ARIA algorithm to decrypt the data.
	 * @param data Target Data
	 * @param key Masterkey
	 * @param keySize Masterkey Size
	 * @param charset Data character set
	 * @return Decrypted data
	 * @throws UnsupportedEncodingException If character is not supported
	 * @throws InvalidKeyException If the Masterkey is not valid
	 */
	public static String decrypt(String data, byte[] key, int keySize, String charset)
	throws UnsupportedEncodingException, InvalidKeyException {
		
		byte[] decrypt = Base64.toByte(data);
		
		ARIAEngine engine = new ARIAEngine(keySize);
		engine.setKey(key);
		engine.setupDecRoundKeys();
		
		int blockCount = decrypt.length / ARIA_BLOCK_SIZE;
		for( int i = 0; i < blockCount; i++ ) {
			
			byte buffer[] = new byte[ARIA_BLOCK_SIZE];
			System.arraycopy(decrypt, (i * ARIA_BLOCK_SIZE), buffer, 0, ARIA_BLOCK_SIZE);
			
			buffer = engine.decrypt(buffer, 0);
			System.arraycopy(buffer, 0, decrypt, (i * ARIA_BLOCK_SIZE), buffer.length);
		}
		
		if( charset == null ) {
			return new String(BlockPadding.getInstance().removePadding(decrypt, ARIA_BLOCK_SIZE));
		} else {
			return new String(BlockPadding.getInstance().removePadding(decrypt, ARIA_BLOCK_SIZE), charset);
		}
	}
	
	/**
	 * <설명>
	 * 파라메터 Box 암호화 셋팅
	 * @param [box]  
	 * @return [ecBox]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
	public static Box getParamEcSet(Box box) throws Exception { 
		Box ecBox = new Box();
		System.out.println("box.getParam() = " + box.getParam());
		String[] paramArray = box.getParam().split("&"); 
		
		byte[] key = createMasterKey(); //마스터 키 생성
		
		String data = "";
		for (String param : paramArray) {
			data = param.substring(param.indexOf("=") + 1, param.length());
			ecBox.put(param.substring(0,param.indexOf("=")), Cipher.encrypt(data, key, key.length*8, null)); //암호화
		}
		
		System.out.println("ecBox.getParam() = " + ecBox.getParam());
		return ecBox;
	}
	
	/**
	 * <설명>
	 * 파라메터 Box 복호화 셋팅
	 * @param [box]  
	 * @return [dcBox]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
	public static Box getParamDcSet(Box box) throws Exception { 
		Box dcBox = new Box();
		System.out.println("box.getParam() = " + box.getParam());
		String[] paramArray = box.getParam().split("&"); 
		
		byte[] key = createMasterKey(); //마스터 키 생성
		
		String data = "";
		for (String param : paramArray) {
			data = param.substring(param.indexOf("=") + 1, param.length());
			dcBox.put(param.substring(0,param.indexOf("=")), Cipher.decrypt(data, key, key.length*8, null)); //복호화
		}
		
		System.out.println("dcBox.getParam() = " + dcBox.getParam());
		return dcBox;
	}
	
	/**
	 * <설명>
	 * Aria 마스터키 생성
	 * @param [box]  
	 * @return [encryptStr]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public static byte[] createMasterKey(){
		String EncryptStr = ""; 
		byte[] key = new byte[16];  
		for (int i = 0; i < key.length; i++) {
			// 1 ~ 127까지 랜덤 숫자 구하기
			int random = (int) (Math.random() * 127) + 1;
			key[i] = (byte) random;
			if(i == (key.length - 1)){
				EncryptStr += key[i];	
			}else{
				EncryptStr += key[i] + "&";
			}
		}
		System.out.println("@@@@[EncryptStr] = " + EncryptStr);
		System.out.println();
		
		masterKeyEc = masterKeyEncrypt(EncryptStr); //Aria 마스터키를 암호화
		return key;
	}
	
	/**
	 * <설명>
	 * Aria 마스터키를 암호화
	 * @param [box]  
	 * @return [encryptStr]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 * @param  
	 */
	public static String masterKeyEncrypt(String masterKey){ 
		byte[] key = new byte[16];  
		key[0] = 112;
		key[1] = 119;
		key[2] = 114;
		
		String data = masterKey;
		
		try {
			data = Cipher.encrypt(data, key, key.length*8, null);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("[masterKey] 마스터키 데이터 = " + masterKey);
		System.out.println("[masterKey] 마스터키 암호화 데이터 = " + data);
		
		return data;
	}
	
	/**
	 * <설명>
	 * Aria 마스터키를 복호화
	 * @param [box]  
	 * @return [encryptStr]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 * @param  
	 */
	public static String masterKeyDecrypt(String masterKey) { 
		byte[] key = new byte[16];  
		key[0] = 112;
		key[1] = 119;
		key[2] = 114;
		
		String data = masterKey;
		
		try {
			data = Cipher.decrypt(data, key, key.length*8, null);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("[masterKey] 데이터 = " + masterKey);
		System.out.println("[masterKey] 암호화 키 = " + data);
		
		return data;
	}
	
	/**
	 * <설명>
	 * 복호화 리스트 가져오기
	 * @param [box]  
	 * @return [ecBox]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
	public static List<Box> getDecryptList(List<Box> list) throws Exception {
		List <Box> resultList = null;
		resultList = new ArrayList<Box>();
		Box resultBox = new Box();
		
		for (int i = 0; i < list.size(); i++) {
			resultBox = list.get(i);
			resultBox = getParamDcSet(resultBox);
			resultList.add(resultBox);
		}
		return resultList;
	}
	
	/**
	 * <설명>
	 * 마스터키 복호화 셋팅
	 * @param [masterKey]  
	 * @return [key]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
	public static byte[] getMasterKeySet(String masterKey) throws Exception {
		//복호화하기 위한 마스터키 셋팅 ***********************
		String master_key = masterKeyDecrypt(masterKey);
		String[] masterKeyArr = master_key.split("&");
		
		byte[] key = new byte[16];
		for (int i = 0; i < masterKeyArr.length; i++) {
			key[i] = Byte.parseByte(masterKeyArr[i]) ;
		}
		//**********************************************
		return key;
	}
	
	/**
	 * <설명>
	 * 순시결과 엑셀 파일 데이터 복호화 셋팅
	 * @param [EncryptStr] [key]  
	 * @return [resultStr]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
	public static String getDataDcSet(String EncryptStr, byte[] key) throws Exception { 
		String resultStr = "";
		if(!"".equals(EncryptStr) && EncryptStr != null){
			resultStr = Cipher.decrypt(EncryptStr, key, key.length*8, "utf-8");
		}
		return resultStr;
	}
	
	/**
	 * <설명>
	 * 순시결과 엑셀 파일 데이터를 복호화
	 * @param [box]  
	 * @return [resultBox]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
	/*public static TotInsExcelVO getInsDataDecryptList(TotInsExcelVO VOList) throws Exception {
		
		Map<String, TotInsExcelVO> map = new HashMap<String, TotInsExcelVO>();
		
		TotInsExcelVO resultVO = new TotInsExcelVO();
		String masterKey = VOList.getMaster_key();
		
		resultVO = (TotInsExcelVO) getInsDataParamDcSet(map, masterKey);
		
		return resultVO;
	}*/
	
	/**
	 * <설명>
	 * 순시결과 엑셀 파일 데이터 Box 복호화 셋팅
	 * @param [box]  
	 * @return [dcBox]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */ 
	/*public static Map<String, String> getInsDataParamDcSet(Map<String, TotInsExcelVO> VOmap, String masterKey) throws Exception { 
		//복호화하기 위한 마스터키 셋팅 ***********************
		String master_key = masterKeyDecrypt(masterKey);
		String[] masterKeyArr = master_key.split("&");
		
		byte[] key = new byte[16];
		for (int i = 0; i < masterKeyArr.length; i++) {
			key[i] = Byte.parseByte(masterKeyArr[i]) ;
		}
		//**********************************************
		
		Set<Entry<String, TotInsExcelVO>> entrySet = VOmap.entrySet();
	    Iterator<Entry<String, TotInsExcelVO>> iterator = entrySet.iterator();
	    
	    String[] name = null;
	    String[] value = null;
	    while (iterator.hasNext()) {
	        Map.Entry entry = (Map.Entry) iterator.next();
	        name = (String[]) entry.getKey();
	        value = (String[]) entry.getValue();
	        System.out.println("@@@@@@@@@@@ : " + entry.getKey() + "=" + entry.getValue());
	    }
	    
	    Map<String, String> resultMap = new HashMap<String, String>();
	    
	    for (int i = 0; i < name.length; i++) {
	    	resultMap.put(name[i], Cipher.decrypt(value[i], key, key.length*8, null));
		}
	    
		return resultMap;
	}*/
	
	/**
	 * <설명>
	 * 마스터키 암호화 리턴
	 * @param []  
	 * @return [masterKeyEc]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [범승철]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */
	public static String getMasterKeyEc() throws Exception {
		return masterKeyEc;
	}
	
	/**
	 * The sample code in the Cipher class
	 * @param args none
	 */
	public static void main(String args[]) {
		
		try {
			
			/*byte[] key = new byte[16];
			for( int i = 0; i < key.length; i++ ) {
				key[i] = (byte)i;
			}*/
			
			byte[] key = createMasterKey(); //마스터 키 생성 
			String data = "test4";
			
			//data : 암호화 할 String 값, key : 마스터키
			data = Cipher.encrypt(data, key, key.length*8, null);
			System.out.println("암호화 = " + data);
			data = Cipher.decrypt(data, key, key.length*8, null);
			System.out.println("복호화 = " + data);   
			
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * <설명>
	 * box 복호화 셋팅
	 * @param [box]  
	 * @return [box]
	 * @throws [예외명] [설명] // 각 예외 당 하나씩
	 * @author [정현도]
	 * @fix(<수정자명>) [yyyy.mm.dd]: [수정 내용]
	 */	
	public static Box getDecryptBox(Box box) throws Exception {
		Iterator<String> itr = box.keySet().iterator();
		Box resultBox = new Box();
		byte[] masterKey = Cipher.getMasterKeySet(box.getString("master_key"));
		while(itr.hasNext()) {
			String key = (String)itr.next();
			String value = "";
			String data = "";
			//value 존재여부 체크
			if(!"".equals(box.get(key))) {
				value = (String) box.get(key);
				data = Cipher.decrypt(value, masterKey, masterKey.length*8, "utf-8");
			}else {
				data = "";
			}
			resultBox.put(key, data);
		}
		
		return resultBox;
	}
}

