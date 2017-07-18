package android.com.myqq.adapter;

import android.app.Activity;
import android.com.myqq.R;
import android.com.myqq.been.Music;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by K49 on 2017-05-04.
 */

public class MessageAdapter extends BaseAdapter {



    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.ic_launcher)
            .showImageOnFail(R.drawable.ic_launcher)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    private Activity activity;
    private List<Music> data;
    private static LayoutInflater inflater=null;
    boolean state  =false;

    public MessageAdapter(Activity a, List<Music> d){
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
        return data.get(position);
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
            convertView  = inflater.inflate(R.layout.item_main_content, null);
            holder.iv = (ImageView)convertView.findViewById(R.id.item_message_iv);
            holder.name = (TextView)convertView.findViewById(R.id.item_message_tv_name);
            holder.meesage = (TextView)convertView.findViewById(R.id.item_message_tv_message);
            holder.time = (TextView)convertView.findViewById(R.id.item_message_time);
            //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
            convertView.setTag(holder);
        }else
        {
            holder = (ViewHolder)convertView.getTag();
        }
        Music song;
        song = data.get(position);
        //dispalyImage使用简单，图片大小根据控件大小调控

        holder.name.setText(song.getTitle());
        holder.meesage.setText(song.getArtist());
        holder.time.setText(song.getDuration());
        ImageLoader.getInstance().displayImage(song.getThumb_url(),holder.iv,options);

        return convertView;
    }


    //ViewHolder静态类
    static class ViewHolder
    {
        public ImageView iv;
        public TextView name;
        public TextView meesage;
        public TextView time;
    }


}
