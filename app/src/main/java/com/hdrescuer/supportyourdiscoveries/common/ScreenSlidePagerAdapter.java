package com.hdrescuer.supportyourdiscoveries.common;

import android.location.Address;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hdrescuer.supportyourdiscoveries.db.entity.AddressShort;
import com.hdrescuer.supportyourdiscoveries.ui.ui.myplaces.createplace.ScreenSlidePageFragment;

import java.util.ArrayList;

 public class ScreenSlidePagerAdapter extends FragmentStateAdapter {

    int num_img;
    ArrayList<String> paths_to_img;
    ArrayList<AddressShort> address_paths;

    public ScreenSlidePagerAdapter(FragmentActivity fa, ArrayList<String> img_paths, ArrayList<AddressShort> address_paths) {
        super(fa);
        this.paths_to_img = new ArrayList<>();
        this.address_paths = new ArrayList<>();
        if(img_paths.size() != 0) {
            this.num_img = img_paths.size();
        }else{
            this.num_img = 1;
        }

        this.paths_to_img = img_paths;
        this.address_paths = address_paths;

    }

    @Override
    public Fragment createFragment(int position) {

        String path = "";
        String address = "";
        if(this.paths_to_img.size() == 0){
            path = "default";
            address = "";
        }else{
            path = this.paths_to_img.get(position);
            address = this.address_paths.get(position).getAddress();
        }

        return new ScreenSlidePageFragment(path, address);
    }

    @Override
    public int getItemCount() {
        return num_img;
    }
}
