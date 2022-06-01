package kr.hnu.android_project1;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WriteMessageRequest extends StringRequest {
    final static private String URL = "http://highero10.dothome.co.kr/AndroidProject/WriteMessage.php";
    private Map<String, String> parameters;
    public WriteMessageRequest(String sender, String receiver, String title, String content, String sendDate, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SendMessageRequest", error.getMessage());
            }
        });
        parameters = new HashMap<>();
        parameters.put("sender", sender);
        parameters.put("receiver", receiver);
        parameters.put("title", title);
        parameters.put("content", content);
        parameters.put("sendDate", sendDate);
    }
    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
