package android.com.myqq.adapter;

import android.com.myqq.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/5/3.
 */
public class LoginAdapter extends BaseAdapter {

    private List<String> qqName;
    private Context context;
    public LoginAdapter(Context context,List<String> qqName){
        this.context = context;
        this.qqName = qqName;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return qqName.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return qqName.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int postion, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            // 初始化绑定控件
            convertView = LayoutInflater.from(context).inflate(R.layout.item_login_name, null);
            holder.tv = (TextView) convertView.findViewById(R.id.item_login_name_tv);
            // add to convertView
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv.setText(qqName.get(postion));
        return convertView;
    }
    static class ViewHolder {
        TextView tv;
    }
}