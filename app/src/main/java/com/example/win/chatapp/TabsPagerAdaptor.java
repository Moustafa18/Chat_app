package com.example.win.chatapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


class TabsPagerAdaptor extends FragmentPagerAdapter{


    public TabsPagerAdaptor(FragmentManager f)
    {
        super(f);
    }

    @Override
    public Fragment getItem(int i) {
        switch(i)
        {
            case 0:
                RequestsFragment r = new RequestsFragment();
                return  r;
            case 1:
                ChatsFragment c = new ChatsFragment();
                return c;
            case 2:
                FriendsFragment f = new FriendsFragment();
                return f;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position)
        {
            case 0:
                return "Requests";
            case 1:
                return "Chats";
            case 2:
                return "Friends";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
