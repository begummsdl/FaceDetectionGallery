package com.pena.faceemotion;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.SparseArray;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.FaceDetector;

import java.util.ArrayList;


import com.google.android.gms.vision.face.Face;

public class ImagesGallery {

    public static ArrayList<String> listOfImages(Context context){
        Uri uri;
        Cursor cursor;
        int column_index_data,column_index_folder_name;
        ArrayList<String>listOfAllImages= new ArrayList<>();
        String ablosutePathOfImage;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;


        String [] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME};

        String orderBy = MediaStore.Video.Media.DATE_TAKEN;
        cursor = context.getContentResolver().query(uri,projection,null
                ,null,orderBy+" DESC");

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);


        while (cursor.moveToNext()){
            //dosya yolu ile fotoğraflara erişim sağlanıyor

            ablosutePathOfImage = cursor.getString(column_index_data);

            final Bitmap myBitmap = BitmapFactory.decodeFile(ablosutePathOfImage);
            FaceDetector faceDetector = new FaceDetector.Builder(context.getApplicationContext())
                    .setTrackingEnabled(false)
                    .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                    .setMode(FaceDetector.FAST_MODE)
                    .build();

            Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
            SparseArray<Face> sparseArray = faceDetector.detect(frame);
            if(sparseArray.size()>0){
                listOfAllImages.add(ablosutePathOfImage);
            }

        }

        return listOfAllImages;
    }
}