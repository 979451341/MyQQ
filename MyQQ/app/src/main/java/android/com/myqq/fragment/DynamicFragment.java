package android.com.myqq.fragment;

import android.com.myqq.R;
import android.com.myqq.activity.FriendActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/5/5.
 */
public class DynamicFragment extends BaseFragment {

    ImageView iv;
    @Override
    public int getFragment() {
        return R.layout.fragment_dynamic;
    }

    @Override
    public void initView(View view) {
        iv = (ImageView)view.findViewById(R.id.dynamic_friend_iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friend();
            }
        });
    }

    @Override
    public void initData(View view) {
    }

    private void friend(){

        Intent intent = new Intent(getActivity(), FriendActivity.class);
        startActivity(intent);
    }

}
