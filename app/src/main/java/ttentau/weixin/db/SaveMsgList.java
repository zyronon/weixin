package ttentau.weixin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import ttentau.weixin.bean.Msg;
import ttentau.weixin.bean.MsgList;

/**
 * Created by ttent on 2017/2/10.
 */

public class SaveMsgList {

    private final DbHelper mDb;
    private static SaveMsgList msglist=null;

    private SaveMsgList(Context context){
        mDb = new DbHelper(context);
    }
    public static SaveMsgList getInstence(Context context){
        if (msglist==null){
            msglist = new SaveMsgList(context);
        }
        return msglist;
    }
    public void insert(String chatid,String username,String lastcontent ){
        SQLiteDatabase wdb = mDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("chatid",chatid);
        values.put("username",username);
        values.put("lastcontent",lastcontent);
        wdb.insert("msglist",null,values);
        Log.e("tag","insert被调用了");
        wdb.close();
    }
    public ArrayList<MsgList> query(){
        ArrayList<MsgList> msgList = new ArrayList<>();
        SQLiteDatabase wdb = mDb.getWritableDatabase();
        Cursor cursor = wdb.query("msglist", null, null, null, null, null, null);
        while (cursor.moveToNext()){
            String chatid = cursor.getString(1);
            String username = cursor.getString(2);
            String lastcontent = cursor.getString(3);
            MsgList msgList1 = new MsgList(chatid, lastcontent);
            msgList.add(msgList1);
        }
        cursor.close();
        wdb.close();
        return  msgList;
    }
    public boolean query(String chatid){
        ArrayList<Msg> msgList = new ArrayList<>();
        SQLiteDatabase wdb = mDb.getWritableDatabase();
        Cursor query = wdb.query("msglist"  , new String[]{"chatid"}, null,null, null, null, null);
        while (query.moveToNext()){
            String queryCahtid = query.getString(0);
            if (queryCahtid.equals(chatid)){
                return true;
            }
        }
        query.close();
        wdb.close();
        return false;
    }
    public void updata(String chatid,String lastcontent){
        SQLiteDatabase wdb = mDb.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("lastcontent",lastcontent);
        wdb.update("msglist",value,"chatid=?",new String[]{chatid});
        wdb.close();
    }


}
