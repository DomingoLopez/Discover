package com.hdrescuer.supportyourdiscoveries.ui.ui.myplaces.createplace;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.hdrescuer.supportyourdiscoveries.BuildConfig;
import com.hdrescuer.supportyourdiscoveries.R;
import com.hdrescuer.supportyourdiscoveries.common.Constants;
import com.hdrescuer.supportyourdiscoveries.common.MyApp;
import com.hdrescuer.supportyourdiscoveries.data.MyPlacesListViewModel;
import com.hdrescuer.supportyourdiscoveries.data.dbrepositories.PlaceRepository;
import com.hdrescuer.supportyourdiscoveries.db.entity.AddressShort;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;
import com.schibstedspain.leku.LocationPicker;
import com.schibstedspain.leku.LocationPickerActivity;
import com.schibstedspain.leku.locale.SearchZoneRect;


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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


public class NewPlaceDialogFragment extends DialogFragment implements View.OnClickListener, OnMapReadyCallback {

    ImageView img_place;
    ImageView btnAddPlace;
    ImageView btnClose;

    EditText title;
    EditText description;


    TextView tvaddress;

    Button add_images;
    Button btn_geolocalizar;

    //ViewPager
    ViewPager2 viewPager;
    //Boton eliminar foto
    ImageView btn_delete_photo;

    //Array de paths de las imágenes
    ArrayList<String> img_paths;
    String currentPhotoPath;

    //Array de direcciones
    ArrayList<AddressShort> address_paths;

    //ViewModel
    MyPlacesListViewModel myPlacesListViewModel;

    //Place id
    Integer place_id;

    PlaceEntity placeEntity_to_update;
    int address_position_to_update;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private final int PICK_IMAGE = 100;

    private final int PICK_PLACE_CODE = 245;

    //Parámetros para Maps
    FusedLocationProviderClient fusedLocationProviderClient;


    public NewPlaceDialogFragment() {


    }

    public NewPlaceDialogFragment(int place_id) {

        this.place_id = place_id;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_SupportYourDiscoveries_FullScreenDialogStyle);

        this.img_paths = new ArrayList<>();
        this.address_paths = new ArrayList<>();

        this.myPlacesListViewModel = new ViewModelProvider(requireActivity()).get(MyPlacesListViewModel.class);

        //Inicializamos el fusedLocationClient
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_new_place_dialog, container, false);

        findViews(view);

        if (this.place_id != null) {
            setFormValues();
        }


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (this.img_paths.size() == 0)
            this.btn_delete_photo.setVisibility(View.INVISIBLE);
        else
            this.btn_delete_photo.setVisibility(View.VISIBLE);

        this.viewPager.setAdapter(new ScreenSlidePagerAdapter(this.getActivity(), this.img_paths, this.address_paths));


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
        this.placeEntity_to_update = this.myPlacesListViewModel.getPlaceDetails(this.place_id);
        this.title.setText(placeEntity_to_update.title);
        this.img_paths = placeEntity_to_update.getPhoto_paths();
        this.address_paths = placeEntity_to_update.getAddress_paths();
        this.description.setText(placeEntity_to_update.description);
        this.address_paths = placeEntity_to_update.getAddress_paths();

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_add_images:


                //Dialog para elegir entre cámara o galería
                AlertDialog.Builder builder_chooser = new AlertDialog.Builder(this.getActivity());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View viewDialog = inflater.inflate(R.layout.choose_gallery_photo, null);
                builder_chooser.setView(viewDialog);
                builder_chooser.setMessage("¿Añadir desde galería o tomar instantánea?");

                AlertDialog dialog_chooser = builder_chooser.create();

                ImageView camera = viewDialog.findViewById(R.id.btn_camera_icon);
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_chooser.dismiss();
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
                                    BuildConfig.APPLICATION_ID + ".provider",
                                    photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }


                    }
                });

                ImageView gallery = viewDialog.findViewById(R.id.btn_gallery_icon);
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_chooser.dismiss();

                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(gallery, PICK_IMAGE);

                    }
                });

                dialog_chooser.show();


                break;

            case R.id.btnClose:

                this.btn_delete_photo.setVisibility(View.INVISIBLE);
                getDialog().dismiss();

                break;

            case R.id.btn_delete_photo:

                AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());

                builder.setMessage("¿Desea elimininar la instantánea?");

                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        int position = viewPager.getCurrentItem();
                        img_paths.remove(position);
                        address_paths.remove(position);

                        if (img_paths.size() == 0)
                            btn_delete_photo.setVisibility(View.INVISIBLE);

                        viewPager.setAdapter(new ScreenSlidePagerAdapter(getActivity(), img_paths, address_paths));
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });


                AlertDialog dialog = builder.create();
                dialog.show();

                break;

            case R.id.btnAddPlace:


                if (validateFields()) {
                    savePlaceToDB();
                    getDialog().dismiss();
                }

                break;

            case R.id.btn_geolocalizar:

                if(address_paths.isEmpty()){
                    Toast.makeText(requireActivity(), "Antes de modificar una dirección debe tomar una foto del sitio", Toast.LENGTH_SHORT).show();
                }else{
                    this.address_position_to_update = viewPager.getCurrentItem();
                    AddressShort addressShort = this.address_paths.get(address_position_to_update);

                    Intent locationPickerIntent = new LocationPickerActivity.Builder()
                            .withLocation(addressShort.getLatitud(), addressShort.getLongitud())
                            .withGeolocApiKey("${MAPS_API_KEY}")
                            .withSearchZone("es_ES")
                            .withSearchZone(new SearchZoneRect(new LatLng(addressShort.getLatitud()-15, addressShort.getLongitud()-15),
                                                               new LatLng(addressShort.getLatitud()+5, addressShort.getLongitud()+5)))
                            .withDefaultLocaleSearchZone()
                            .shouldReturnOkOnBackPressed()
                            //.withStreetHidden()
                            //.withCityHidden()
                            //.withZipCodeHidden()
                            .withSatelliteViewHidden()
                            .withGoogleTimeZoneEnabled()
                            .withVoiceSearchHidden()
                            .withUnnamedRoadHidden()
                            .build(requireActivity());

                    startActivityForResult(locationPickerIntent, PICK_PLACE_CODE);
                }




                    break;

        }

    }





    private void savePlaceToDB() {

        if(this.place_id != null){ //Edit

            PlaceEntity placeEntity = new PlaceEntity(
                    this.place_id,
                    this.title.getText().toString(),
                    this.description.getText().toString(),
                    this.placeEntity_to_update.getRating(),
                    Constants.USERNAME,
                    this.img_paths,
                    this.address_paths,
                    Clock.systemUTC().instant().toString()
                    );

            this.myPlacesListViewModel.updatePlace(placeEntity);

        }else{

            int id = this.myPlacesListViewModel.getMaxId();

            this.myPlacesListViewModel.insertPlace(new PlaceEntity(
                    id+1,
                    this.title.getText().toString(),
                    this.description.getText().toString(),
                    0.0f,
                    Constants.USERNAME,
                    this.img_paths,
                    this.address_paths,
                    Clock.systemUTC().instant().toString()
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



                geolocaLiza();



            }
        }else if (requestCode == PICK_IMAGE){
            if(resultCode == RESULT_OK ){
                if(this.btn_delete_photo.getVisibility() == View.INVISIBLE)
                    this.btn_delete_photo.setVisibility(View.VISIBLE);

/*
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


                }
*/
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = requireActivity().getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();


                this.img_paths.add(picturePath);

                geolocaLiza();

            }

        }else if(requestCode == PICK_PLACE_CODE){
            if(resultCode == RESULT_OK ) {
                double latitude = data.getDoubleExtra("latitude", 0.0);
                double longitude = data.getDoubleExtra("longitude", 0.0);
                String address = data.getStringExtra("location_address");

                String parts[] = address.split(",");
                String result_address = "";

                for (int i = 0; i < parts.length; i++) {
                    if (i == parts.length - 2) {
                        result_address += parts[i];
                    } else if (i != parts.length - 1) {
                        result_address += parts[i];
                        result_address += ", ";
                    }

                }

                this.address_paths.set(this.address_position_to_update, new AddressShort(result_address, latitude, longitude));
                this.viewPager.setAdapter(new ScreenSlidePagerAdapter(getActivity(), img_paths, address_paths));

            }
        }
    }


    void geolocaLiza(){
        if(ActivityCompat.checkSelfPermission(this.getActivity().getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    //Inicializamos location
                    Location location = task.getResult();

                    if(location != null){
                        try {
                            //Inicializamos Geocoder
                            Geocoder geocoder = new Geocoder(requireActivity(), Locale.getDefault());

                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                            address_paths.add(new AddressShort(addresses.get(0).getAddressLine(0),addresses.get(0).getLatitude(),addresses.get(0).getLongitude()));

                            viewPager.setAdapter(new ScreenSlidePagerAdapter(getActivity(),img_paths,address_paths));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }else{
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
        }
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }


    private class ScreenSlidePagerAdapter extends FragmentStateAdapter {

        int num_img;
        ArrayList<String> paths_to_img;
        ArrayList<AddressShort> paths_to_address;

        public ScreenSlidePagerAdapter(FragmentActivity fa, ArrayList<String> img_paths, ArrayList<AddressShort> address_paths) {
            super(fa);
            this.paths_to_img = new ArrayList<>();
            this.paths_to_address = new ArrayList<>();
            if(img_paths.size() != 0) {
                this.num_img = img_paths.size();
            }else{
                this.num_img = 1;
            }

            this.paths_to_img = img_paths;
            this.paths_to_address = address_paths;

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
                address = this.paths_to_address.get(position).getAddress();
            }

            return new ScreenSlidePageFragment(path,address);
        }

        @Override
        public int getItemCount() {
            return num_img;
        }
    }




    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


    }


}