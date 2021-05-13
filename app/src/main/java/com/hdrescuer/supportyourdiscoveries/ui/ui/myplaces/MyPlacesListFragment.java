package com.hdrescuer.supportyourdiscoveries.ui.ui.myplaces;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hdrescuer.supportyourdiscoveries.R;
import com.hdrescuer.supportyourdiscoveries.data.MyPlacesListViewModel;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;
import com.hdrescuer.supportyourdiscoveries.ui.ui.myplaces.createplace.NewPlaceDialogFragment;
import com.hdrescuer.supportyourdiscoveries.common.ListItemClickListener;

import java.util.List;


public class MyPlacesListFragment extends Fragment implements ListItemClickListener, View.OnClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";

    RecyclerView recyclerView;
    MyPlacesRecyclerView adapter;
    List<PlaceEntity> placeList;
    MyPlacesListViewModel myPlacesListViewModel;

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
            // TODO: Customize parameters
            int mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }


        this.myPlacesListViewModel = new ViewModelProvider(requireActivity()).get(MyPlacesListViewModel.class);
        alreadyCreated = true;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_my_places, container, false);
        Context context = view.getContext();
        this.recyclerView = view.findViewById(R.id.list_my_places);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context));

        this.adapter = new MyPlacesRecyclerView(
                getActivity(),
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
        if(!alreadyCreated){
          
        }
        alreadyCreated = false;
    }






    private void findViews(View view) {
        this.btn = view.findViewById(R.id.btn_add_place);
        this.btn.setOnClickListener(this);
    }



    private void loadUserData() {

        this.myPlacesListViewModel.getPlaces().observe(requireActivity(), new Observer<List<PlaceEntity>>() {
            @Override
            public void onChanged(List<PlaceEntity> places) {
                Log.i("PLACES", places.toString());
                placeList = places;
                adapter.setData(placeList);
            }
        });


    }


    @Override
    public void onListItemClick(int position, String action) {

        switch (action){

            case "EDIT":
                NewPlaceDialogFragment dialog = new NewPlaceDialogFragment(placeList.get(position).getId());
                dialog.show(this.getActivity().getSupportFragmentManager(), "NewPlaceFragment");
                break;

            case "DELETE":
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setMessage("¿Desea elimininar este lugar?");

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        myPlacesListViewModel.deletePlace(placeList.get(position).getId());

                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });


                AlertDialog dialog_delete = builder.create();
                dialog_delete.show();
                break;

            case "SHOW":
                /*int id = this.placeList.get(position).getId();
                Intent i = new Intent(MyApp.getContext(), PlaceDetailsActivity.class);
                i.putExtra("id", id);
                startActivity(i);*/
                break;

        }

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