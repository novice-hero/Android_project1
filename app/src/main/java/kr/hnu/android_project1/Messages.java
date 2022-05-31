package kr.hnu.android_project1;

public class Messages {
    String sender;
    String receiver;
    String title;
    String content;
    String sendDate;

    public Messages(String sender, String receiver, String title, String content, String sendDate) {
        this.sender = sender;
        this.receiver = receiver;
        this.title = title;
        this.content = content;
        this.sendDate = sendDate;
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
    public String getSendDate() { return sendDate; }
}
