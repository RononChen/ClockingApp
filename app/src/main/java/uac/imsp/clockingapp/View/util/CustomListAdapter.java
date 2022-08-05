package uac.imsp.clockingapp.View.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import uac.imsp.clockingapp.Controller.util.Result;

public class CustomListAdapter  extends BaseAdapter {

    private final List<Result> listData;
    private final LayoutInflater layoutInflater;
    private final Context context;

    public CustomListAdapter(Context context,  List<Result> listData) {
        this.context = context;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
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
            convertView = layoutInflater.inflate(holder.listItemId, null);

            holder.numberView = convertView.findViewById(holder.numberId);
            holder.nameView = convertView.findViewById(holder.nameId);
            holder.serviceView = convertView.findViewById(holder.serviceId);

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