package kr.hnu.android_project1;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    // 로그인 리퀘스트
    final static private String URL = "http://highero10.dothome.co.kr/AndroidProject/UserLogin.php";
    private Map<String, String> parameters;
    public LoginRequest(String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RegisterRequest", error.getMessage());
            }
        });
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
