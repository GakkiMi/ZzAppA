package com.huadingcloudpackage.www.adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> listFragment;                         //fragment列表
    private String[] listTitle;                              //tab名的列表
    private FragmentManager fm;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> listFragment, String[] listTitle) {
        super(fm);
        this.fm = fm;
        this.listFragment = listFragment;
        this.listTitle = listTitle;
    }


    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listTitle.length;
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //   super.destroyItem(container, position, object);
    }


    //刷新方法
    public void setFragments(List<Fragment> fragments, String[] listTitle) {
        if (this.listFragment != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.listFragment) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        this.listFragment = fragments;
        this.listTitle = listTitle;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

}
