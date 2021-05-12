package com.hdrescuer.supportyourdiscoveries.ui.ui.myplaces;

import android.content.Intent;
import android.graphics.Bitmap;
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
import com.hdrescuer.supportyourdiscoveries.BuildConfig;
import com.hdrescuer.supportyourdiscoveries.R;
import com.hdrescuer.supportyourdiscoveries.common.MyApp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;


public class NewPlaceDialogFragment extends DialogFragment implements View.OnClickListener {

    ImageView img_place;
    ImageView btnAddPlace;
    ImageView btnClose;

    EditText title;
    EditText description;

    Bitmap tmp;

    String currentPhotoPath;




    static final int REQUEST_IMAGE_CAPTURE = 1;


    public NewPlaceDialogFragment(){


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_SupportYourDiscoveries_FullScreenDialogStyle);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_new_place_dialog,container, false);

        findViews(view);

        setFormValues();

        return view;
    }


    private void findViews(View view) {

        this.img_place = view.findViewById(R.id.img_place);
        this.img_place.setOnClickListener(this);

        this.btnAddPlace = view.findViewById(R.id.btnAddPlace);
        this.btnAddPlace.setOnClickListener(this);

        this.btnClose = view.findViewById(R.id.btnClose);
        this.btnClose.setOnClickListener(this);

        this.title = view.findViewById(R.id.new_place_title);
        this.description = view.findViewById(R.id.new_place_description);
    }

    /**
     * Método que setea los elementos del formulario del diálogo
     * @author Domingo Lopez
     */
    private void setFormValues() {

        Glide.with(this)
                .load(R.mipmap.img_no_img_foreground)
                .centerCrop()
                .into(this.img_place);
    }



    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.img_place:

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

                getDialog().dismiss();

                break;

            case R.id.btnAddPlace:

                File img = null;

                if(validateFields()) {
                   galleryAddPic();

                    if(img != null)
                        Log.i("PATH",img.getAbsolutePath());

                }

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
        else if(this.img_place.getTag().equals("empty"))
            Toast.makeText(this.requireActivity(), "Debes añadir una foto al lugar", Toast.LENGTH_SHORT).show();
        else
            valido = true;

        return valido;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CAPTURE){
            if(resultCode == RESULT_OK){

                //Obtenemos la foto realizada
                /*Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                this.tmp = imageBitmap;


                this.img_place.setTag("img_añadida");*/

                File file = new File(this.currentPhotoPath);

                Glide.with(this)
                        .load(this.currentPhotoPath)
                        .centerCrop()
                        .into(this.img_place);
            }
        }
    }
}