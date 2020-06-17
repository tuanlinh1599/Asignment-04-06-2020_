package com.quannar178.managestudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String NAME_FILE = "/studentdb";
    private final String SV_MSSV = "MSSV";
    private final String SV_NAME = "NAME";
    private final String SV_GMAIL_ = "Gmail";
    private final String SV_BIRTHDAY_ = "BirthDay";
    private final String SV_LOCA = "Loca";
    Context activityContext;

    SearchView txtSearchValue;
    SQLiteDatabase db;
    String path;
    ListView listView;
    ListViewCustomAdapter listViewCustomAdapter;

    List<StudentModel> lists;
    List<StudentModel> liststemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityContext = this;

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission Denied. Asking for permission.");
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1234);
            }
        }
        lists = new ArrayList<>();
        liststemp = new ArrayList<>();


        listView = findViewById(R.id.listview);
        listViewCustomAdapter = new ListViewCustomAdapter(this, R.layout.custom_line_listview, lists);
        listView.setAdapter(listViewCustomAdapter);
        listView.setLongClickable(true);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(activityContext)
                        .setTitle("Delete student")
                        .setMessage("Are you sure delete " + lists.get(position).getMssv())
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                StudentModel student = lists.get(position);
                                deleteRecords(student.getMssv());
                                lists.remove(student);
                                listViewCustomAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create()
                        .show();
                    return true;
            }
        });

        openDatabase();
        createTable();
        Log.v("TAG", "Select data");
        selectData();

//        Log.v("TAG", "Select data insert");
//        insertRecords();
//        selectData();
//        Log.v("TAG", "Select data delete");
//        deleteRecords();
//        selectData();

    }

    public void openDatabase(){
        File file = getApplication().getFilesDir();
        path = file + NAME_FILE;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
    }

    public void createTable()
    {
        db.beginTransaction();
        try {
            db.execSQL("create table tblSTUDENT(" +
                    "MSSV int PRIMARY KEY," +
                    "NAME text," +
                    "BirthDay text," +
                    "Gmail text," +
                    "Loca text)");

            db.execSQL("insert into tblSTUDENT(MSSV, NAME, BirthDay, Gmail, Loca) values ('20173310', 'Nguyen Ba Quan', '17/08/1999', 'quan@gmail' , 'HUST')");
            db.execSQL("insert into tblSTUDENT(MSSV, NAME, BirthDay, Gmail,Loca) values ('20173311', 'Nguyen Ba Quan', '17/08/1999', 'quan@gmail' ,'HUST')");
            db.execSQL("insert into tblSTUDENT(MSSV, NAME, BirthDay, Gmail,Loca) values ('20173312', 'Nguyen Ba Quan', '17/08/1999', 'quan@gmail' ,'HUST')");
            db.execSQL("insert into tblSTUDENT(MSSV, NAME, BirthDay, Gmail,Loca) values ('20173313', 'Nguyen Ba Quan', '17/08/1999', 'quan@gmail' ,'HUST')");
            db.execSQL("insert into tblSTUDENT(MSSV, NAME, BirthDay, Gmail,Loca) values ('20173314', 'Nguyen Ba Quan', '17/08/1999',  'quan@gmail' , 'HUST')");
            db.execSQL("insert into tblSTUDENT(MSSV, NAME, BirthDay, Gmail,Loca) values ('20173317', 'Nguyen Ba Quan', '17/08/1999', 'quan@gmail' ,'HUST')");


            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void selectData()
    {
        Cursor cs = db.rawQuery("select * from tblSTUDENT", null);
        cs.moveToFirst();
        lists.clear();
        do {
            int MSSV = cs.getInt(0);
            String NAME = cs.getString(1);
            String BIRTHDAY = cs.getString(cs.getColumnIndex("BirthDay"));
            String GMAIL = cs.getString(cs.getColumnIndex("Gmail"));
            String LOCA = cs.getString(cs.getColumnIndex("Loca"));


            lists.add(new StudentModel(MSSV, NAME, BIRTHDAY, GMAIL, LOCA));
            listViewCustomAdapter.notifyDataSetChanged();



            Log.v("TAG", MSSV + " - " + NAME + " - " + BIRTHDAY + " - " + GMAIL + " - " + LOCA);
        } while (cs.moveToNext());
    }

    public void insertRecords(int MSSV, String name, String birthday, String gmail, String Loca)
    {
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("MSSV", MSSV);
            values.put("NAME", name);
            values.put("BirthDay", birthday);
            values.put("Gmail", gmail);
            values.put("Loca", Loca);


            long rowID = db.insert("tblSTUDENT", null, values);

            Log.v("TAG", "rowID insert: " + rowID);

            values.clear();

            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void updateRecords(int updateMSSV)
    {
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put("NAME", "UPDATE");

            long ret = db.update("tblSTUDENT", values, "MSSV = '" + updateMSSV + "'", null);
            Log.v("TAG", "ret update: " + ret);




            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void deleteRecords(int delMSSV)
    {
        db.beginTransaction();
        try {
            long ret = db.delete("tblSTUDENT", "MSSV = '" + delMSSV + "'", null);
            Log.v("TAG", "ret: " + ret);

            for(StudentModel student : lists){
                if(student.getMssv() == delMSSV){
                    lists.remove(student);
                }
                break;
            }


            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1234)
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED)
                Log.v("TAG", "Permission Denied.");
            else
                Log.v("TAG", "Permission Granted.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        txtSearchValue = (SearchView) menu.findItem(R.id.action_search).getActionView();


        txtSearchValue.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                liststemp.clear();

                for(StudentModel student : lists){
                    String mssv = ( student.getMssv() +"");
                    String name = student.getName();
                    if(mssv.contains(newText) || name.contains(newText)){
                        liststemp.add(student);
                    }
                }
                ListViewCustomAdapter listViewCustomAdapter1 = new ListViewCustomAdapter(activityContext, R.layout.custom_line_listview, liststemp);
                listView.setAdapter(listViewCustomAdapter1);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.addStudent){
            final Dialog dialog = new Dialog(activityContext);
            dialog.setTitle("Add student");
            dialog.setContentView(R.layout.custom_dialog_add);

            final EditText edtName, edtMssv, edtBirthday, edtGmail, edtLoca;
            Button button;

            edtMssv = dialog.findViewById(R.id.edtMSSVadd);
            edtName = dialog.findViewById(R.id.edtNameadd);
            edtBirthday = dialog.findViewById(R.id.edtBirthdayadd);
            edtGmail = dialog.findViewById(R.id.edtGmailadd);
            edtLoca = dialog.findViewById(R.id.edtLocaadd);
            button = dialog.findViewById(R.id.btnAdd);

            if(edtLoca.getText().equals("")
                    || edtName.getText().equals("")
                    ||edtBirthday.getText().equals("")
                    ||edtGmail.getText().equals("")
                    ||edtLoca.getText().equals("") ){

            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mssv = 0;
                    try{
                        mssv = Integer.parseInt(edtMssv.getText().toString());
                    }catch (ClassCastException e){
                        Log.v("Tag", e.toString());
                    }

                    String name = edtName.getText().toString();
                    String birthday = edtBirthday.getText().toString();
                    String gmail = edtGmail.getText().toString();
                    String loca = edtLoca.getText().toString();
                    if(edtMssv.getText().equals("")
                            || edtName.getText().equals("")
                            ||edtBirthday.getText().equals("")
                            ||edtGmail.getText().equals("")
                            ||edtLoca.getText().equals("") ){
                        Toast.makeText(activityContext,"Miss value", Toast.LENGTH_LONG).show();
                    }else{
                        insertRecords(mssv, name, birthday, gmail, loca);
                        selectData();
                    }
                }
            });
            dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}