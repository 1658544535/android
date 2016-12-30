package com.qunyu.taoduoduo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.andbase.library.image.AbImageLoader;
import com.andbase.library.util.AbFileUtil;
import com.andbase.library.util.AbImageUtil;
import com.andbase.library.util.AbLogUtil;
import com.andbase.library.util.AbStrUtil;

import java.io.File;
import java.util.List;


/**
 * 适配器 网络URL的图片和本地.
 */
public class ImageListAdapter extends BaseAdapter {
	
	/** 上下问. */
	private Context context;
	
	/** 图片的路径. */
	private List<String> images = null;
	
	/** 图片宽度. */
	private int width;
	
	/** 图片高度. */
	private int height;
	
	//图片下载器
    private AbImageLoader imageLoader = null;
	

	public ImageListAdapter(Context context,List<String> images,int width,int height) {
		this.context = context;
		this.images = images;
		this.width = width;
		this.height = height;
		this.imageLoader = new AbImageLoader(context);
	}
	
	/**
	 * 获取数量.
	 * @return the count
	 */
	public int getCount() {
		return images.size();
	}

	/**
	 * 获取索引位置的路径.
	 * @param position the position
	 * @return the item
	 */
	public Object getItem(int position) {
		return images.get(position);
	}

	/**
	 * 获取位置.
	 * @param position the position
	 * @return the item id
	 */
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 显示View.
	 * @param position the position
	 * @param convertView the convert view
	 * @param parent the parent
	 * @return the view
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();

			ImageView imageView = new ImageView(context);
			imageView.setScaleType(ScaleType.FIT_CENTER);
			holder.imageView = imageView;
			holder.imageView.setAdjustViewBounds(true);
			convertView = imageView;
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.imageView.setImageBitmap(null);
        holder.imageView.setImageDrawable(null);

		String image = images.get(position);
        AbLogUtil.e(this.context,position+"路径："+image);
		if(!AbStrUtil.isEmpty(image)){
	      		if(image.indexOf("http://")!=-1){
	      		    //图片的下载
	                imageLoader.display(holder.imageView,image,this.width,this.height);
					
				}else if(AbStrUtil.isNumber(image)){
					//索引图片
					try {
						int res  = Integer.parseInt(image);
						holder.imageView.setImageDrawable(context.getResources().getDrawable(res));
					} catch (Exception e) {
					}
				}else{

					Bitmap mBitmap = AbFileUtil.getBitmapFromSD(new File(image), AbImageUtil.SCALEIMG, width, height);
					if(mBitmap!=null){
						holder.imageView.setImageBitmap(mBitmap);
					}else{
						// 无图片时显示
					}
				}
		}

	    return convertView;
	}
	
	
	/**
	 * 增加并改变视图.
	 * @param position the position
	 * @param imagePaths the image paths
	 */
	public void addItem(int position,String imagePaths) {
		images.add(position,imagePaths);
		notifyDataSetChanged();
	}
	
	/**
	 * 增加多条并改变视图.
	 * @param image the image paths
	 */
	public void addItems(List<String> image) {
		images.addAll(image);
		notifyDataSetChanged();
	}
	
	/**
	 * 增加多条并改变视图.
	 */
	public void clearItems() {
		images.clear();
		notifyDataSetChanged();
	}
	
	/**
	 * View元素.
	 */
	public static class ViewHolder {
		public ImageView imageView;
	}

}
