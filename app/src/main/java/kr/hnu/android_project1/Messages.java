package kr.hnu.android_project1;

public class Messages {
    String sender;
    String receiver;
    String title;
    String content;
    String date;

    public Messages(String sender, String receiver, String title, String content, String date) {
        this.sender = sender;
        this.receiver = receiver;
        this.title = title;
        this.content = content;
        this.date = date;
    }
    public String getSender() {
        return sender;
    }
    public String getReceiver() {
        return receiver;
    }
    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }
    public String getDate() { return date; }
}
