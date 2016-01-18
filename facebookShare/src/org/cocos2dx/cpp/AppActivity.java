/****************************************************************************
Copyright (c) 2008-2010 Ricardo Quesada
Copyright (c) 2010-2012 cocos2d-x.org
Copyright (c) 2011      Zynga Inc.
Copyright (c) 2013-2014 Chukong Technologies Inc.
 
http://www.cocos2d-x.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
****************************************************************************/
package org.cocos2dx.cpp;

import java.util.Arrays;

import org.cocos2dx.lib.Cocos2dxActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.Sharer.Result;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class AppActivity extends Cocos2dxActivity {
	CallbackManager callbackManager;
	ShareDialog shareDialog;

	// jni로 쓰기 위함
	public static Activity actInstance;
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FacebookSdk.sdkInitialize(getApplicationContext());
		
		// jni로 사용하기 위함
		actInstance = this;
				
		callbackManager = CallbackManager.Factory.create();
		shareDialog = new ShareDialog(this);
		
		shareDialog.registerCallback(callbackManager, shareCallBack);
		
		
	}
	
	public FacebookCallback<Sharer.Result> shareCallBack = new FacebookCallback<Sharer.Result>() {
		@Override
        public void onSuccess(Result result) {
			
        }
        @Override
        public void onCancel() {
        }
        @Override
        public void onError(FacebookException error) {
        }
	};
	
	@Override
	protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    callbackManager.onActivityResult(requestCode, resultCode, data);
	}
	
	public void facebookShare() {
		Log.d("asdf", "aaaaa");
		if (ShareDialog.canShow(ShareLinkContent.class)) {
		    ShareLinkContent linkContent = new ShareLinkContent.Builder()
		            .setContentTitle("페이스북 이름")
		            .setContentDescription("내용")
		            .setContentUrl(Uri.parse("cid.dothome.co.kr"))
		            .setImageUrl(Uri.parse("http://cid.dothome.co.kr/images/cid_icon4.png"))
		            .build();

		    shareDialog.show(linkContent);
		}
	}
	
	// jni로 사용하기 위함
	public static Object getJavaActivity()
	{
		return actInstance;
	}
}