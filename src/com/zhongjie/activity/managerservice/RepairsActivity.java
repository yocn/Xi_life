package com.zhongjie.activity.managerservice;

import java.util.Calendar;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import antistatic.spinnerwheel.AbstractWheel;
import antistatic.spinnerwheel.OnWheelChangedListener;
import antistatic.spinnerwheel.WheelVerticalView;
import antistatic.spinnerwheel.adapters.NumericWheelAdapter;

import com.zhongjie.R;
import com.zhongjie.activity.BaseSecondActivity;

public class RepairsActivity extends BaseSecondActivity{
	
	private WheelVerticalView mYearWheel, mMonthWheel, mDayWheel;
	private Calendar mCurCalendar, mMaxCalendar;
	private View mSubmit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_repairs);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initData() {
		mCurCalendar = Calendar.getInstance(Locale.CHINA);
		mMaxCalendar = (Calendar)mCurCalendar.clone();
		mMaxCalendar.add(Calendar.DAY_OF_YEAR, 100);
	}

	@Override
	protected void findViews() {
		mYearWheel = (WheelVerticalView)findViewById(R.id.act_repairs_wheel_year);
		mMonthWheel = (WheelVerticalView)findViewById(R.id.act_repairs_wheel_month);
		mDayWheel = (WheelVerticalView)findViewById(R.id.act_repairs_wheel_day);
		mSubmit = findViewById(R.id.act_repairs_submit);
	}
	
	private void initCalendar(){
		final int curYear = mCurCalendar.get(Calendar.YEAR);
		final int maxYear = mMaxCalendar.get(Calendar.YEAR);
		final int curMounth = mCurCalendar.get(Calendar.MONTH) + 1;
		final int maxMounth = mMaxCalendar.get(Calendar.MONTH) + 1;
		mYearWheel.setViewAdapter(new NumericWheelAdapter(this, curYear
				, maxYear));
		
		//设置可选月数
		if(curYear == maxYear){
			mMonthWheel.setViewAdapter(new NumericWheelAdapter(this, curMounth
					, maxMounth, "%02d"));
		}else{
			mMonthWheel.setViewAdapter(new NumericWheelAdapter(this, curMounth
					, 12, "%02d"));
			
			mYearWheel.addChangingListener(new OnWheelChangedListener() {
				
				@Override
				public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
					newValue = paresStrDate(((NumericWheelAdapter)mMonthWheel.getViewAdapter()).getItemText(newValue).toString());
					//设置可选月数
					if(newValue == curYear){
						mMonthWheel.setViewAdapter(new NumericWheelAdapter(RepairsActivity.this, curMounth
								, 12, "%02d"));
					}else if(newValue == maxYear){
						mMonthWheel.setViewAdapter(new NumericWheelAdapter(RepairsActivity.this, 1
								, maxMounth, "%02d"));
					}else{
						mMonthWheel.setViewAdapter(new NumericWheelAdapter(RepairsActivity.this, 1
								, 12, "%02d"));
					}
					mMonthWheel.setCurrentItem(0, true);
				}
			});
		}
		
		
		
		if(curMounth == maxMounth){
			mDayWheel.setViewAdapter(new NumericWheelAdapter(this, mCurCalendar.get(Calendar.DAY_OF_MONTH)
					, mMaxCalendar.get(Calendar.DAY_OF_MONTH), "%02d"));
		}else{
			mDayWheel.setViewAdapter(new NumericWheelAdapter(this, mCurCalendar.get(Calendar.DAY_OF_MONTH)
					, mCurCalendar.getActualMaximum(Calendar.DAY_OF_MONTH), "%02d"));
			
			mMonthWheel.addChangingListener(new OnWheelChangedListener() {
				
				@Override
				public void onChanged(AbstractWheel wheel, int oldValue, int newValue) {
					newValue = paresStrDate(((NumericWheelAdapter)mMonthWheel.getViewAdapter()).getItemText(newValue).toString());
					if(newValue == curMounth){
						mDayWheel.setViewAdapter(new NumericWheelAdapter(RepairsActivity.this, mCurCalendar.get(Calendar.DAY_OF_MONTH)
								, mCurCalendar.getActualMaximum(Calendar.DAY_OF_MONTH), "%02d"));
					}else if(newValue == maxMounth){
						mDayWheel.setViewAdapter(new NumericWheelAdapter(RepairsActivity.this, 1
								, mMaxCalendar.get(Calendar.DAY_OF_MONTH), "%02d"));
					}else{
						Calendar temp = ((Calendar)mCurCalendar.clone());
						temp.set(Calendar.MONTH, newValue - 1);
						mDayWheel.setViewAdapter(new NumericWheelAdapter(RepairsActivity.this, 1
								, temp.getActualMaximum(Calendar.DAY_OF_MONTH), "%02d"));
					}
					mDayWheel.setCurrentItem(0, true);
				}
			});
		}
	}
	
	private int paresStrDate(String strDate){
		if(strDate.charAt(0) == 0 && strDate.length() > 1){
			return Integer.valueOf(strDate.substring(1, strDate.length()));
		}
		return Integer.valueOf(strDate);
	}

	@Override
	protected void initViews() {
		mTopCenterImg.setImageResource(R.drawable.ic_logo_repair_top);
		mTopCenterImg.setVisibility(View.VISIBLE);
		
		initCalendar();
		
		
		mSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(RepairsActivity.this, RepairsSuccess.class));
			}
		});
	}
	
}