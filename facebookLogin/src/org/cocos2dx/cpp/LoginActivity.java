package org.cocos2dx.cpp;

import org.json.JSONException;
import org.json.JSONObject;

import com.fTest.abcd.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class LoginActivity extends Activity {
	public static Activity actInstance;
	private CallbackManager callbackManager;
	String id="";
	String name="";
	String email="";
	Context mContext;
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getApplicationContext();
		actInstance = this;
	
		FacebookSdk.sdkInitialize(this.getApplicationContext());
		setContentView(R.layout.activity_face);


		
		callbackManager = CallbackManager.Factory.create();
		LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
		loginButton.setReadPermissions("email");
		loginButton.registerCallback(callbackManager,
				new FacebookCallback<LoginResult>() {
					@Override
					public void onSuccess(LoginResult loginResult) {//로그인이 성공되었을때 호출
						GraphRequest request = GraphRequest.newMeRequest(
								loginResult.getAccessToken(),
	                            new GraphRequest.GraphJSONObjectCallback() {
	                                @Override
	                                public void onCompleted(
	                                        JSONObject object,
	                                        GraphResponse response) {
	                                    // Application code
	                                	try {
											  
						id = (String) response.getJSONObject().get("id");//페이스북 아이디값
						name = (String) response.getJSONObject().get("name");//페이스북 이름
						email = (String) response.getJSONObject().get("email");//이메일
											  
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
	                                	
	                              // new joinTask().execute(); //자신의 서버에서 로그인 처리를 해줍니다
	                                  
	                                }
	                            });
	                    Bundle parameters = new Bundle();
	                    parameters.putString("fields", "id,name,email,gender, birthday");
	                    request.setParameters(parameters);
	                    request.executeAsync();
					}

					@Override
					public void onCancel() {
						Toast.makeText(LoginActivity.this, "로그인을 취소 하였습니다!", Toast.LENGTH_SHORT).show();
						// App code
					}

					@Override
					public void onError(FacebookException exception) {
						Toast.makeText(LoginActivity.this, "에러가 발생하였습니다", Toast.LENGTH_SHORT).show();
						// App code
					}
				});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
		
		Log.d("myLog"  ,"requestCode  "  + requestCode);
		Log.d("myLog"  ,"resultCode"  + resultCode);
		Log.d("myLog"  ,"data  "  + data.toString());
		
	}
	
}