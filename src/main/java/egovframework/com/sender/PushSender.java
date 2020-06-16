package egovframework.com.sender;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class PushSender {

private static final String API_KEY= "AIzaSyCJjpUpC166MY3C5wn0T9zF7q1Lu0rEmyc"; 
	
	//기기가 활성화 상태일 때 보여줄 것인지. 
	private static final boolean DELAY_WHILE_IDLE = false;
	//기기가 비활성화 상태일 때 GCM Storage에 보관되는 시간
	private static int TIME_TO_LIVE = 3;
	
	//private static final String regId ="보낼 단말의 id"; 
	private Sender sender;
	private Message msg;
	
	public String sendGcmPush(String title, String push_cont , String device_token , String device_del , String board_idx){
		String error = "";
		sender = new Sender(API_KEY);								//푸시 보내는 객체 생성
		Message.Builder builder = new Message.Builder();		//푸시 메시지 만드는 객체
		builder.delayWhileIdle(DELAY_WHILE_IDLE);
		builder.timeToLive(TIME_TO_LIVE);
		
		builder.addData("title", title);		//푸시 메시지에 데이터 추가
		builder.addData("msg", push_cont);		//푸시 메시지에 데이터 추가
		builder.addData("device_token", device_token);		//푸시 메시지에 데이터 추가
		builder.addData("device_del", device_del);		//푸시 메시지에 데이터 추가
		builder.addData("board_idx", board_idx);		//푸시 메시지에 데이터 추가
		
		msg = builder.build();									//푸시 메시지 생성
		try {
			Result result = sender.send(msg, device_token, 5);			//푸시 전송(메세지,등록자 아이디,전송회수)//등록자 아이디가 아닌 보낼 대상의 ID 입니다.
			if(result.getMessageId() != null){
				String canonicalRegId = result.getCanonicalRegistrationId();
				if(canonicalRegId != null){
					//System.out.println("메세지는 만들어 졌지만 표준 registration ID를 반환된 경우 현재의 registration ID를 대처할 필요가 있습니다.");
				}
			}else{
				error = result.getErrorCodeName();
					if( error.equals(Constants.ERROR_NOT_REGISTERED)){
				}
			}
//			System.out.println("result ==> "+result);
//			System.out.println("getCanonicalIds : " + result.getCanonicalRegistrationId()); 
//			System.out.println("getMessageId : " + result.getMessageId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return error;
	}
	
	public static PushSender getInstance(){
		PushSender pushSender = new PushSender();
		return pushSender;
	}
}
