package com.hdrescuer.supportyourdiscoveries.ui.ui.myplaces;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hdrescuer.supportyourdiscoveries.R;
import com.hdrescuer.supportyourdiscoveries.data.LatestPlacesListViewModel;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;
import com.hdrescuer.supportyourdiscoveries.ui.ui.places.LatestPlacesRecyclerView;
import com.hdrescuer.supportyourdiscoveries.common.ListItemClickListener;

import java.util.List;


public class MyPlacesListFragment extends Fragment implements ListItemClickListener, View.OnClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    RecyclerView recyclerView;
    LatestPlacesRecyclerView adapter;
    List<PlaceEntity> placeEntityList;
    LatestPlacesListViewModel latestPlacesListViewModel;

    FloatingActionButton btn;

    boolean alreadyCreated = false;


    public MyPlacesListFragment() {
    }


    @SuppressWarnings("unused")
    public static MyPlacesListFragment newInstance(int columnCount) {
        MyPlacesListFragment fragment = new MyPlacesListFragment();
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


        this.latestPlacesListViewModel = new ViewModelProvider(getActivity()).get(LatestPlacesListViewModel.class);
        alreadyCreated = true;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_my_places, container, false);
        Context context = view.getContext();
        this.recyclerView = view.findViewById(R.id.list_my_places);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));

        this.adapter = new LatestPlacesRecyclerView(
                getActivity(),
                this.placeEntityList,
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
        if(!alreadyCreated){
            refreshUserDetails();
        }
        alreadyCreated = false;
    }


    private void refreshUserDetails() {

    }



    private void findViews(View view) {
        this.btn = view.findViewById(R.id.btn_add_place);
        this.btn.setOnClickListener(this);
    }



    private void loadUserData() {

        /*this.latestPlacesListViewModel.getUsers().observe(requireActivity(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                Log.i("USERS", users.toString());
                userList = users;
                adapter.setData(userList);
            }
        });*/

    }


    @Override
    public void onListItemClick(int position) {

        /*String id = this.placeEntityList.get(position).getId();
        Intent i = new Intent(MyApp.getContext(), PlaceDetailsActivity.class);
        i.putExtra("id", id);
        startActivity(i);*/
    }



    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btn_add_place:
                NewPlaceDialogFragment dialog = new NewPlaceDialogFragment();
                dialog.show(this.getActivity().getSupportFragmentManager(), "NewPlaceFragment");

                break;

        }


    }


}