package kr.hnu.android_project1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReceiveMsgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_msg);

        createMsg("aaa","1st title","2022-03-26 12-37-54");
        createMsg("aaa","2nd title","2022-03-26 12-39-01");
        createMsg("aaa","3rd title","2022-03-26 12-45-12");
    }

    public void createMsg(String s, String t, String d) {
        LinearLayout linear = (LinearLayout)findViewById(R.id.linear_receive);
        RelativeLayout rel = (RelativeLayout) View.inflate(
                this, R.layout.newmessage, null);
        TextView sender = (TextView)rel.findViewById(R.id.sender);
        TextView title = (TextView)rel.findViewById(R.id.title);
        TextView date = (TextView)rel.findViewById(R.id.date);

        sender.setText(s);
        title.setText(t);
        date.setText(d);

        linear.addView(rel);
    }
}