package android.com.myqq.listener;

public interface HttpCallbackListener {

	void onFinish(String response);

	void onError(Exception e);

}
