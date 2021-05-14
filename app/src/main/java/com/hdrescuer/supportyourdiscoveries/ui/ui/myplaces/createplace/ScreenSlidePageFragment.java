package com.hdrescuer.supportyourdiscoveries.ui.ui.myplaces.createplace;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hdrescuer.supportyourdiscoveries.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScreenSlidePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScreenSlidePageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private String img_path;

    public ScreenSlidePageFragment() {
        // Required empty public constructor
    }

    public ScreenSlidePageFragment(String img_path) {
        this.img_path = img_path;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScreenSlidePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScreenSlidePageFragment newInstance(String param1, String param2) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewgroup = (ViewGroup) inflater.inflate(R.layout.fragment_img_gallery,container, false);

        ImageView img = viewgroup.findViewById(R.id.img_viewpager);

        if(this.img_path.equals("default")){
            Glide.with(this)
                    .load(R.mipmap.img_no_img_foreground)
                    .fitCenter()
                    .into(img);
        }else{
            Glide.with(this)
                    .load(this.img_path)
                    .fitCenter()
                    .into(img);

        }


        return viewgroup;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


    }

}