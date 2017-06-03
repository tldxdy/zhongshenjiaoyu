package com.yunduancn.zhongshenjiaoyu.activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.utils.PhotoUtil;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class PersonalCenterActivity extends AppCompatActivity implements View.OnClickListener{


    TextView title;

    ImageView back_image, user_image;

    RelativeLayout touxian_layout, profession_layout, signature_layout, nickname_layout, birthday_layout, region_layout, binding_phone_layout, binding_email_layout, real_name_layout, school_registration_layout;



    private static final int PHOTO_PICKED_WITH_DATA = 1881;
    private static final int CAMERA_WITH_DATA = 1882;
    private static final int CAMERA_CROP_RESULT = 1883;
    private static final int PHOTO_CROP_RESOULT = 1884;
    private static final int ICON_SIZE = 96;


    private final int NEED_CAMERA = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);


        initView();
    }

    private void initView() {
        activity_personal_center = (LinearLayout) findViewById(R.id.activity_personal_center);
        user_image = (ImageView) findViewById(R.id.user_image);

        title = (TextView) findViewById(R.id.title);
        title.setText("个人信息");
        back_image = (ImageView) findViewById(R.id.back_image);
        back_image.setVisibility(View.VISIBLE);
        back_image.setOnClickListener(this);

        touxian_layout = (RelativeLayout) findViewById(R.id.touxian_layout);
        touxian_layout.setOnClickListener(this);
        profession_layout = (RelativeLayout) findViewById(R.id.profession_layout);
        profession_layout.setOnClickListener(this);
        signature_layout = (RelativeLayout) findViewById(R.id.signature_layout);
        signature_layout.setOnClickListener(this);
        nickname_layout = (RelativeLayout) findViewById(R.id.nickname_layout);
        nickname_layout.setOnClickListener(this);
        birthday_layout = (RelativeLayout) findViewById(R.id.birthday_layout);
        birthday_layout.setOnClickListener(this);
        region_layout = (RelativeLayout) findViewById(R.id.region_layout);
        region_layout.setOnClickListener(this);
        binding_phone_layout = (RelativeLayout) findViewById(R.id.binding_phone_layout);
        binding_phone_layout.setOnClickListener(this);
        binding_email_layout = (RelativeLayout) findViewById(R.id.binding_email_layout);
        binding_email_layout.setOnClickListener(this);
        real_name_layout = (RelativeLayout) findViewById(R.id.real_name_layout);
        real_name_layout.setOnClickListener(this);
        school_registration_layout = (RelativeLayout) findViewById(R.id.school_registration_layout);
        school_registration_layout.setOnClickListener(this);




    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_image:
                finish();
                break;
            case R.id.touxian_layout:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, NEED_CAMERA);
                } else {
                    showPop();

                }
                break;
            case R.id.profession_layout:

                break;
            case R.id.signature_layout:

                break;
            case R.id.nickname_layout:

                break;
            case R.id.birthday_layout:

                break;
            case R.id.region_layout:

                break;
            case R.id.binding_phone_layout:

                break;
            case R.id.binding_email_layout:

                break;
            case R.id.school_registration_layout:

                break;
        }
    }

    private PopupWindow mSetPhotoPop;
    private LinearLayout activity_personal_center;

    private File mCurrentPhotoFile;
    private Bitmap imageBitmap;


    /**
     *  弹出 popupwindow
     */
    public void showPop(){
        View mainView = LayoutInflater.from(this).inflate(R.layout.alert_setphoto_menu_layout, null);
        Button btnTakePhoto = (Button) mainView.findViewById(R.id.btn_take_photo);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetPhotoPop.dismiss();
                // 拍照获取
                doTakePhoto();
            }
        });
        Button btnCheckFromGallery = (Button) mainView.findViewById(R.id.btn_check_from_gallery);
        btnCheckFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetPhotoPop.dismiss();
                // 相册获取
                doPickPhotoFromGallery();
            }
        });
        Button btnCancle = (Button) mainView.findViewById(R.id.btn_cancel);
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSetPhotoPop.dismiss();
            }
        });
        mSetPhotoPop = new PopupWindow(this);
        mSetPhotoPop.setBackgroundDrawable(new BitmapDrawable());
        mSetPhotoPop.setFocusable(true);
        mSetPhotoPop.setTouchable(true);
        mSetPhotoPop.setOutsideTouchable(true);
        mSetPhotoPop.setContentView(mainView);
        mSetPhotoPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mSetPhotoPop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        mSetPhotoPop.setAnimationStyle(R.style.avatar_dialog_animation);

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N)
        {
            mSetPhotoPop.showAtLocation(activity_personal_center, Gravity.NO_GRAVITY, 0, 0);
        }else{
            mSetPhotoPop.showAtLocation(activity_personal_center, Gravity.BOTTOM, 0, 0);
        }
        mSetPhotoPop.update();
    }

    /**
     * 调用系统相机拍照
     */
    protected void doTakePhoto() {
        try {
            // Launch camera to take photo for selected contact
            File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/Photo");
            if (!file.exists()) {
                file.mkdirs();
            }
            mCurrentPhotoFile = new File(file, PhotoUtil.getRandomFileName());
            final Intent intent = getTakePickIntent(mCurrentPhotoFile);
            startActivityForResult(intent, CAMERA_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            ToastUtils.show(getApplicationContext(),R.string.photoPickerNotFoundText);
        }
    }

    /**
     * Constructs an intent for capturing a photo and storing it in a temporary
     * file.
     */
    public static Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }

    /**
     * 相机剪切图片
     */
    protected void doCropPhoto(File f) {
        try {
            // Add the image to the media store
            MediaScannerConnection.scanFile(this, new String[]{
                    f.getAbsolutePath()
            }, new String[]{
                    null
            }, null);

            // Launch gallery to crop the photo
            final Intent intent = getCropImageIntent(Uri.fromFile(f));
            startActivityForResult(intent, CAMERA_CROP_RESULT);
        } catch (Exception e) {
            ToastUtils.show(getApplicationContext(),R.string.photoPickerNotFoundText);
        }
    }

    /**
     * 获取系统剪裁图片的Intent.
     */
    public static Intent getCropImageIntent(Uri photoUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", ICON_SIZE);
        intent.putExtra("outputY", ICON_SIZE);
        intent.putExtra("return-data", true);
        return intent;
    }

    /**
     * 从相册选择图片
     */
    protected void doPickPhotoFromGallery() {
        try {
            // Launch picker to choose photo for selected contact
            final Intent intent = getPhotoPickIntent();
            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        } catch (ActivityNotFoundException e) {
            ToastUtils.show(getApplicationContext(),R.string.photoPickerNotFoundText);
        }
    }

    /**
     * 获取调用相册的Intent
     */
    public static Intent getPhotoPickIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return intent;
    }

    /**
     * 相册裁剪图片
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");//调用Android系统自带的一个图片剪裁页面,
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");//进行修剪
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", ICON_SIZE);
        intent.putExtra("outputY", ICON_SIZE);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CROP_RESOULT);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case NEED_CAMERA:
                // 如果权限被拒绝，grantResults 为空
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    showPop();

                } else {
                    ToastUtils.show(getApplicationContext(),"拍照需要获取相机权限");
                }
                break;

        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            switch (requestCode) {
                case PHOTO_PICKED_WITH_DATA:
                    // 相册选择图片后裁剪图片
                    startPhotoZoom(data.getData());
                    break;
                case PHOTO_CROP_RESOULT:
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        imageBitmap = extras.getParcelable("data");
                        //imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        user_image.setImageBitmap(imageBitmap);
                    }
                    break;
                case CAMERA_WITH_DATA:
                    // 相机拍照后裁剪图片
                    doCropPhoto(mCurrentPhotoFile);
                    break;
                case CAMERA_CROP_RESULT:
                    imageBitmap = data.getParcelableExtra("data");
                    // imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    user_image.setImageBitmap(imageBitmap);
                    break;
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCurrentPhotoFile = null;
        imageBitmap = null;

    }
}
