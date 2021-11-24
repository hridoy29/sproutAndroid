package com.example.pantomime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    Button _btnLogin;
    EditText _editTextUserMobileNo;
    EditText _editTextUserPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this._editTextUserMobileNo = (EditText) findViewById(R.id.editTextUserMobileNo);
        this._editTextUserPassword = (EditText) findViewById(R.id.editTextUserPassword);
        this._btnLogin = (Button) findViewById(R.id.btnLogin);
        this._btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    login();
                    /*Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);*/
                } catch (Exception ex) {
                    Toast.makeText(LoginActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void login() {
        if (validate()) {
            String user_id = this._editTextUserMobileNo.getText().toString();
            String password = this._editTextUserPassword.getText().toString();
            ArrayList<String> passing = new ArrayList();
            passing.add(user_id);
            passing.add(password);
            getUsers(user_id,password);
        }
        else {
            onLoginFailed();
        }
    }
    public boolean validate() {
        boolean valid = false;
        String user_id = this._editTextUserMobileNo.getText().toString();
        String password = this._editTextUserPassword.getText().toString();
        // mDatabaseHelper.getUserValidation(user_id,password);
        if (user_id.isEmpty()) {
            this._editTextUserMobileNo.setError("enter a user id");
            return false;
        } else {
            this._editTextUserMobileNo.setError(null);
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            this._editTextUserPassword.setError("between 4 and 10 alphanumeric characters");
            return false;
        }
        else {
            this._editTextUserPassword.setError(null);
        }

        return true;
    }
    private void getUsers(String mobileNo, String password) {

        Call<ArrayList<ClassRegestration>> call = RetrofitClient.getInstance().getApi().UserLogin();
        call.enqueue(new Callback<ArrayList<ClassRegestration>>() {

            @Override
            public void onResponse(Call<ArrayList<ClassRegestration>> call, Response<ArrayList<ClassRegestration>> response) {
                ArrayList<ClassRegestration> userList = response.body();
                boolean isUserAvailable=false;
                if (userList.size() > 1) {
                    for (int i = 0; i <userList.size();i++){
                        if(userList.get(i).getMobileNo().equals(mobileNo) && userList.get(i).getPassword().equals(password)){
                            onLoginSuccess();
                        }

                    }
                } else {
                    // onLoginFailed();
                    Toast.makeText(LoginActivity.this, "Please keep your internet connected", Toast.LENGTH_LONG).show();
                }

            }
            @Override
            public void onFailure(Call<ArrayList<ClassRegestration>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onLoginSuccess() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        Bundle bundle = new Bundle();
        int _userid=0;
        /*SharedPreferences.Editor editor = getSharedPreferences("UserId", MODE_PRIVATE).edit();
        editor.putString("Id", this._useridText.getText().toString());
        editor.putString("Password", this._passwordText.getText().toString());
        editor.apply();*/

        bundle.putInt("userid", _userid);
        Toast.makeText(this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void onLoginFailed() {
        this._btnLogin.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Invalid User", Toast.LENGTH_LONG).show();
    }
}