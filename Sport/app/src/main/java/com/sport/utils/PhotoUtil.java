package com.sport.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PhotoUtil {

	public static final int PiC_FROM_ALBUM = 1;
	public static final int PiC_FROM_CAMERA = 2;
	public static final int PiC_FROM_CROP = 3;
	public static final String IMAGE_NAME = "image.jpg";


	// 相册去图片
	public static void getPhotoFromAlbum(FragmentActivity mContext) {

		Intent getAlbum;
		if (Build.VERSION.SDK_INT < 19) {
			getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
			getAlbum.setType("image/*");


			mContext.startActivityForResult(getAlbum, PiC_FROM_ALBUM);
		} else {
			getAlbum = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			mContext.startActivityForResult(getAlbum, PiC_FROM_ALBUM);
		}

	}

	// 拍照取图片
	public static void getPhotoFromCarema(FragmentActivity mContext) {

		Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if (hasSdcard()) {
			File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
			File file = new File(path, IMAGE_NAME);
			intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		}
		mContext.startActivityForResult(intentFromCapture, PiC_FROM_CAMERA);
	}

	// 切图
	public static void zoomPhoto(FragmentActivity mContext, Uri uri, int aspectX, int aspectY, int outputX, int outputY) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", aspectX);
		intent.putExtra("aspectY", aspectY);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("return-data", true);
		mContext.startActivityForResult(intent, PiC_FROM_CROP);
	}

	public static String reSizeImg(String imgFile, float size) {

		Bitmap image = reSizeImg(imgFile, 800, 800);

		File f = new File(imgFile);
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			image.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imgFile;
	}

	public static Bitmap reSizeImg(String imgFile, int weight, int height) {
		Options o = new Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imgFile, o);
		int scale = 0;
		while ((o.outWidth >> scale) > weight || (o.outHeight >> scale) > height) {
			scale++;
		}
		o = new Options();
		o.inSampleSize = 1 << scale;

		return BitmapFactory.decodeFile(imgFile, o);
	}

	public static String reSizeImg(String imgFile) {
		return reSizeImg(imgFile, 200);
	}

	public static Bitmap reSizeImgWH(String imgFile) {
		return reSizeImg(imgFile, 800, 800);
	}


	/**
	 * 判断是否存在sd卡
	 *
	 * @return
	 */
	public static boolean hasSdcard() {

		String status = Environment.getExternalStorageState();

		return status.equals(Environment.MEDIA_MOUNTED) ? true : false;
	}


	public static File saveFile(Bitmap bm, String fileName){
		String path = Environment.getExternalStorageDirectory().getAbsolutePath() +"/imageTemp/";
		File dirFile = new File(path);
		if(!dirFile.exists()){
			dirFile.mkdir();
		}
		File myCaptureFile = new File(path + fileName);
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
			bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
			bos.flush();
			bos.close();
			return myCaptureFile;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
