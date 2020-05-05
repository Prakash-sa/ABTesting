package com.company.ab.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StoreImageHelper {

    private String currentPhotoPath;

    public String storeImage(final Context context, Bitmap image) throws IOException {

        File pictureFile = createImageFile2(context);
        if (pictureFile == null) {
            Log.i("Error", "Error creating media file, check storage permissions: ");
            return "";
        }
        try {
            OutputStream stream = null;

            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);


            Log.i("FileSaveAt",pictureFile.toString());
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            Log.i("Error", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.i("Error", "Error accessing file: " + e.getMessage());
        }
        return pictureFile.toString();
    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PNG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        File image=null;
        try {
            image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".png",         /* suffix */
                    storageDir      /* directory */
            );
            String cameraFilePath = "file://" + image.getAbsolutePath();
            Log.i("Camera path:- ",cameraFilePath);
            return image;
        } catch (IOException e){
            e.printStackTrace();
        }

        return null;
    }



    private File createImageFile2(Context context) throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpeg",
                storageDir
        );
        String currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
