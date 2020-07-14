package br.com.dhsoftware.workerday.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;

public class IntentUtil {

    private Context context;

    public IntentUtil(Context context) {
        this.context = context;
    }

    public void startIntentOpenImage(String uriString) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.parse(uriString);
        intent.setDataAndType(uri, "image/*");
        context.startActivity(intent);
    }

    public Intent startIntentGetImage(File file) {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        //galleryIntent.setType("image/*");
        galleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        galleryIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));


        Intent chooser = Intent.createChooser(galleryIntent, "Selecione a imagem");


        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));//add file ure (photo is saved here)
        Intent[] extraIntents = {cameraIntent};
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);


        return chooser;
    }
}
