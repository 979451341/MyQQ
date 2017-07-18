package android.com.myqq.activity;

import android.com.myqq.R;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/5/30.
 */
public class WalletActivity extends BaseActivity implements Runnable {

    private ViewPager mBanner;
    private BannerAdapter mBannerAdapter;
    private ImageView[] mIndicators;
    private Timer mTimer = new Timer();

    private int mBannerPosition = 0;
    private final int FAKE_BANNER_SIZE = 100;
    private final int DEFAULT_BANNER_SIZE = 5;
    private boolean mIsUserTouched = false;

    private int[] mImagesSrc = {
            R.mipmap.img1,
            R.mipmap.img2,
            R.mipmap.img3,
            R.mipmap.img4,
            R.mipmap.img5
    };

    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            if (!mIsUserTouched) {
                mBannerPosition = (mBannerPosition + 1) % FAKE_BANNER_SIZE;
                runOnUiThread(WalletActivity.this);
            }
        }
    };

    private GridView gridview;

    private TextView back;



    private void setIndicator(int position) {
        position %= DEFAULT_BANNER_SIZE;
        for(ImageView indicator : mIndicators) {
            indicator.setImageResource(R.mipmap.indicator_unchecked);
        }
        mIndicators[position].setImageResource(R.mipmap.indicator_checked);
    }

    @Override
    public void run() {
        if (mBannerPosition == FAKE_BANNER_SIZE - 1) {
            mBanner.setCurrentItem(DEFAULT_BANNER_SIZE - 1, false);
        } else {
            mBanner.setCurrentItem(mBannerPosition);
        }
    }

    @Override
    protected void onDestroy() {
        mTimer.cancel();
        super.onDestroy();
    }

    private class BannerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

        private LayoutInflater mInflater;

        public BannerAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return FAKE_BANNER_SIZE;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= DEFAULT_BANNER_SIZE;
            View view = mInflater.inflate(R.layout.item_wallet_pager, container, false);
            ImageView imageView = (ImageView) view.findViewById(R.id.image);
            imageView.setImageResource(mImagesSrc[position]);
            final int pos = position;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  //  Toast.makeText(WalletActivity.this, "click banner item :" + pos, Toast.LENGTH_SHORT).show();
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            int position = mBanner.getCurrentItem();
            if (position == 0) {
                position = DEFAULT_BANNER_SIZE;
                mBanner.setCurrentItem(position, false);
            } else if (position == FAKE_BANNER_SIZE - 1) {
                position = DEFAULT_BANNER_SIZE - 1;
                mBanner.setCurrentItem(position, false);
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            mBannerPosition = position;
            setIndicator(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }




    @Override
    public int putView() {
        return R.layout.activity_wallet;
    }

    @Override
    public void initView() {
        gridview = (GridView) findViewById(R.id.wallet_gridview);

        mBanner = (ViewPager) findViewById(R.id.banner);

        back = (TextView)findViewById(R.id.wallet_tv_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WalletActivity.this.finish();
            }
        });

    }

    @Override
    public void initData() {
        mIndicators = new ImageView[] {
                (ImageView)findViewById(R.id.indicator1),
                (ImageView)findViewById(R.id.indicator2),
                (ImageView)findViewById(R.id.indicator3),
                (ImageView)findViewById(R.id.indicator4),
                (ImageView)findViewById(R.id.indicator5)
        };
        mBannerAdapter = new BannerAdapter(this);
        mBanner.setAdapter(mBannerAdapter);
        mBanner.setOnPageChangeListener(mBannerAdapter);
        mBanner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN
                        || action == MotionEvent.ACTION_MOVE) {
                    mIsUserTouched = true;
                } else if (action == MotionEvent.ACTION_UP) {
                    mIsUserTouched = false;
                }
                return false;
            }
        });
        mTimer.schedule(mTimerTask, 5000, 5000);

        // 生成动态数组，并且转入数据
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < 9; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", R.mipmap.ic_launcher);// 添加图像资源的ID
            map.put("ItemText", "NO." + String.valueOf(i));// 按序号做ItemText
            lstImageItem.add(map);
        }
        // 生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
        SimpleAdapter saImageItems = new SimpleAdapter(this, // 没什么解释
                lstImageItem,// 数据来源
                R.layout.item_wallet_gridview,// night_item的XML实现
                // 动态数组与ImageItem对应的子项
                new String[] { "ItemImage", "ItemText" },
                // ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[] { R.id.ItemImage, R.id.ItemText });
        // 添加并且显示
        gridview.setAdapter(saImageItems);
    }
}
