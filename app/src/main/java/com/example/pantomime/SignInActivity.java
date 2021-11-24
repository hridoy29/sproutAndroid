package com.example.pantomime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    Button _btnSignIn;
    /*EditText _editTextName;*/
    EditText _editTextMobileNo;
    EditText _editTextPassword;
    EditText _editTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        /*this._editTextName = (EditText) findViewById(R.id.editTextName);*/
        this._editTextMobileNo = (EditText) findViewById(R.id.editTextMobileNo);
        this._editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        this._editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        this._btnSignIn = (Button) findViewById(R.id.btnSignIn);

        this._btnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String Password = _editTextPassword.getText().toString();
                    String ConfirmPassword = _editTextConfirmPassword.getText().toString();
                    if(Password.equals(ConfirmPassword)){
                        registration();
                    }
                    else{
                        Toast.makeText(SignInActivity.this, "Password and confirm password are not same", Toast.LENGTH_LONG).show();
                        return;
                    }


                    /*Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                    startActivity(intent);*/
                } catch (Exception ex) {
                    Toast.makeText(SignInActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void registration() {
        if (validate()) {
            String mobileNo = this._editTextMobileNo.getText().toString();
            String password = this._editTextPassword.getText().toString();
            postUsersInfo(mobileNo,password);
        }
        else {
            onLoginFailed();
        }
    }

    public boolean validate() {
        boolean valid = false;
        String user_id = this._editTextMobileNo.getText().toString();
        String password = this._editTextPassword.getText().toString();

        if (user_id.isEmpty()) {
            this._editTextMobileNo.setError("enter a user id");
            return false;
        } else {
            this._editTextMobileNo.setError(null);
        }
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            this._editTextPassword.setError("between 4 and 10 alphanumeric characters");
            return false;
        }
        else {
            this._editTextPassword.setError(null);
        }

        return true;
    }
    private void postUsersInfo(String mobileNo, String password) {

        ClassRegestration _classRegestration = new ClassRegestration(1,mobileNo, password,true);
        Call<ClassRegestration> call = RetrofitClient
                .getInstance()
                .getApi().UserRegestration(_classRegestration);

        call.enqueue(new Callback<ClassRegestration>() {
            @Override
            public void onResponse(Call<ClassRegestration> call, Response<ClassRegestration> response) {
                ClassRegestration s = response.body();
                if ("Users already exist".equals(s.MobileNo)) {
                    Toast.makeText(SignInActivity.this, "Mobile number already exist", Toast.LENGTH_LONG).show();
                } else if(s.Id != 0) {
                    Toast.makeText(SignInActivity.this, "Registration Successful", Toast.LENGTH_LONG).show();
                    onRegistrationSuccess();
                }
                else {
                    Toast.makeText(SignInActivity.this, "Invalid", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ClassRegestration> call, Throwable t) {
                Toast.makeText(SignInActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onRegistrationSuccess() {
        Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
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
        this._btnSignIn.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Invalid User", Toast.LENGTH_LONG).show();
    }
}