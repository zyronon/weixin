package ttentau.weixin.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ttent on 2017/2/10.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final String CREATE_MSG_INFO="create table msginfo(" +
            "id integer primary key autoincrement," +
            "chatid varchar," +
            "username varchar,"+
            "content varchar,"+
            "type integer)";
    public static final String CREATE_MSG_LIST="create table msglist(" +
            "id integer primary key autoincrement," +
            "chatid varchar," +
            "username varchar,"+
            "lastcontent varchar," +
            "time real)";
    public DbHelper(Context context) {
        super(context,"WeiXin.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MSG_INFO);
        db.execSQL(CREATE_MSG_LIST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
