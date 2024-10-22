package com.example.labavasin6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper {
    private static String DB_name="animal.db";
    private static String DB_LOCATION;
    private static final int DB_version=1;

    private Context myContext;

    public DBHelper(Context context){
        super(context,DB_name,null,DB_version);
        this.myContext = context;
        DB_LOCATION=context.getApplicationInfo().dataDir+"/databases/";
        copyDB();
    }
    private boolean checkDB(){
        File fileDB = new File(DB_LOCATION + DB_name);
        return fileDB.exists();
    }

    private void copyDB(){
        if(!checkDB()){
            try {
                copyDBFile();
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        }
    }
    private void copyDBFile() throws IOException {
        InputStream inputStream= myContext.getAssets().open(DB_name);
        OutputStream outputStream = new FileOutputStream(DB_LOCATION + DB_name);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer,0,length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
