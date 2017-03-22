package com.tradeitsignals.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.tradeitsignals.R;

/**
 * Created by ThorneBird on 2/24/2016.
 */
public class MenuListAdapter extends ArrayAdapter<String> {

    private Context context;
    private String  [] list;

    public MenuListAdapter(Context context, int resource,String [] list) {
        super(context, resource);
        this.context=context;
        this.list=list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



          LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          convertView=inflater.inflate(R.layout.item_menu_list,null);

          TextView tv=(TextView)convertView.findViewById(R.id.cb);

          tv.setText(list[position]);

       return convertView;
    }


    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public String getItem(int position) {
        return list[position];
    }

}
