package com.hdrescuer.supportyourdiscoveries.ui.ui.places;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hdrescuer.supportyourdiscoveries.R;
import com.hdrescuer.supportyourdiscoveries.common.ListItemClickListener;
import com.hdrescuer.supportyourdiscoveries.common.MyApp;
import com.hdrescuer.supportyourdiscoveries.data.MyPlacesListViewModel;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;
import com.hdrescuer.supportyourdiscoveries.ui.ui.placedetails.PlaceDetailsActivity;

import java.util.List;


public class LatestPlacesListFragment extends Fragment implements ListItemClickListener, View.OnClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    RecyclerView recyclerView;
    LatestPlacesRecyclerView adapter;
    List<PlaceEntity> placeList;
    MyPlacesListViewModel latestPlacesListViewModel;

    boolean alreadyCreated = false;


    public LatestPlacesListFragment() {
    }


    @SuppressWarnings("unused")
    public static LatestPlacesListFragment newInstance(int columnCount) {
        LatestPlacesListFragment fragment = new LatestPlacesListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }


        this.latestPlacesListViewModel = new ViewModelProvider(getActivity()).get(MyPlacesListViewModel.class);
        alreadyCreated = true;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_latest_places, container, false);
        Context context = view.getContext();
        this.recyclerView = view.findViewById(R.id.list);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));

        this.adapter = new LatestPlacesRecyclerView(
                getActivity(),
                this.getActivity(),
                this.placeList,
                this
        );
        this.recyclerView.setAdapter(adapter);


        findViews(view);
        loadUserData();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        placeList = this.latestPlacesListViewModel.getAllPlaces().getValue();
        adapter.setData(placeList);
    }



    private void findViews(View view) {

    }



    private void loadUserData() {

        this.latestPlacesListViewModel.getAllPlaces().observe(requireActivity(), new Observer<List<PlaceEntity>>() {
            @Override
            public void onChanged(List<PlaceEntity> places) {

                placeList = places;
                adapter.setData(placeList);
            }
        });

    }


    @Override
    public void onListItemClick(int position,String action) {



            int id_place = this.placeList.get(position).getId();

            Intent i = new Intent(this.getContext(), PlaceDetailsActivity.class);
            i.putExtra("id",id_place);
            startActivity(i);


    }



    @Override
    public void onClick(View view) {




    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


    }

}