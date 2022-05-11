package kr.hnu.android_project1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MessageDBHelper extends SQLiteOpenHelper {
    SQLiteDatabase writableDB, readableDB;
    public MessageDBHelper(Context context) {
        super(context, "message.db", null, 3);
        writableDB = getWritableDatabase();
        readableDB = getReadableDatabase();
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE message ( _id INTEGER PRIMARY KEY AUTOINCREMENT, sender TEXT, receiver TEXT, title TEXT, content TEXT, date TEXT);");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS message");
        onCreate(db);
    }
    public Cursor getCursorMsg() {
        String selectQuery = "SELECT sender, receiver, title, content, date FROM message order by _id desc";
        // 최신 메시지가 제일 위로 나오게 데이터를 _id 기준으로 내림차순 정렬
        return readableDB.rawQuery(selectQuery, null);
    }
}
