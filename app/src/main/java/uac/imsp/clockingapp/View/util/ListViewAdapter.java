package uac.imsp.clockingapp.View.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uac.imsp.clockingapp.Controller.util.Result;
import uac.imsp.clockingapp.R;

public class ListViewAdapter  extends BaseAdapter {

    private final List<Result> listData;
    private ArrayList <Result> arrayList;
    private final LayoutInflater layoutInflater;
    private final Context context;


    public ListViewAdapter(Context context,  List<Result> listData) {
        this.context = context;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
        this.arrayList=new ArrayList<>();
        this.arrayList.addAll(listData);
    }
    public static class ViewHolder {
        TextView numberView;
        TextView nameView;
        TextView serviceView;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        Result result = this.listData.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.result_item_layout, null);


            holder.numberView = convertView.findViewById(R.id.search_number);
            holder.nameView = convertView.findViewById(R.id.search_name);
            holder.serviceView = convertView.findViewById(R.id.search_service);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }



        holder.numberView.setText(result.getNumber());
        holder.nameView.setText(result.getName());
        holder.serviceView.setText(result.getService());
        return convertView;

    }


}