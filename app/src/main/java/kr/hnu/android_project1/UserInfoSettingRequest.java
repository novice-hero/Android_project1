package kr.hnu.android_project1;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UserInfoSettingRequest extends StringRequest {
    final static private String URL = "http://highero10.dothome.co.kr/AndroidProject/UserInfoSetting.php";
    private Map<String, String> parameters;
    public UserInfoSettingRequest(String userPassword, String userName, String userDepartment, String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("UserInfoSettingRequest", error.getMessage());
            }
        });
        parameters = new HashMap<>();
        parameters.put("userPassword", userPassword);
        parameters.put("userName", userName);
        parameters.put("userDepartment", userDepartment);
        parameters.put("userID", userID);
    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
