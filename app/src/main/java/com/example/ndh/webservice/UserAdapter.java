package com.example.ndh.webservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by NDH on 4/23/2018.
 */

public class UserAdapter extends BaseAdapter {
    Context context;
    int Layout;
    ArrayList<User> users;
    public UserAdapter(Context context, int layout, ArrayList<User> users) {
        this.context = context;
        Layout = layout;
        this.users = users;
    }


    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(Layout,null);
        TextView txtname = view.findViewById(R.id.txtNameUsers);

        User user = users.get(i);
        txtname.setText(user.getName());
        return view;
    }
}
