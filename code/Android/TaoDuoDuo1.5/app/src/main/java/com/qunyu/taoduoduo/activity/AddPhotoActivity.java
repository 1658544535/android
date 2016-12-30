package com.qunyu.taoduoduo.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.andbase.library.app.base.AbBaseActivity;
import com.andbase.library.util.AbDialogUtil;
import com.andbase.library.util.AbFileUtil;
import com.andbase.library.util.AbLogUtil;
import com.andbase.library.util.AbStrUtil;
import com.andbase.library.view.listener.AbOnItemClickListener;
import com.andbase.library.view.sample.AbSampleGridView;
import com.qunyu.taoduoduo.R;
import com.qunyu.taoduoduo.adapter.ImageListAdapter;
import com.qunyu.taoduoduo.global.MyApplication;
import com.qunyu.taoduoduo.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class AddPhotoActivity extends AbBaseActivity {
	
	private MyApplication application;
	private AbSampleGridView girdView = null;
	private ImageListAdapter imageListAdapter = null;
	private ArrayList<String> photoList = null;
	private int selectIndex = 0;
	private int camIndex = 0;
	private View mAvatarView = null;
	
	/* 用来标识请求照相功能的activity */
	private static final int CAMERA_WITH_DATA = 3023;
	/* 用来标识请求gallery的activity */
	private static final int PHOTO_PICKED_WITH_DATA = 3021;
	/* 用来标识请求裁剪图片后的activity */
	private static final int CAMERA_CROP_DATA = 3022;
	
	// 照相机拍照得到的图片
	private File currentPhotoFile;
	private String fileName;

 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_photo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitle(R.string.title_add_photo);
        toolbar.setContentInsetsRelative(0, 0);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

		application = (MyApplication) this.getApplication();
		photoList = new ArrayList<String>();
		
        //默认
		photoList.add(String.valueOf(R.mipmap.cam_photo));
		
		girdView = (AbSampleGridView)findViewById(R.id.grid_view);
        girdView.setPadding(10,10);
        girdView.setColumn(4);
		imageListAdapter = new ImageListAdapter(this, photoList,120,120);
		girdView.setAdapter(imageListAdapter);
	   
		//初始化图片保存路径
	    String photo_dir = AbFileUtil.getImageDownloadDir(this);
	    if(AbStrUtil.isEmpty(photo_dir)){
			ToastUtils.showShortToast(AddPhotoActivity.this, "存储卡不存在");
		}
		
		
		Button addBtn = (Button)findViewById(R.id.addBtn);

		girdView.setOnItemClickListener(new AbOnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
				selectIndex = position;
				if(selectIndex == camIndex){
					mAvatarView = View.inflate(AddPhotoActivity.this,R.layout.view_choose_avatar,null);
					Button albumButton = (Button)mAvatarView.findViewById(R.id.choose_album);
					Button camButton = (Button)mAvatarView.findViewById(R.id.choose_cam);
					Button cancelButton = (Button)mAvatarView.findViewById(R.id.choose_cancel);
					albumButton.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							AbDialogUtil.removeDialog(AddPhotoActivity.this);
							// 从相册中去获取
							try {
								Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
								intent.setType("image/*");
								startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
							} catch (Exception e) {
								ToastUtils.showShortToast(AddPhotoActivity.this, "没有找到照片");
							}
						}

					});

					camButton.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							AbDialogUtil.removeDialog(AddPhotoActivity.this);
							doPickPhotoAction();
						}

					});

					cancelButton.setOnClickListener(new OnClickListener(){

						@Override
						public void onClick(View v) {
							AbDialogUtil.removeDialog(AddPhotoActivity.this);
						}

					});
					AbDialogUtil.showDialog(mAvatarView,Gravity.BOTTOM);
				}else{
					//点击了 其他的
				}
			}

		});
		
		addBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				uploadFile(photoList);
			}
		});

	}
	
	/**
	 * 从照相机获取
	 */
	private void doPickPhotoAction() {
		String status = Environment.getExternalStorageState();
		//判断是否有SD卡,如果有sd卡存入sd卡在说，没有sd卡直接转换为图片
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			doTakePhoto();
		} else {
			ToastUtils.showShortToast(AddPhotoActivity.this, "没有可用的存储卡");
		}
	}

	/**
	 * 拍照获取图片
	 */
	protected void doTakePhoto() {
		try {
			fileName = "album_"+new Random().nextInt(1000) + "-" + System.currentTimeMillis() + ".png";
            String photo_dir = AbFileUtil.getImageDownloadDir(this);
			currentPhotoFile = new File(photo_dir, fileName);
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentPhotoFile));
			startActivityForResult(intent, CAMERA_WITH_DATA);
		} catch (Exception e) {
			ToastUtils.showShortToast(AddPhotoActivity.this, "未找到系统相机程序");
		}
	}
	
	/**
	 * 因为调用了Camera和Gally所以要判断他们各自的返回情况,
	 * 他们启动时是这样的startActivityForResult
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent mIntent) {
		if (resultCode != RESULT_OK){
			return;
		}
		switch (requestCode) {
			case PHOTO_PICKED_WITH_DATA:
				Uri uri = mIntent.getData();
				String currentFilePath = getPath(uri);
				if(!AbStrUtil.isEmpty(currentFilePath)){
					Intent intent1 = new Intent(this, CropImageActivity.class);
					intent1.putExtra("PATH", currentFilePath);
					startActivityForResult(intent1, CAMERA_CROP_DATA);
		        }else{
					ToastUtils.showShortToast(AddPhotoActivity.this, "未在存储卡中找到这个文件");
				}
				break;
			case CAMERA_WITH_DATA:
				AbLogUtil.d(AddPhotoActivity.class, "将要进行裁剪的图片的路径是 = " + currentPhotoFile.getPath());
				String currentFilePath2 = currentPhotoFile.getPath();
				Intent intent2 = new Intent(this, CropImageActivity.class);
				intent2.putExtra("PATH",currentFilePath2);
				startActivityForResult(intent2,CAMERA_CROP_DATA);
				break;
			case CAMERA_CROP_DATA:
				String path = mIntent.getStringExtra("PATH");
		    	AbLogUtil.e(AddPhotoActivity.class, "裁剪后得到的图片的路径是 = " + path);
				imageListAdapter.addItem(imageListAdapter.getCount()-1,path);
		     	camIndex++;
				break;
		}
	}

	/**
	 * 从相册得到的url转换为SD卡中图片路径
	 */
	public String getPath(Uri uri) {
		if(AbStrUtil.isEmpty(uri.getAuthority())){
			return null;
		}

        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
	}

	public void uploadFile(List<String> photoList){

	}

}
