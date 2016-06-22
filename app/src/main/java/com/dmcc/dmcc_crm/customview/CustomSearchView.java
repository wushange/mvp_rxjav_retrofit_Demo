package com.dmcc.dmcc_crm.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmcc.dmcc_crm.R;


public class CustomSearchView extends LinearLayout {

	public View view;
	public EditText search_content;
	public ImageButton search_clear;
	public ImageView floatView;
	public AutoSearchListener searchListener;
	public TextView searchBtn;
	private boolean isShowBtn;

	/**
	 * @param context
	 */
	public CustomSearchView(Context context) {
		this(context, null);
	}

	/**
	 * @param searchListener
	 *            the searchListener to set
	 */
	public void setSearchListener(AutoSearchListener searchListener) {
		this.searchListener = searchListener;
	}

	public CustomSearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 在构造函数中将Xml中定义的布局解析出来。
		view = LayoutInflater.from(context).inflate(R.layout.view_search, this,
				true);
		search_content = (EditText) view.findViewById(R.id.search_content);
		searchBtn = (TextView) view.findViewById(R.id.search_btn);
		floatView = (ImageView) view.findViewById(R.id.float_text);
		// 添加自定义的属性
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.SearchButton);
		CharSequence text = a.getText(R.styleable.SearchButton_android_text);
		isShowBtn = a.getBoolean(R.styleable.SearchButton_isShowBtn, false);
		/*
		 * if(text != null){ search_button.setText(text); }
		 */

		// 添加文字控制 如果添加字符 文字 则显示 清空键
		if (search_content != null)
			search_content.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					if (s.length() > 0) {
						if (isShowBtn)
							searchBtn.setVisibility(View.VISIBLE);
						search_clear.setVisibility(View.VISIBLE);
						floatView.setVisibility(View.GONE);
					} else {
						if (isShowBtn)
							search_clear.setVisibility(View.GONE);
						searchBtn.setVisibility(View.GONE);
						floatView.setVisibility(View.VISIBLE);
					}
					if (null != searchListener) {
						searchListener.autoSearch();
					}

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub

				}
			});

		search_clear = (ImageButton) view.findViewById(R.id.search_clear);
		// 清空输入
		if (search_clear != null)
			search_clear.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String content = search_content.getText().toString();
					if (content.length() > 0) {
						search_content.setText("");

					}
				}
			});

	}

	public void setEnableSearch(boolean isenalbeSearch) {
		if (isenalbeSearch == false) {
			search_content.setEnabled(false);
		}

	}

	public TextView getBtn() {
		return searchBtn;
	}

	public interface AutoSearchListener {
		void autoSearch();
	}

}
