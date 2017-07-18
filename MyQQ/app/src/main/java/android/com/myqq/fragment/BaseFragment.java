package android.com.myqq.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/5/5.
 */
public abstract class BaseFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(getFragment(),container,false);

        initView(view);
        initData(view);
        return view;
    }


    public abstract int getFragment();
    public abstract void initView(View view);
    public abstract void initData(View view);
}
