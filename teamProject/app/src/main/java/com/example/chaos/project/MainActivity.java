package com.example.chaos.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {
    //private DBManager mgr;
    private ViewPager view_pager;
    private LinearLayout dotGroup;
    private TextView titles;
    private int imgIds[] = new int[]{R.drawable.a, R.drawable.b, R.drawable.c}; // stores images
    private String textview[] = new String[]{"", "", ""}; // stores titles of the images
    private int curIndex = 0; // records the current index of the images
    ImageAdapter imgAdapter; // ImageAdapter is a class
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mgr = new DBManager(this);
        setViewPager();
    }

    public void changeToMenu(View v){
        Intent disIntent=new Intent(this,Resturant.class);
        startActivity(disIntent);
    }

    private void setViewPager(){
        titles = (TextView)findViewById(R.id.title);
        view_pager = (ViewPager)findViewById(R.id.view_pager);
        dotGroup = (LinearLayout)findViewById(R.id.dotgroup);

        imgAdapter = new ImageAdapter(); // create adapter
        imgAdapter.setData(imgIds);
        view_pager.setAdapter(imgAdapter);

        view_pager.addOnPageChangeListener(new MyPageChangeListener()); //设置页面切换监听器

        initPoints(imgIds.length); // initialize the dots to match the number of pictures
        startAutoScroll();
    }

    // initialize
    private void initPoints(int count){
        for(int i=0; i<count; i++){
            ImageView img = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.setMargins(0, 0, 20, 0);
            img.setLayoutParams(params);

            img.setImageResource(R.drawable.dot);

            dotGroup.addView(img);
        }

        ((ImageView) dotGroup.getChildAt(curIndex)).setImageResource(R.drawable.dot2);

        titles.setText(textview[curIndex]); // 当前图片名
    }

    // auto scroll
    private void startAutoScroll(){
        ScheduledExecutorService scheduledExecutorService =
                Executors.newSingleThreadScheduledExecutor();

        // 每隔4秒钟切换一张图片
        scheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 5,
                4, TimeUnit.SECONDS);
    }

    // switch images
    private class ViewPagerTask implements Runnable{
        @Override
        public void run(){
            runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    int count = imgAdapter.getCount();
                    view_pager.setCurrentItem((curIndex + 1) % count);
                }
            });
        }
    }

    class MyPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels){

        }

        @Override
        public void onPageSelected(int position){
            ImageView imageView1 = (ImageView) dotGroup.getChildAt(position);
            ImageView imageView2 = (ImageView) dotGroup.getChildAt(curIndex);

            if(imageView1 != null){
                imageView1.setImageResource(R.drawable.dot2);
            }

            if(imageView2 != null){
                imageView2.setImageResource(R.drawable.dot);
            }

            curIndex = position;
            titles.setText(textview[curIndex]);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //这段代码可不加，主要功能是实现切换到末尾后返回到第一张
            /* switch (state) {
                case 1:// 手势滑动
                    b = false;
                    break;
                case 2:// 界面切换中
                    b = true;
                    break;
                case 0:// 滑动结束，即切换完毕或者加载完毕
                    // 当前为最后一张，此时从右向左滑，则切换到第一张
                    if (view_pager.getCurrentItem() == view_pager.getAdapter()
                            .getCount() - 1 && !b) {
                        view_pager.setCurrentItem(0);
                    }
                    // 当前为第一张，此时从左向右滑，则切换到最后一张
                    else if (view_pager.getCurrentItem() == 0 && !b) {
                        view_pager.setCurrentItem(view_pager.getAdapter()
                                .getCount() - 1);
                    }
                    break;

                default:
                    break;
            } */
        }
    }

    class ImageAdapter extends PagerAdapter {
        private List<ImageView> views = new ArrayList<ImageView>();

        @Override
        public int getCount(){
            if(views == null){
                return 0;
            }

            return views.size();
        }

        public void setData(int[] imgIds){
            for(int i=0; i<imgIds.length; i++){
                ImageView img = new ImageView(MainActivity.this);

                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                );

                img.setLayoutParams(params);
                img.setScaleType(ImageView.ScaleType.FIT_XY);

                img.setImageResource(imgIds[i]);
                views.add(img);
            }
        }

        public Object getItem(int position){
            if(position<getCount()){
                return views.get(position);
            }
            return null;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1){
            return arg0 == arg1;
        }


        public void destroyItem(View container, int position, Object object) {

            if (position < views.size())
                ((ViewPager) container).removeView(views.get(position));
        }

        @Override
        public int getItemPosition(Object object) {
            return views.indexOf(object);
        }

        @Override
        public Object instantiateItem(View container, int position) {
            if (position < views.size()) {
                final ImageView imageView = views.get(position);
                ((ViewPager) container).addView(imageView);
                return views.get(position);
            }
            return null;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

