package org.cocos2dx.cpp;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

import com.asdf.platon.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

public class LoginActivity extends Activity {
	public static Activity actInstance;
	private CallbackManager callbackManager;
	String id="";
	String name="";
	String email="";
	Context mContext;
	LoginManager loginManager;
	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		FacebookSdk.sdkInitialize(this.getApplicationContext());
		
		mContext = getApplicationContext();
		actInstance = this;
	
		// ==================================================================
		//1. 로그인 버튼없이 바로 인증하려 한다면 1.로 되있는 주석 제거
		//2. 로그인 버튼을 이용하려 하면 2.로 되어있는 주석 제거
		// ==================================================================
		
		//2. setContentView(R.layout.activity_face);

		//1. callbackManager = CallbackManager.Factory.create();
		
		//1. loginManager = LoginManager.getInstance();
		
		//1. loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
			
		//});
		//2. LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
		//2. loginButton.setReadPermissions("email");
		//2. loginButton.registerCallback(callbackManager,
		//2.		new FacebookCallback<LoginResult>() {
					@Override
					public void onSuccess(LoginResult loginResult) {
						GraphRequest request = GraphRequest.newMeRequest(
								loginResult.getAccessToken(),
	                            new GraphRequest.GraphJSONObjectCallback() {
	                                @Override
	                                public void onCompleted(
	                                        JSONObject object,
	                                        GraphResponse response) {
	                                    // Application code
	                                	try {
	                                		id = (String) response.getJSONObject().get("id");
	                                		name = (String) response.getJSONObject().get("name");
	                                		email = (String) response.getJSONObject().get("email");
	                                		} catch (JSONException e) {
	                                			// TODO Auto-generated catch block
	                                			e.printStackTrace();
	                                			}
	                                	// new joinTask().execute(); //자신의 서버에서 로그인 처리를 해줍니다
	                                	}
	                                });
						Bundle parameters = new Bundle();
	                    parameters.putString("fields", "id ,name, email");
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
		Collection<String> permissions = Arrays.asList("public_profile", "user_friends");

        loginManager.logInWithReadPermissions(this, permissions); // Null Pointer Exception here
	}
		
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);

        redirectSignupActivity();
	}
	
	protected void redirectSignupActivity() {
		Log.d("redirectSignupActivity","redirectSignupActivity"); 		
        final Intent intent = new Intent(this, AppActivity.class);
        startActivity(intent);
        finish();
    }
}
