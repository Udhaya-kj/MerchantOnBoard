package life.corals.onboarding.Activity;

import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;

import life.corals.onboarding.R;

public class Upload_Photo_Activity extends AppCompatActivity {
    private LinearLayout button_next;
    private ImageView imageView_preview_photo;
    private SharedPreferences sharedpreferences_open_screen;
    private ImageView imageView_back;
    Bitmap resized_bm;
    public static final String MyPREFERENCES_UPLOAD_PHOTO = "MyPrefs_Upload_Photo";
    public static final String UPLOAD_PHOTO = "UPLOAD_PHOTO";
    public static final String UPLOAD_PHOTO2 = "UPLOAD_PHOTO2";
    public static final String UPLOAD_PHOTO_PATH = "UPLOAD_PHOTO_PATH";
    private SharedPreferences sharedpreferences_uphoto;
    private String encoded,encoded2,encoded_normal;
    private TextView upload_photo;
    private LinearLayout layout_upload_photo,layout_change_photo,layout_logo;
    private static final String TAG = Upload_Photo_Activity.class.getSimpleName();
    public static final int REQUEST_IMAGE = 100;
    //String path;
    Dimension imgSize,boundary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__photo_);

        sharedpreferences_open_screen = getSharedPreferences(Welcome_Activity.MyPREFERENCES_OPEN_SCREEN, Context.MODE_PRIVATE);
        sharedpreferences_uphoto = getSharedPreferences(MyPREFERENCES_UPLOAD_PHOTO, Context.MODE_PRIVATE);

        button_next = (LinearLayout) findViewById(R.id.button_next_photo_upload);
        imageView_preview_photo = (ImageView) findViewById(R.id.image_upload_logo);
        imageView_back = (ImageView) findViewById(R.id.back_arrow);
        layout_upload_photo = (LinearLayout) findViewById(R.id.text_upload_photo);
        layout_change_photo = (LinearLayout) findViewById(R.id.text_change_photo);
        layout_logo = (LinearLayout) findViewById(R.id.layout_logo);
        upload_photo = (TextView) findViewById(R.id.upload_photo);
        //upload_photo.setText(Html.fromHtml("<p><u>Upload Photo</u></p>"));

        String logo1 = sharedpreferences_uphoto.getString(UPLOAD_PHOTO, "");
        String logo2 = sharedpreferences_uphoto.getString(UPLOAD_PHOTO2, "");
        String get_path = sharedpreferences_uphoto.getString(UPLOAD_PHOTO_PATH, "");
        Log.d("ImageUpload--->", logo1+"," + logo2+"," + get_path );

        if (!TextUtils.isEmpty(get_path)) {
            //path = get_path;
            layout_logo.setVisibility(View.VISIBLE);
            byte[] imageAsBytes = Base64.decode(get_path.getBytes(), Base64.DEFAULT);
            imageView_preview_photo.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
            layout_change_photo.setVisibility(View.VISIBLE);

        }
        else {
            layout_logo.setVisibility(View.GONE);
        }

        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        layout_upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selectImageToCrop();
                checkandroidversion();
            }
        });

        layout_change_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //selectImageToCrop();
                checkandroidversion();
            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("Activity--->","Next");
                if (imageView_preview_photo.getDrawable() == null) {
                    //Image doesn´t exist.
                    Toast.makeText(Upload_Photo_Activity.this, "Please upload your logo!", Toast.LENGTH_LONG).show();
                } else {
                    //Image exist.
                    final ProgressDialog pd = new ProgressDialog(Upload_Photo_Activity.this, R.style.MyTheme);
                    pd.setMessage("Please wait...");
                    pd.setCancelable(false);
                    //pd.show();

                    Intent in = new Intent(getApplicationContext(), Setup_QR_Code_Activity.class);
                    startActivity(in);
                    finish();

                    /*  if (resized_bm != null) {

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                Intent in = new Intent(getApplicationContext(), Setup_QR_Code_Activity.class);
                                startActivity(in);
                                finish();
                            }
                        }, 4000);

                      if(TextUtils.isEmpty(encoded) && TextUtils.isEmpty(encoded2) && TextUtils.isEmpty(encoded_normal)) {
                            Bitmap resize_image1 = Bitmap.createScaledBitmap(resized_bm, 1280, 720, false);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            resize_image1.compress(Bitmap.CompressFormat.PNG, 90, baos); //bm is the bitmap object
                            byte[] b = baos.toByteArray();
                            encoded = Base64.encodeToString(b, Base64.DEFAULT);

                            Bitmap resize_image2 = Bitmap.createScaledBitmap(resized_bm, 800, 480, false);
                            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                            resize_image2.compress(Bitmap.CompressFormat.PNG, 90, baos2); //bm is the bitmap object
                            byte[] b2 = baos2.toByteArray();
                            encoded2 = Base64.encodeToString(b2, Base64.DEFAULT);

                            ByteArrayOutputStream baos_nr = new ByteArrayOutputStream();
                            resized_bm.compress(Bitmap.CompressFormat.PNG, 90, baos_nr); //bm is the bitmap object
                            byte[] b_nr = baos_nr.toByteArray();
                            encoded_normal = Base64.encodeToString(b_nr, Base64.DEFAULT);

                            SharedPreferences.Editor editor = sharedpreferences_uphoto.edit();
                            editor.putString(UPLOAD_PHOTO, encoded);
                            editor.putString(UPLOAD_PHOTO2, encoded2);
                            editor.putString(UPLOAD_PHOTO_PATH, encoded_normal);
                            editor.commit();
                        }


                    } else {
                        //Toast.makeText(Upload_Photo_Activity.this, "Resize null", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                Intent in = new Intent(getApplicationContext(), Setup_QR_Code_Activity.class);
                                startActivity(in);
                                finish();
                            }
                        }, 4000);

                        if(TextUtils.isEmpty(encoded) && TextUtils.isEmpty(encoded2) && TextUtils.isEmpty(encoded_normal)) {
                            Bitmap bitmap = ((BitmapDrawable) imageView_preview_photo.getDrawable()).getBitmap();
                            Bitmap resize_image1 = Bitmap.createScaledBitmap(bitmap, 1280, 720, false);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            resize_image1.compress(Bitmap.CompressFormat.PNG, 90, baos); //bm is the bitmap object
                            byte[] b = baos.toByteArray();
                            encoded = Base64.encodeToString(b, Base64.DEFAULT);

                            Bitmap resize_image2 = Bitmap.createScaledBitmap(bitmap, 800, 480, false);
                            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                            resize_image2.compress(Bitmap.CompressFormat.PNG, 90, baos2); //bm is the bitmap object
                            byte[] b2 = baos2.toByteArray();
                            encoded2 = Base64.encodeToString(b2, Base64.DEFAULT);

                            ByteArrayOutputStream baos_nr = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 90, baos_nr); //bm is the bitmap object
                            byte[] b_nr = baos_nr.toByteArray();
                            encoded_normal = Base64.encodeToString(b_nr, Base64.DEFAULT);

                            SharedPreferences.Editor editor = sharedpreferences_uphoto.edit();
                            editor.putString(UPLOAD_PHOTO, encoded);
                            editor.putString(UPLOAD_PHOTO2, encoded2);
                            editor.putString(UPLOAD_PHOTO_PATH, encoded_normal);
                            editor.commit();
                        }



                    }*/
                    /*}
                    else {
                        Toast.makeText(Upload_Photo_Activity.this, "Photo size should be 512 x 512 ", Toast.LENGTH_SHORT).show();
                    }*/
                }
            }
        });

        ImagePickerActivity.clearCache(this);
    }


    //Crop Image
    private void pickImage() {
        CropImage.startPickImageActivity(this);
    }

    private void croprequest(Uri imageuri) {
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                .setMultiTouchEnabled(true)
                .setAutoZoomEnabled(true)
                //.setRequestedSize(512, 512)
                //.setMinCropWindowSize(512, 512)
                //.setMaxCropResultSize(512, 512)
                .start(this);
    }

    public void checkandroidversion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 555);
            } catch (Exception e) {
                Log.d("Permission", "" + e.getMessage());
            }

        } else {
            pickImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 555 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage();
        } else {
            checkandroidversion();
        }

    }

     @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = CropImage.getPickImageResultUri(this, data);
            croprequest(uri);
        }


        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            Log.d("Bitmap---->", "" + "crop_req");
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
                    resized_bm = Bitmap.createScaledBitmap(bitmap, 512, 512, true);

                    // imageView_preview_photo.setImageBitmap(resized_bm);
                    Log.d("Bitmap---->", "" + bitmap);

                    loadProfile(result.getUri().toString());

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void onBackPressed() {
        Intent in = new Intent(Upload_Photo_Activity.this, Operating_Hours_Activity.class);
        startActivity(in);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }


    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(Upload_Photo_Activity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(Upload_Photo_Activity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {

                Uri uri = data.getParcelableExtra("path");
                //path = getRealPathFromUri(data.getData());
                //To get image path
            *//*    try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    Uri uri_resized = getImageUri(this, bitmap);

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri_resized, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    path = cursor.getString(columnIndex);
                    cursor.close();

                    // path=uri_resized.getPath();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("URI--->", "Image cache path Error: " + e.getMessage());
                }*//*
                //path=uri.getPath();
                //Log.d("Image URI--->", "Image cache path: " + path);
                loadProfile(uri.toString());

            }
        }
    }*/

    private String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getApplicationContext().getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);

        try {
            URL urlll = new URL(url);
            resized_bm = BitmapFactory.decodeStream(urlll.openConnection().getInputStream());

            View dialogView = LayoutInflater.from(Upload_Photo_Activity.this).inflate(R.layout.logo_preview_constraintlayout, null, false);
            final AlertDialog.Builder builder = new AlertDialog.Builder(Upload_Photo_Activity.this);
            final ImageView imageView = (ImageView) dialogView.findViewById(R.id.alert_preview);
            Button cancel_btn = (Button) dialogView.findViewById(R.id.preview_cancel_button);
            Button ok_btn = (Button) dialogView.findViewById(R.id.preview_ok_button);
            //setting the view of the builder to our custom view that we already inflated
            builder.setView(dialogView);
            builder.setCancelable(false);
            //finally creating the alert dialog and displaying it
            final AlertDialog alertDialog = builder.create();
            alertDialog.show();
            imageView.setImageBitmap(resized_bm);


            ok_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final ProgressDialog pd = new ProgressDialog(Upload_Photo_Activity.this, R.style.MyTheme);
                    pd.setMessage("Please wait...");
                    pd.setCancelable(false);
                    pd.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            alertDialog.dismiss();
                            layout_logo.setVisibility(View.VISIBLE);
                            imageView_preview_photo.setBackground(null);
                            imageView_preview_photo.setImageBitmap(resized_bm);

                            if (layout_change_photo.getVisibility() == View.GONE) {
                                layout_change_photo.setVisibility(View.VISIBLE);
                            }
                        }
                    },4000);

                    //Bitmap bm=((BitmapDrawable)imageView.getDrawable()).getBitmap();

                    imageView.setDrawingCacheEnabled(true);
                    imageView.buildDrawingCache();
                    Bitmap bm = imageView.getDrawingCache();


                    Bitmap resize_image1 = Bitmap.createScaledBitmap(bm, 1280, 720, false);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    resize_image1.compress(Bitmap.CompressFormat.JPEG, 90, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    encoded = Base64.encodeToString(b, Base64.DEFAULT);

                    Bitmap resize_image2 = Bitmap.createScaledBitmap(bm, 800, 480, false);
                    ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                    resize_image2.compress(Bitmap.CompressFormat.JPEG, 90, baos2); //bm is the bitmap object
                    byte[] b2 = baos2.toByteArray();
                    encoded2 = Base64.encodeToString(b2, Base64.DEFAULT);

                    ByteArrayOutputStream baos_nr = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 90, baos_nr); //bm is the bitmap object
                    byte[] b_nr = baos_nr.toByteArray();
                    encoded_normal = Base64.encodeToString(b_nr, Base64.DEFAULT);

                    SharedPreferences.Editor editor = sharedpreferences_uphoto.edit();
                    editor.putString(UPLOAD_PHOTO, encoded);
                    editor.putString(UPLOAD_PHOTO2, encoded2);
                    editor.putString(UPLOAD_PHOTO_PATH, encoded_normal);
                    editor.commit();

                }
            });

            cancel_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });


        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Upload_Photo_Activity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Upload_Photo_Activity.this.openSettings();
            }
        });
        builder.setNegativeButton(getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    void selectImageToCrop() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }



}
