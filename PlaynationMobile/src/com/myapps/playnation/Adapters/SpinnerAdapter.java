package com.myapps.playnation.Adapters;

import com.myapps.playnation.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends ArrayAdapter<String> {

	private LayoutInflater inflater;
	String[] array = null;
	ViewHolder holder;

	public SpinnerAdapter(Activity act, int textViewResourceId, String[] array) {
		super(act, textViewResourceId, array);
		this.inflater = (LayoutInflater) act
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.array = array;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return array.length;
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return array[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
	    if(convertView==null){
	     
	      vi = inflater.inflate(R.layout.component_spinner, null);
	      holder = new ViewHolder();
	     
	      holder.tvGameType = (TextView)vi.findViewById(R.id.component_spinner_TV); 
	  //    holder.tvImage =(ImageView)vi.findViewById(R.id.component_spinner_pic); 	 
	      vi.setTag(holder);
	    }
	    else{	    	
	    	holder = (ViewHolder)vi.getTag();
	    }
	   
	      holder.tvGameType.setText(array[position]);
	     // holder.tvImage.setImageResource(R.drawable.picdown);

	      return vi;	}
	static class ViewHolder{		
		TextView tvGameType;
		//ImageView tvImage;
	}

}
