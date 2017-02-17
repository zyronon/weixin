package ttentau.weixin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import ttentau.weixin.bean.Msg;

/**
 * Created by ttent on 2017/2/10.
 */

public class SaveMsgInfo {

    private final DbHelper mDb;
    private static SaveMsgInfo saveMsgInfo=null;

    private SaveMsgInfo(Context context){
        mDb = new DbHelper(context);
    }
    public static SaveMsgInfo getInstence(Context context){
        if (saveMsgInfo==null){
            saveMsgInfo = new SaveMsgInfo(context);
        }
        return saveMsgInfo;
    }
    public void insert(String chatid,String username,String content,int type){
        SQLiteDatabase wdb = mDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("chatid",chatid);
        values.put("username",username);
        values.put("content",content);
        values.put("type",type);
        wdb.insert("msginfo",null,values);
        wdb.close();
    }
    public void query(){
        SQLiteDatabase wdb = mDb.getWritableDatabase();
        Cursor cursor = wdb.query("msginfo", null, null, null, null, null, null);
        while (cursor.moveToNext()){
            String chatid = cursor.getString(1);
            String content = cursor.getString(3);
            Log.e("tag","chatID==="+chatid+"content++++++"+content);
        }
        cursor.close();
        wdb.close();
    }
    public ArrayList<Msg> query(String chatid){
        ArrayList<Msg> msgList = new ArrayList<>();
        SQLiteDatabase wdb = mDb.getWritableDatabase();
        Cursor query = wdb.query("msginfo"  , new String[]{"content", "type"}, "chatid=?", new String[]{chatid}, null, null, null);
        while (query.moveToNext()){
            String content = query.getString(0);
//            String type = query.getString(1);
            int type = query.getInt(1);
            Msg msg = new Msg(type, content);
            msgList.add(msg);
//            Log.e("tag","content+==="+content+"type===="+ android.R.attr.type);
        }
        query.close();
        wdb.close();
        return msgList;
    }


}
