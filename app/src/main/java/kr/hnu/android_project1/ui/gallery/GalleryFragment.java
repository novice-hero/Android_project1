package kr.hnu.android_project1.ui.gallery;

import static kr.hnu.android_project1.MainActivity.loginID;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import kr.hnu.android_project1.Messages;
import kr.hnu.android_project1.MyRecyclerViewAdapter;
import kr.hnu.android_project1.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {
    // 받은 메시지 함
    private FragmentGalleryBinding binding;
    ArrayList<Messages> messageList;
    MyRecyclerViewAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        RecyclerView recyclerView = binding.receiveRecycler;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        messageList = new ArrayList<>();
        adapter = new MyRecyclerViewAdapter(messageList);
        recyclerView.setAdapter(adapter);
        new BackgroundTask().execute();

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
                target = "http://highero10.dothome.co.kr/AndroidProject/ReceiveMessageList.php?receiver=" + loginID;
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(GalleryFragment.this.getContext());
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
