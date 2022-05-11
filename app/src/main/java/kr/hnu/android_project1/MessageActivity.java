package kr.hnu.android_project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {
    // 받은 메시지를 눌렀을 때 나오는 화면
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Intent intent = getIntent(); // 인텐트를 받아서 정보를 각각 저장

        String sender = intent.getExtras().getString("sender");
        String receiver = intent.getExtras().getString("receiver");
        String title = intent.getExtras().getString("title");
        String date = intent.getExtras().getString("date");
        String content = intent.getExtras().getString("content");

        TextView tv_sender = findViewById(R.id.tv_sender_empty);
        TextView tv_receiver = findViewById(R.id.tv_receiver_empty);
        TextView tv_title = findViewById(R.id.tv_title_empty);
        TextView tv_date = findViewById(R.id.tv_date_empty);
        TextView tv_content = findViewById(R.id.tv_content_empty);

        tv_sender.setText(sender);
        tv_receiver.setText(receiver);
        tv_title.setText(title);
        tv_date.setText(date);
        tv_content.setText(content);
    }
}