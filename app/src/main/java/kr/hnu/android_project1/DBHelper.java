package kr.hnu.android_project1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "users.db", null, 2);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users ( _id INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT, password TEXT, name TEXT);");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }
}
