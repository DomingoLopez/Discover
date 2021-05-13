package com.hdrescuer.supportyourdiscoveries.ui.ui.myplaces.createplace;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hdrescuer.supportyourdiscoveries.BuildConfig;
import com.hdrescuer.supportyourdiscoveries.R;
import com.hdrescuer.supportyourdiscoveries.common.Constants;
import com.hdrescuer.supportyourdiscoveries.common.MyApp;
import com.hdrescuer.supportyourdiscoveries.data.MyPlacesListViewModel;
import com.hdrescuer.supportyourdiscoveries.data.dbrepositories.PlaceRepository;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class NewPlaceDialogFragment extends DialogFragment implements View.OnClickListener {

    ImageView img_place;
    ImageView btnAddPlace;
    ImageView btnClose;

    EditText title;
    EditText description;

    Button add_images;
    Button btn_geolocalizar;

    //ViewPager
    ViewPager2 viewPager;
    //Boton eliminar foto
    ImageView btn_delete_photo;

    //Array de paths de las imágenes
    ArrayList<String> img_paths;
    String currentPhotoPath;

    //ViewModel
    MyPlacesListViewModel myPlacesListViewModel;

    //Place id
    Integer place_id;


    static final int REQUEST_IMAGE_CAPTURE = 1;


    public NewPlaceDialogFragment(){


    }

    public NewPlaceDialogFragment(int place_id){

        this.place_id = place_id;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_SupportYourDiscoveries_FullScreenDialogStyle);

        this.img_paths = new ArrayList<>();

        this.myPlacesListViewModel = new ViewModelProvider(requireActivity()).get(MyPlacesListViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_new_place_dialog,container, false);

        findViews(view);

        if(this.place_id != null){
            setFormValues();
        }


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(this.img_paths.size() == 0)
            this.btn_delete_photo.setVisibility(View.INVISIBLE);
        else
            this.btn_delete_photo.setVisibility(View.VISIBLE);

        this.viewPager.setAdapter(new ScreenSlidePagerAdapter(this.getActivity(),this.img_paths));


    }

    private void findViews(View view) {

        //ViewPager
        this.viewPager = view.findViewById(R.id.viewpager_img_gallery);


        this.btn_delete_photo = view.findViewById(R.id.btn_delete_photo);
        this.btn_delete_photo.setOnClickListener(this);


        this.btn_geolocalizar = view.findViewById(R.id.btn_geolocalizar);
        this.btn_geolocalizar.setOnClickListener(this);



        this.add_images = view.findViewById(R.id.btn_add_images);
        this.add_images.setOnClickListener(this);

        this.btnAddPlace = view.findViewById(R.id.btnAddPlace);
        this.btnAddPlace.setOnClickListener(this);

        this.btnClose = view.findViewById(R.id.btnClose);
        this.btnClose.setOnClickListener(this);



        this.title = view.findViewById(R.id.new_place_title);
        this.description = view.findViewById(R.id.new_place_description);
    }


    private void setFormValues() {

        //Obtenemos los datos del lugar
        PlaceEntity placeEntity = this.myPlacesListViewModel.getPlaceDetails(this.place_id);
        this.title.setText(placeEntity.title);
        this.img_paths = placeEntity.getPhoto_paths();
        this.description.setText(placeEntity.description);

    }



    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_add_images:

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(requireActivity().getApplicationContext(),
                            BuildConfig.APPLICATION_ID+".provider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }


                break;

            case R.id.btnClose:

                this.btn_delete_photo.setVisibility(View.INVISIBLE);
                getDialog().dismiss();

                break;

            case R.id.btn_delete_photo:


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setMessage("¿Desea elimininar la instantánea?");

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        int position =viewPager.getCurrentItem();
                        img_paths.remove(position);

                        if(img_paths.size() == 0)
                            btn_delete_photo.setVisibility(View.INVISIBLE);

                        viewPager.setAdapter(new ScreenSlidePagerAdapter(getActivity(),img_paths));
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });


                AlertDialog dialog = builder.create();
                dialog.show();

            case R.id.btnAddPlace:


                if(validateFields()) {
                    savePlaceToDB();
                }
                getDialog().dismiss();
                break;

        }

    }



    private void savePlaceToDB() {

        if(this.place_id != null){

            PlaceEntity placeEntity = new PlaceEntity(
                    this.title.getText().toString(),
                    this.description.getText().toString(),
                    0.0f,
                    Constants.ID,
                    this.img_paths,
                    Clock.systemUTC().instant().toString(),
                    0.0,
                    0.0,
                    "Sin dirección");
            placeEntity.setId(this.place_id);

            this.myPlacesListViewModel.updatePlace(placeEntity);

        }else{
            this.myPlacesListViewModel.insertPlace(new PlaceEntity(
                    this.title.getText().toString(),
                    this.description.getText().toString(),
                    0.0f,
                    Constants.ID,
                    this.img_paths,
                    Clock.systemUTC().instant().toString(),
                    0.0,
                    0.0,
                    "Sin dirección"
            ));
        }




    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        requireActivity().sendBroadcast(mediaScanIntent);
    }


    private Boolean validateFields() {

        //Diálogo de nuevo usuario

        Boolean valido = false;

        if(this.title.getText().toString().isEmpty())
            this.title.setError("Título requerido");
        else if(this.description.getText().toString().isEmpty())
            this.description.setError("Añade una descripción");
        else if(this.img_paths.size()==0)
            Toast.makeText(this.requireActivity(), "Debes añadir alguna foto del lugar", Toast.LENGTH_SHORT).show();
        else
            valido = true;

        return valido;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(resultCode == RESULT_OK){


                if(this.btn_delete_photo.getVisibility() == View.INVISIBLE)
                    this.btn_delete_photo.setVisibility(View.VISIBLE);

                this.img_paths.add(this.currentPhotoPath);
                this.viewPager.setAdapter(new ScreenSlidePagerAdapter(this.getActivity(),this.img_paths));


            }
        }
    }





    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {

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







}