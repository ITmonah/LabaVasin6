package com.example.labavasin6;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private SQLiteDatabase database;
    private ListView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper  = new DBHelper(getApplicationContext());
        try {
            database = dbHelper.getWritableDatabase();
        } catch (Exception e){
            e.printStackTrace();
        }

        scrollView = findViewById(R.id.ListView);

        ArrayList<HashMap<String,String>> animals = new ArrayList<>();
        HashMap<String,String> animal = new HashMap<>();
        Cursor cursor = database.rawQuery("SELECT animals.id, animals.name, animal_types.name FROM animals JOIN animal_types ON animal_types.id = animals.animal_type_id",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            animal=new HashMap<>();
            animal.put("name",cursor.getString(1));
            animal.put("type",cursor.getString(2));
            animals.add(animal);
            cursor.moveToNext();
        }
        cursor.close();
        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(),
                animals,
                android.R.layout.simple_list_item_2,
                new String[]{"name","tupe"},
                new int[]{android.R.id.text1,android.R.id.text2}
        );
    }
}