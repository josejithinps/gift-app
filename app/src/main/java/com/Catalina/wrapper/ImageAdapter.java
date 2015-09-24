package com.Catalina.wrapper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.Catalina.catalinaapp.GridImageView;
import com.Catalina.utilities.Settings;

public class ImageAdapter extends BaseAdapter{
	
	private Context context;
	private Integer[] thumbIDs;
	
	public ImageAdapter(Context c, Integer[] thumbs){
		context = c;
		thumbIDs = thumbs;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return thumbIDs.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		// TODO Auto-generated method stub
		GridImageView imageView;
		if(convertView == null){
			imageView = new GridImageView(context);
//			imageView.setLayoutParams(new GridView.LayoutParams(500,500));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		}else{
			imageView = (GridImageView) convertView;
		}
		
		imageView.setImageResource(thumbIDs[position]);

		return imageView;
	}

}
