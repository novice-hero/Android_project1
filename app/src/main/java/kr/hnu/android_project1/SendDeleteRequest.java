package kr.hnu.android_project1;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SendDeleteRequest extends StringRequest {
    // 보낸 메시지 삭제 리퀘스트
    final static private String URL = "http://highero10.dothome.co.kr/AndroidProject/DeleteSendMessage.php";
    private Map<String, String> parameters;
    public SendDeleteRequest(String sender, String sendDate, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("highero10", error.getMessage());
            }
        });
        parameters = new HashMap<>();
        parameters.put("sender", sender);
        parameters.put("sendDate", sendDate);
    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
