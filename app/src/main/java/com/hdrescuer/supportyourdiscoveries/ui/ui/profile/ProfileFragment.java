package com.hdrescuer.supportyourdiscoveries.ui.ui.profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Query;

import com.bumptech.glide.Glide;
import com.hdrescuer.supportyourdiscoveries.BuildConfig;
import com.hdrescuer.supportyourdiscoveries.R;
import com.hdrescuer.supportyourdiscoveries.common.Constants;
import com.hdrescuer.supportyourdiscoveries.common.SessionManager;
import com.hdrescuer.supportyourdiscoveries.data.MyPlacesListViewModel;
import com.hdrescuer.supportyourdiscoveries.data.ProfileViewModel;
import com.hdrescuer.supportyourdiscoveries.db.entity.AuthorEntity;
import com.hdrescuer.supportyourdiscoveries.db.entity.PlaceEntity;
import com.hdrescuer.supportyourdiscoveries.ui.HomeActivity;
import com.hdrescuer.supportyourdiscoveries.ui.MainActivity;
import com.hdrescuer.supportyourdiscoveries.ui.RegisterActivity;
import com.hdrescuer.supportyourdiscoveries.ui.ui.myplaces.createplace.NewPlaceDialogFragment;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    ProfileViewModel profileViewModel;

    ImageView profileImage;
    TextView username;
    Button btn_edit_profile;
    Button btn_logout;

    TextView visited;
    TextView published;
    TextView ratingUser;

    String currentPhotoPath;

    private SessionManager session;

    MyPlacesListViewModel latestPlacesListViewModel;

    private static DecimalFormat df2 = new DecimalFormat("#.#");

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private final int PICK_IMAGE = 100;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        session = new SessionManager(requireActivity());
        findViews(view);
        this.latestPlacesListViewModel = new ViewModelProvider(getActivity()).get(MyPlacesListViewModel.class);
        loadUserData();


        return view;
    }



    private void findViews(View view) {

        this.profileImage = view.findViewById(R.id.imageProfile);
        this.username = view.findViewById(R.id.tvUsernameProfile);
        this.btn_edit_profile = view.findViewById(R.id.btn_edit_profile);
        this.btn_logout = view.findViewById(R.id.btn_logout);

        this.visited = view.findViewById(R.id.tvVisited);
        this.published = view.findViewById(R.id.tvPublished);
        this.ratingUser = view.findViewById(R.id.tvRatingUser);

        this.btn_edit_profile.setOnClickListener(this);
        this.btn_logout.setOnClickListener(this);



    }

    private void loadUserData() {
        this.username.setText(Constants.USERNAME);
        this.currentPhotoPath = "";


        Glide.with(this)
                .load(Constants.PHOTO)
                .fitCenter()
                .circleCrop()
                .error(R.mipmap.img_no_img_foreground)
                .into(this.profileImage);
    }

    @Override
    public void onResume() {
        super.onResume();

        this.visited.setText(Integer.toString(this.latestPlacesListViewModel.getNumVisited(Constants.USERNAME)));
        this.ratingUser.setText(df2.format(this.latestPlacesListViewModel.getAVGValorationUser(Constants.USERNAME)));
        this.published.setText(Integer.toString(this.latestPlacesListViewModel.getPublished(Constants.USERNAME)));


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_edit_profile:

                //Dialog para elegir entre cámara o galería
                AlertDialog.Builder builder_chooser = new AlertDialog.Builder(this.getActivity());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View viewDialog = inflater.inflate(R.layout.choose_gallery_photo,null);
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
                                    BuildConfig.APPLICATION_ID+".provider",
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

            case R.id.btn_logout:
                Constants.USERNAME = "";
                Constants.PHOTO = "";
                Constants.ID = 0;
                Constants.EMAIL = "";

                session.setLogin(false);

                Intent i = new Intent(requireActivity(), MainActivity.class);
                startActivity(i);
                requireActivity().finish();

                break;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(resultCode == RESULT_OK){

                Constants.PHOTO = this.currentPhotoPath;
                Glide.with(this)
                        .load(this.currentPhotoPath)
                        .fitCenter()
                        .circleCrop()
                        .error(R.mipmap.img_no_img_foreground)
                        .into(this.profileImage);

                AuthorEntity authorEntity = this.profileViewModel.getAuthorByUsername(Constants.USERNAME);
                authorEntity.setMain_photo_url(this.currentPhotoPath);
                this.profileViewModel.updateAuthor(authorEntity);

            }
        }else if (requestCode == PICK_IMAGE){
            if(resultCode == RESULT_OK ){

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = requireActivity().getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();


                Constants.PHOTO = picturePath;
                Glide.with(this)
                        .load(picturePath)
                        .fitCenter()
                        .circleCrop()
                        .error(R.mipmap.img_no_img_foreground)
                        .into(this.profileImage);

                AuthorEntity authorEntity = this.profileViewModel.getAuthorByUsername(Constants.USERNAME);
                authorEntity.setMain_photo_url(picturePath);
                this.profileViewModel.updateAuthor(authorEntity);

            }

        }
    }



}