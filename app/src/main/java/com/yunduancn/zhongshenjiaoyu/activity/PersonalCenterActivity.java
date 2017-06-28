package com.yunduancn.zhongshenjiaoyu.activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.yunduancn.zhongshenjiaoyu.R;
import com.yunduancn.zhongshenjiaoyu.model.JsonBean;
import com.yunduancn.zhongshenjiaoyu.model.UserInfoModel;
import com.yunduancn.zhongshenjiaoyu.utils.Constant;
import com.yunduancn.zhongshenjiaoyu.utils.DateUtil;
import com.yunduancn.zhongshenjiaoyu.utils.Dialogmanager;
import com.yunduancn.zhongshenjiaoyu.utils.GetJsonDataUtil;
import com.yunduancn.zhongshenjiaoyu.utils.OkHttp_Utils;
import com.yunduancn.zhongshenjiaoyu.utils.PhotoUtil;
import com.yunduancn.zhongshenjiaoyu.utils.SharedPreferencesUtils;
import com.yunduancn.zhongshenjiaoyu.utils.ToastUtils;
import com.yunduancn.zhongshenjiaoyu.utils.UrlUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

public class PersonalCenterActivity extends AppCompatActivity implements View.OnClickListener{


    TextView title;

    ImageView back_image, user_image;

    RelativeLayout touxian_layout, profession_layout, signature_layout, nickname_layout, birthday_layout,
            region_layout, binding_phone_layout, binding_email_layout, real_name_layout, school_registration_layout,
            sex_layout;


    TextView profession_text, signature_text, nickname_text, birthday_text, region_text,
            binding_phone_text, binding_email_text, real_name_text, school_registration_text,sex_text;



    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";

    private static final int PHOTO_PICKED_WITH_DATA = 1881;
    private static final int CAMERA_WITH_DATA = 1882;
    private static final int CAMERA_CROP_RESULT = 1883;
    private static final int PHOTO_CROP_RESOULT = 1884;
    private static final int ICON_SIZE = 96;


    private final int NEED_CAMERA = 200;


    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();


    UserInfoModel userInfoModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);


        initView();
        initJsonData();
    }

    private void initView() {
        userInfoModel = (UserInfoModel) getIntent().getSerializableExtra("userInfoModel");

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
        sex_layout = (RelativeLayout) findViewById(R.id.sex_layout);
        sex_layout.setOnClickListener(this);



        profession_text = (TextView) findViewById(R.id.profession_text);
        signature_text = (TextView) findViewById(R.id.signature_text);
        nickname_text = (TextView) findViewById(R.id.nickname_text);
        birthday_text = (TextView) findViewById(R.id.birthday_text);
        region_text = (TextView) findViewById(R.id.region_text);
        binding_phone_text = (TextView) findViewById(R.id.binding_phone_text);
        binding_email_text = (TextView) findViewById(R.id.binding_email_text);
        real_name_text = (TextView) findViewById(R.id.real_name_text);
        school_registration_text = (TextView) findViewById(R.id.school_registration_text);
        sex_text = (TextView) findViewById(R.id.sex_text);

        /**
         *设置信息
         */
        profession_text.setText(userInfoModel.getJob());
        signature_text.setText(userInfoModel.getSignature());
        nickname_text.setText(userInfoModel.getNickname());
        birthday_text.setText(userInfoModel.getDateField1());
        region_text.setText(userInfoModel.getVarcharField1());
        binding_phone_text.setText(userInfoModel.getVerifiedMobile());
        binding_email_text.setText(userInfoModel.getEmail());
        real_name_text.setText(userInfoModel.getApprovalStatus());
        sex_text.setText(userInfoModel.getGender());



        Picasso.with(this)
                .load(userInfoModel.getLargeAvatar())
                .error(R.mipmap.me)
                .placeholder(R.mipmap.me)
                .resize(100,100)
                .into(user_image);


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
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
                    settouxian();
                    //showPop();

                }
                break;
            case R.id.profession_layout:
                intent.setClass(this,PersonalInformationModifyActivity.class);
                intent.putExtra("title","编写职业");
                intent.putExtra("content",profession_text.getText().toString().trim() + "");
                startActivityForResult(intent,101);


                break;
            case R.id.signature_layout:

                intent.setClass(this,PersonalInformationModifyActivity.class);
                intent.putExtra("title","编写签名");
                intent.putExtra("content",signature_text.getText().toString().trim() + "");
                startActivityForResult(intent,102);

                break;
            case R.id.nickname_layout:
                intent.setClass(this,PersonalInformationModifyActivity.class);
                intent.putExtra("title","编写昵称");
                intent.putExtra("content",nickname_text.getText().toString().trim() + "");
                startActivityForResult(intent,103);

                break;
            case R.id.birthday_layout:

                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        birthday_text.setText(DateUtil.birthdaytime(date));
                        setDateField1();
                    }
                })
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .setTitleText("日期选择")
                        .setLabel("年", "月", "日", "", "", "")
                        .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                        .isDialog(true)//是否显示为对话框样式
                        .build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();


                break;
            case R.id.region_layout:
                OptionsPickerView  pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1).getPickerViewText()+
                                options2Items.get(options1).get(options2)+
                                options3Items.get(options1).get(options2).get(options3);
                        region_text.setText(tx);

                        setVarcharField1();

                    }
                })

                        .setTitleText("城市选择")
                        .setDividerColor(Color.BLACK)
                        .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                        .setContentTextSize(20)
                        .isDialog(true)//是否显示为对话框样式
                        .setOutSideCancelable(false)// default is true
                        .build();
                pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
                pvOptions.show();



                break;
            case R.id.binding_phone_layout:

                break;
            case R.id.binding_email_layout:

                /*intent.setClass(this,PersonalInformationModifyActivity.class);
                intent.putExtra("title","编写邮箱");
                intent.putExtra("content",binding_email_text.getText().toString().trim() + "");
                startActivityForResult(intent,104);*/


                break;
            case R.id.school_registration_layout:

                break;

            case R.id.real_name_layout:

                break;

            case R.id.sex_layout:
                    setSex();
                break;

            default:


                break;
        }
    }

    private void settouxian() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = View.inflate(this, R.layout.xuanze, null);
        builder.setView(v);
        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView gallery = (TextView) v.findViewById(R.id.gallery);
        TextView camera = (TextView) v.findViewById(R.id.camera);
        gallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                doPickPhotoFromGallery();
                dialog.dismiss();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                doTakePhoto();
                dialog.dismiss();
            }
        });
    }

    private void setSex() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = View.inflate(this, R.layout.xuanze, null);
        builder.setView(v);
        final AlertDialog dialog = builder.create();
        dialog.show();

        TextView gallery = (TextView) v.findViewById(R.id.gallery);
        gallery.setText("男");
        TextView camera = (TextView) v.findViewById(R.id.camera);
        camera.setText("女");
        gallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sex_text.setText("男");
                setGender();
                dialog.dismiss();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sex_text.setText("女");
                setGender();
                dialog.dismiss();
            }
        });
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
                    //showPop();
                    settouxian();
                } else {
                    ToastUtils.show(getApplicationContext(),"拍照需要获取相机权限");
                }
                break;

        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 105){
            String d = data.getStringExtra("information");
            switch (requestCode){
                case 101:
                    profession_text.setText(d);
                    Dialogmanager.loadstart(this);
                    setJobs();
                    break;
                case 102:
                    signature_text.setText(d);
                    Dialogmanager.loadstart(this);
                    setSignature();
                    break;
                case 103:
                    nickname_text.setText(d);
                    Dialogmanager.loadstart(this);
                    setNickname();
                    break;
                case 104:
                    binding_email_text.setText(d);
                    break;

            }
        }


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
                        // imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        user_image.setImageBitmap(imageBitmap);
                        Dialogmanager.loadstart(this);
                        setUserImage();
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
                    Dialogmanager.loadstart(this);
                    setUserImage();
                    break;
            }
        }
    }



    private void setUserImage() {
        Map<String, String> map = new HashMap<>();
        map.put("largeavatar", getimagedate() + "");
        map.put("userId", SharedPreferencesUtils.getValue(this, Constant.AppName,"userId",null) + "");
        OkHttp_Utils.PostMethods(map, UrlUtils.largeavatarurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    /*if (code == 0) {

                    }*/
                    ToastUtils.show(getApplicationContext(), json.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    /**
     * B、职业
     */
    private void setJobs() {
        Map<String, String> map = new HashMap<>();
        map.put("job", profession_text.getText().toString().trim() + "");
        map.put("userId", SharedPreferencesUtils.getValue(this, Constant.AppName,"userId",null) + "");
        OkHttp_Utils.PostMethods(map, UrlUtils.jobsurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    /*if (code == 0) {

                    }*/
                    ToastUtils.show(getApplicationContext(), json.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    /**
     * C、签名
     */
    private void setSignature() {
        Map<String, String> map = new HashMap<>();
        map.put("signature", signature_text.getText().toString().trim() + "");
        map.put("userId", SharedPreferencesUtils.getValue(this, Constant.AppName,"userId",null) + "");
        OkHttp_Utils.PostMethods(map, UrlUtils.signatureurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    /*if (code == 0) {

                    }*/
                    ToastUtils.show(getApplicationContext(), json.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    /**
     * 设置昵称
     */
    private void setNickname() {
        Map<String, String> map = new HashMap<>();
        map.put("nickname", nickname_text.getText().toString().trim() + "");
        map.put("userId", SharedPreferencesUtils.getValue(this, Constant.AppName,"userId",null) + "");
        OkHttp_Utils.PostMethods(map, UrlUtils.nicknameurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    /*if (code == 0) {

                    }*/
                    ToastUtils.show(getApplicationContext(), json.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    /**
     * E、性别 gender
     */
    private void setGender() {
        Map<String, String> map = new HashMap<>();
        map.put("gender", sex_text.getText().toString().trim() + "");
        map.put("userId", SharedPreferencesUtils.getValue(this, Constant.AppName,"userId",null) + "");
        OkHttp_Utils.PostMethods(map, UrlUtils.genderurl, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    /*if (code == 0) {

                    }*/
                    ToastUtils.show(getApplicationContext(), json.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
    }


    /**
     * F、生日
     */
    private void setDateField1() {
        Map<String, String> map = new HashMap<>();
        map.put("dateField1", birthday_text.getText().toString().trim() + "");
        map.put("userId", SharedPreferencesUtils.getValue(this, Constant.AppName,"userId",null) + "");
        OkHttp_Utils.PostMethods(map, UrlUtils.dateField1url, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    /*if (code == 0) {

                    }*/
                    ToastUtils.show(getApplicationContext(), json.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
    }

    /**
     * G、地区 varcharField1
     */
    private void setVarcharField1() {
        Map<String, String> map = new HashMap<>();
        map.put("varcharField1", region_text.getText().toString().trim() + "");
        map.put("userId", SharedPreferencesUtils.getValue(this, Constant.AppName,"userId",null) + "");
        OkHttp_Utils.PostMethods(map, UrlUtils.varcharField1url, new OkHttp_Utils.CallBack() {
            @Override
            public void onMyError(Call call, Exception e, int id) {
                Dialogmanager.loadfinsh(0);
            }

            @Override
            public void onMyResponse(String response, int id) {
                Dialogmanager.loadfinsh(0);
                try {
                    JSONObject json = new JSONObject(response);
                    int code = json.getInt("code");
                    /*if (code == 0) {

                    }*/
                    ToastUtils.show(getApplicationContext(), json.getString("msg"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
    }


    private String getimagedate() {
        if (imageBitmap != null) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            try {
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] buffer = out.toByteArray();

            byte[] encode = Base64.encode(buffer, Base64.DEFAULT);
            String photo = new String(encode);
            return photo;
        }

        return null;
    }


    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this,"province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c=0; c<jsonBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        ||jsonBean.get(i).getCityList().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {

                    for (int d=0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }


    }


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCurrentPhotoFile = null;
        imageBitmap = null;

    }
}
