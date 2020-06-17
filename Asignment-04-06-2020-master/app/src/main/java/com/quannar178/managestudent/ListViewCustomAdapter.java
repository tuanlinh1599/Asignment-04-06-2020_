package com.quannar178.managestudent;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

class ListViewCustomAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<StudentModel> lists;

    public ListViewCustomAdapter(Context context, int layout, List<StudentModel> lists) {
        this.context = context;
        this.layout = layout;
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyHolder myHolder;

        View row = convertView;
        if(row == null){
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();

            row = layoutInflater.inflate(layout, null);

            myHolder = new MyHolder();

            myHolder.mssv = row.findViewById(R.id.msvv);
            myHolder.name = row.findViewById(R.id.name);
            myHolder.birthday = row.findViewById(R.id.birthday);
            myHolder.gmail = row.findViewById(R.id.gmail);
            myHolder.location = row.findViewById(R.id.location);

            row.setTag(myHolder);
        }else{
            myHolder = (MyHolder) row.getTag();
        }

        myHolder.mssv.setText(lists.get(position).getMssv() + "");
        myHolder.name.setText(lists.get(position).getName());
        myHolder.birthday.setText(lists.get(position).getBirthDay());
        myHolder.gmail.setText(lists.get(position).getGmail());
        myHolder.location.setText(lists.get(position).getLoca());

        return row;

    }

    private class MyHolder{
        TextView mssv, name, birthday, gmail, location;
    }
}
