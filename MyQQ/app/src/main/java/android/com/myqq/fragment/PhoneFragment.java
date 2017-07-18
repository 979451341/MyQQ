package android.com.myqq.fragment;

import android.com.myqq.R;
import android.com.myqq.adapter.PhoneAdapter;
import android.com.myqq.been.Music;
import android.com.myqq.constant.DBConstant;
import android.com.myqq.db.MusicDB;
import android.com.myqq.listener.HttpCallbackListener;
import android.com.myqq.util.XMLParser;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/5/4.
 */
public class PhoneFragment extends BaseFragment {

    ListView listv;
    private MusicDB coolWeatherDB;
    PhoneAdapter phoneAdapter;

    XMLParser parser;
    String url;

    boolean success;
    ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    List<Music> musicList = new ArrayList<Music>();
    @Override
    public int getFragment() {
        return R.layout.fragment_phone;
    }

    @Override
    public void initView(View view) {

        listv = (ListView)view.findViewById(R.id.phone_listv);
    }

    @Override
    public void initData(View view) {
        coolWeatherDB = MusicDB.getInstance(getActivity());
        musicList = coolWeatherDB.loadProvinces();

        if(musicList.size()>0){

            listv.setAdapter(new PhoneAdapter(getActivity(),musicList));
        }else {

            parser = new XMLParser();
            parser.getXmlFromUrl(DBConstant.URL, new HttpCallbackListener() {
                @Override
                public void onFinish(final String response) {

                    url = response;
                    success = true;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            parserXML();
                        }
                    });

                }

                @Override
                public void onError(Exception e) {
                    success = false;
                }
            });


        }
    }

    public void parserXML(){
        Document doc = parser.getDomElement(url); // 获取 DOM 节点
        NodeList nl = doc.getElementsByTagName(DBConstant.KEY_SONG);
        // 循环遍历所有的歌节点 <song>
        for (int i = 0; i < nl.getLength(); i++) {
            // 新建一个 HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            Element e = (Element) nl.item(i);
            //每个子节点添加到HashMap关键= >值
            map.put(DBConstant.KEY_ID, parser.getValue(e, DBConstant.KEY_ID));
            map.put(DBConstant.KEY_TITLE, parser.getValue(e, DBConstant.KEY_TITLE));
            map.put(DBConstant.KEY_ARTIST, parser.getValue(e, DBConstant.KEY_ARTIST));
            map.put(DBConstant.KEY_DURATION, parser.getValue(e, DBConstant.KEY_DURATION));
            map.put(DBConstant.KEY_THUMB_URL, parser.getValue(e, DBConstant.KEY_THUMB_URL));

            // HashList添加到数组列表

            songsList.add(map);

        }

        for (int i =0 ;i<songsList.size();i++){
            HashMap<String, String> map = songsList.get(i);
            Music music = new Music();
            music.setTitle(map.get(DBConstant.KEY_TITLE));
            music.setArtist(map.get(DBConstant.KEY_ARTIST));
            music.setDuration(map.get(DBConstant.KEY_DURATION));
            music.setThumb_url(map.get(DBConstant.KEY_THUMB_URL));

            if(!musicList.contains(music))
                coolWeatherDB.saveProvince(music);

            musicList.add(music);


        }

        phoneAdapter = new PhoneAdapter(getActivity(),musicList);
        listv.setAdapter(phoneAdapter);
        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }


}
