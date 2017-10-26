package br.com.acalfortaleza.acalapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity {

    LoginButton login_Button;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        inicializacaoControles();
        LoginFacebook();


    }



     private void inicializacaoControles(){

         login_Button = (LoginButton)findViewById(R.id.login_button);
         callbackManager = CallbackManager.Factory.create();

     }

      private void  LoginFacebook(){

          LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {



              @Override
               public void onSuccess(LoginResult loginResult) {

                  Intent it = new Intent(LoginActivity.this,MainActivity.class);
                  startActivity(it);

              }

              @Override
              public void onCancel() {



              }

              @Override
              public void onError(FacebookException error) {

              }
          });

      }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
