package com.inscripts.chatapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import static com.inscripts.chatapp.Constants.*;
public class CustomListView extends ArrayAdapter<Chat> {

	public static class ViewHolder {

		public ImageView imageSender, imageReciver;
		public TextView message, time;
		public LinearLayout tvBg, tvTime;
//		public TextView sender_msg, reciver_msg;
//		public TextView sender_time, reciver_time;

	}

	private Activity activity;
	private ArrayList<Chat> array;
	private LayoutInflater inflater;
	private LinearLayout.LayoutParams params;
	private Bitmap icon;
	private Bitmap sender;
	private int pixels;

	public CustomListView(Activity activity, ArrayList<Chat> array) {
		super(activity, android.R.layout.simple_list_item_1);
	
		this.activity = activity;
		this.array = array;
		inflater = LayoutInflater.from(activity);
		final float scale = getContext().getResources().getDisplayMetrics().density;
		pixels = (int) (50 * scale + 0.5f);
		icon = BitmapFactory.decodeResource(activity.getResources(), R.drawable.deepika);
		sender = BitmapFactory.decodeResource(activity.getResources(), R.drawable.sid);
		
	}

	@Override
	public int getCount() {
		return array.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		

		LinearLayout linearLayout = (LinearLayout) convertView;
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = this.activity.getLayoutInflater();
			linearLayout = (LinearLayout) inflater.inflate(R.layout.demo,	null);

			holder = new ViewHolder();
			holder.imageReciver = (ImageView)linearLayout.findViewById(R.id.reciverImg);
			holder.imageSender = (ImageView)linearLayout.findViewById(R.id.senderImg);
			holder.message = (TextView)linearLayout.findViewById(R.id.txtMessage);
			holder.time = (TextView)linearLayout.findViewById(R.id.time);
			holder.tvBg = (LinearLayout)linearLayout.findViewById(R.id.contentWithBackground);
			holder.tvTime = (LinearLayout)linearLayout.findViewById(R.id.timelayout);
			holder.tvBg.setBackgroundResource(R.drawable.sender_text);
			params =  new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, pixels);
			linearLayout.setTag(holder);
		} else {
			holder = (ViewHolder) linearLayout.getTag();
		}
		
		
		Chat c = array.get(position);
		if (c.getRole().equalsIgnoreCase("sender")){
			params.setMargins(5, 5, 100, 5);
			holder.imageReciver.setVisibility(View.GONE);
			holder.imageSender.setVisibility(View.VISIBLE);
			holder.imageSender.setImageBitmap(sender);
			holder.message.setText(c.getMessage());
			holder.time.setText(c.getTimeStamp());
			holder.tvBg.setGravity(Gravity.RIGHT);
			holder.tvBg.setLayoutParams(params);
			holder.tvTime.setGravity(Gravity.LEFT);
			
		} else {
			params.setMargins(5, 5, 5, 5);
			holder.imageReciver.setVisibility(View.VISIBLE);
			holder.imageSender.setVisibility(View.GONE);
			holder.imageReciver.setImageBitmap(icon);
			holder.message.setText(c.getMessage());
			holder.time.setText(c.getTimeStamp());
			holder.tvBg.setGravity(Gravity.LEFT);
			holder.tvTime.setGravity(Gravity.RIGHT);
			holder.tvBg.setLayoutParams(params);
		}
		
		holder.time.setText(getDateCurrentTimeZone(Long.parseLong(c.getTimeStamp())));
		return linearLayout;

	}
	
	public  String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            Date currenTimeZone = (Date) calendar.getTime();
            Date d1 = new Date();
            return sdf.format(currenTimeZone);
        }catch (Exception e) {
        }
        return "";
    }

}