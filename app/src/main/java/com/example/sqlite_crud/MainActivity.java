package com.example.sqlite_crud;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String TAG=MainActivity.class.getName();
    private Button addButton,deleteButton,showButton;
    private TextView textView;
    private DatabaseAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton=findViewById(R.id.add_button);
        deleteButton=findViewById(R.id.delete_button);
        showButton=findViewById(R.id.show_button);

        textView=findViewById(R.id.textView);

        openDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }

    private void openDB() {
        myAdapter=new DatabaseAdapter(this);
        myAdapter.open();
    }

    private void closeDB()
    {
        myAdapter.close();
    }


    private void  displayMessage(String txt)
    {
        textView.setText(txt);
    }

    public void addData(View view) {
        displayMessage("Clicked to add record");
        long newId=myAdapter.insertRow("Susmita","Heroin","India");
        Cursor cursor=myAdapter.getRow(newId);
        displayDataSets(cursor);

    }


    public void deleteData(View view) {
        displayMessage("Clicked to delete record");
        myAdapter.deleteAllRows();
    }


    public void showData(View view) {
        displayMessage("Clicked to display records");
        Cursor cursor=myAdapter.getAllRows();
        displayDataSets(cursor);
    }


    private void displayDataSets(Cursor cursor) {
        String msg="";

        if(cursor.moveToFirst()){
            do {
                int id=cursor.getInt(DatabaseAdapter.COL_ROWID);
                String name=cursor.getString(DatabaseAdapter.COL_NAME);
                String occupation=cursor.getString(DatabaseAdapter.COL_OCCUPATOON);
                String country=cursor.getString(DatabaseAdapter.COL_COUNTRY);

                msg+= "id = " +id +
                        ", name = " +name +
                        ", Occupation = " +occupation +
                        ", Country = " +country +"\n" ;
            }while (cursor.moveToNext());
        }
        cursor.close();
        displayMessage(msg);
    }
}
