package com.hdrescuer.supportyourdiscoveries.common;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.hdrescuer.supportyourdiscoveries.ui.ui.myplaces.createplace.ScreenSlidePageFragment;

import java.util.ArrayList;

 public class ScreenSlidePagerAdapter extends FragmentStateAdapter {

    int num_img;
    ArrayList<String> paths_to_img;

    public ScreenSlidePagerAdapter(FragmentActivity fa, ArrayList<String> img_paths) {
        super(fa);
        this.paths_to_img = new ArrayList<>();
        if(img_paths.size() != 0) {
            this.num_img = img_paths.size();
        }else{
            this.num_img = 1;
        }

        this.paths_to_img = img_paths;

    }

    @Override
    public Fragment createFragment(int position) {

        String path = "";
        if(this.paths_to_img.size() == 0){
            path = "default";
        }else{
            path = this.paths_to_img.get(position);
        }

        return new ScreenSlidePageFragment(path);
    }

    @Override
    public int getItemCount() {
        return num_img;
    }
}
