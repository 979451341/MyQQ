package android.com.myqq.adapter;

import android.app.Activity;
import android.com.myqq.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */
public class NavAdapter extends BaseAdapter {


    private Activity activity;
    private List<String> data;
    private static LayoutInflater inflater=null;

    public NavAdapter(Activity a, List<String> d){
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        //如果缓存convertView为空，则需要创建View
        if(convertView == null)
        {
            holder = new ViewHolder();
            //根据自定义的Item布局加载布局
            convertView  = inflater.inflate(R.layout.item_nav_list, null);
            holder.iv = (ImageView)convertView.findViewById(R.id.item_nav_iv);
            holder.name = (TextView)convertView.findViewById(R.id.item_nav_tv);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.iv.setBackgroundResource(R.drawable.ic_launcher);
        holder.name.setText(data.get(position));

        return convertView;
    }
    //ViewHolder静态类
    static class ViewHolder
    {
        public ImageView iv;
        public TextView name;
    }


}

