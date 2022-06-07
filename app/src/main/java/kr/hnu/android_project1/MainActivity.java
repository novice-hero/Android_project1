package kr.hnu.android_project1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    // 로그인 화면
    private AlertDialog dialog;
    private EditText editID, editPW;
    public static String loginID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editID = findViewById(R.id.inputID);
        editPW = findViewById(R.id.inputPW);
    }

    public void onClickLogin(View v) {
        String userID = editID.getText().toString();
        String userPassword = editPW.getText().toString();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean isSuccess = jsonResponse.getBoolean("success");
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    if (isSuccess) {
                        loginID = editID.getText().toString();
                        Intent intentRegister = new Intent(MainActivity.this, NavActivity.class);
                        startActivity(intentRegister);
                        finish();
                    } else {
                        dialog = builder.setMessage("Login failed").setNegativeButton("Retry", null).create();
                        dialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(loginRequest);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    public void moveSignUp(View v) { // 계정 생성 화면으로 넘어감
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}