package com.gordarg.messageforwarder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gordarg.messageforwarder.data.DBHelper;
import com.gordarg.messageforwarder.model.Forwarder;

import java.util.ArrayList;

public class MainActivityAdapter extends android.widget.BaseAdapter {

    ArrayList<Forwarder> items;
    Context context;
    private LayoutInflater layoutInflater;

    public MainActivityAdapter(Context context, ArrayList<Forwarder> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return (items== null) ? 0 :
                items.size();
    }

    @Override
    public Forwarder getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

//        if (layoutInflater == null)
//            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.simple_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Forwarder item = getItem(position);

        viewHolder.tvFrom.setText(item.getFrom());
        viewHolder.tvTo.setText(item.getTo());
        viewHolder.btnRemoveItem.setOnLongClickListener(v -> {
            DBHelper mydb = new DBHelper(context);
            mydb.deleteForwarder(item.getId());
            Toast.makeText(context, "آیتم حذف شد", Toast.LENGTH_LONG).show();
            items.remove(position);
            this.notifyDataSetChanged();
            return true;
        });

        return convertView;
    }
}

class ViewHolder{
    TextView tvFrom ;
    TextView tvTo ;
    Button btnRemoveItem;

    public ViewHolder(View view){
        tvFrom = (TextView)view.findViewById(R.id.tvFrom);
        tvTo = (TextView)view.findViewById(R.id.tvTo);
        btnRemoveItem = (Button)view.findViewById(R.id.btnRemoveItem);
    }
}
