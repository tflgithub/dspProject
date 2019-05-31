package com.luzuzu.main.adapter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import com.luzuzu.main.model.MainPageItem;
import java.util.ArrayList;

/**
 * Created by fula on 2019/5/19.
 */

public class MainViewPagerAdapter extends PagerAdapter{

    private ArrayList<MainPageItem> items = null;

    public MainViewPagerAdapter(ArrayList<MainPageItem> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = items.get(position).view;
        view.setTag(position);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        View view = (View)object;
        container.removeView(view);
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return items.get(position).title;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        View view=(View)object;
        return  (int)view.getTag();
    }
}
