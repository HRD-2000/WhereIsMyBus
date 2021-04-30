package com.hrd.whereismybus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.hrd.whereismybus.Pojo.Login_pojo;
import com.hrd.whereismybus.Pojo.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    EditText edt_username,edt_password;
    Button login;

    String header;
    SharedPreferences mSP;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor editor;
    LoadingWithAnim loadingDialog;

    ArrayList<Login_pojo> model;

    String username,password;
    String login_url,result;

    TextInputLayout filledTextField_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //im = findViewById(R.id.imageView);
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        login = findViewById(R.id.login_button);

        filledTextField_username = findViewById(R.id.filledTextField_username);

        header = getString(R.string.header);
        mSP = getSharedPreferences("login", Context.MODE_PRIVATE);

        loadingDialog = new LoadingWithAnim(Login.this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Login.this, MapsRoute.class));

                /*
                if (edt_username.getText().toString().length()==0 ) {
                    edt_username.setError("Username can't be empty");
                }else if(edt_password.getText().toString().isEmpty()){
                    edt_password.setError("Password can't be empty");
                }else{
                    loadingDialog.startLoadingDialog();
                    login_url = header+"/user_login.php?username="+edt_username.getText().toString();
                    //new retrieve().execute();

                    Log.v("Login",""+login_url);

                }*/
            }
        });
    }

    public class retrieve extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {


            try
            {
                JsonParser o = new JsonParser();
                result = o.insert(login_url);
                model = new ArrayList<>();

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("res");

                Log.v("Login_DATA",""+result);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject11 = jsonArray.getJSONObject(i);
                    Login_pojo p = new Login_pojo();

                    p.setUsername(jsonObject11.getString("username"));
                    p.setPassword(jsonObject11.getString("password"));
                    model.add(p);

                    username = p.getUsername();
                    password = p.getPassword();

                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                Toast.makeText(Login.this, "Please check your Internet Connection and Retry", Toast.LENGTH_LONG).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (edt_username.getText().toString().equals(username) && edt_password.getText().toString().equals(password))
            {
                Toast.makeText(Login.this, " Login sucessful... ", Toast.LENGTH_SHORT).show();
                loadingDialog.dismissDialog();
                Intent intent = new Intent(Login.this, MapsRoute.class);

                startActivity(intent);

                mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                editor = mSharedPreferences.edit();
                editor.putString("email_id",edt_username.getText().toString());
                editor.putString("password",edt_password.getText().toString());
                editor.commit();

                mSP.edit().putBoolean("logged",true).apply();

            }
            else{
                loadingDialog.dismissDialog();
                Toast.makeText(Login.this, " Email or Password is incorrect!! ", Toast.LENGTH_SHORT).show();
            }



        }
    }


}