package kr.hnu.android_project1;

import static kr.hnu.android_project1.MainActivity.loginID;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import kr.hnu.android_project1.databinding.FragmentReceiveMessageBinding;
import kr.hnu.android_project1.databinding.FragmentSendMessageBinding;
import kr.hnu.android_project1.databinding.FragmentWriteMessageBinding;

public class SendMessageFragment extends Fragment {
    private FragmentSendMessageBinding binding;
    ArrayList<Messages> messageList;
    MyRecyclerViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSendMessageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView recyclerView = binding.receiveRecycler;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        messageList = new ArrayList<>();
        adapter = new MyRecyclerViewAdapter(messageList);
        recyclerView.setAdapter(adapter);
        new SendMessageFragment.BackgroundTask().execute();

        return root;
    }
    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            try {
                target = "http://highero10.dothome.co.kr/AndroidProject/SendMessageList.php?sender=" + loginID;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                messageList.clear();
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                String sender, receiver, title, content, sendDate;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject row = jsonArray.getJSONObject(i);
                    sender = row.getString("sender");
                    receiver = row.getString("receiver");
                    title = row.getString("title");
                    content = row.getString("content");
                    sendDate = row.getString("sendDate");
                    Messages messages = new Messages(sender, receiver, title, content, sendDate);
                    messageList.add(messages);
                }
                adapter.notifyDataSetChanged();
                if (jsonArray.length() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SendMessageFragment.this.getContext());
                    builder.setMessage("받은 메시지가 없습니다.").setNegativeButton("확인", null).create().show();
                }
            } catch (Exception e) {
                Log.e("Error: ", s);
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}