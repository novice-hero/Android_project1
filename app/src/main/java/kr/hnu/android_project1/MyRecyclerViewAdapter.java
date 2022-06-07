package kr.hnu.android_project1;

import static kr.hnu.android_project1.ReceiveMessageFragment.RMF_CHECK;
import static kr.hnu.android_project1.SendMessageFragment.SMF_CHECK;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {
    // 리사이클러뷰 어댑터
    ArrayList<Messages> arrayList;
    public MyRecyclerViewAdapter(ArrayList<Messages> arrayList) {
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.newmessage, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.MyViewHolder holder, int position) {
        Messages item = arrayList.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView sender, title, date;
        public MyViewHolder(View itemView) {
            super(itemView);
            // 보낸 사람, 제목, 보낸 시간 변수에 각각 지정
            sender = itemView.findViewById(R.id.new_sender);
            title = itemView.findViewById(R.id.new_title);
            date = itemView.findViewById(R.id.new_date);
            itemView.setOnClickListener(new View.OnClickListener(){ // 각 메시지를 클릭하면
                @Override
                public void onClick(View v) {
                    // 보낸이, 받는이, 제목, 내용, 보낸 시간 변수에 저장
                    String sender = arrayList.get(getAdapterPosition()).getSender();
                    String receiver = arrayList.get(getAdapterPosition()).getReceiver();
                    String title = arrayList.get(getAdapterPosition()).getTitle();
                    String content = arrayList.get(getAdapterPosition()).getContent();
                    String sendDate = arrayList.get(getAdapterPosition()).getSendDate();

                    if (SMF_CHECK) {
                        Intent sendIntent = new Intent(v.getContext(), SendMessageActivity.class);
                        sendIntent.putExtra("sender",sender);
                        sendIntent.putExtra("receiver",receiver);
                        sendIntent.putExtra("title",title);
                        sendIntent.putExtra("content",content);
                        sendIntent.putExtra("sendDate",sendDate);
                        v.getContext().startActivity(sendIntent);
                    } else if (RMF_CHECK) {
                        Intent receiveIntent = new Intent(v.getContext(), ReceiveMessageActivity.class);
                        receiveIntent.putExtra("sender",sender);
                        receiveIntent.putExtra("receiver",receiver);
                        receiveIntent.putExtra("title",title);
                        receiveIntent.putExtra("content",content);
                        receiveIntent.putExtra("sendDate",sendDate);
                        v.getContext().startActivity(receiveIntent);
                    }
                }
            });
        }
        public void setItem(Messages msg) {
            sender.setText(msg.getSender());
            title.setText(msg.getTitle());
            date.setText(msg.getSendDate());
        }
    }
}
