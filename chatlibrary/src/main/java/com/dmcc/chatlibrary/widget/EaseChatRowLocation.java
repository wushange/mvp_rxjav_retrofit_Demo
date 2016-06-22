package com.dmcc.chatlibrary.widget;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dmcc.chatlibrary.R;
import com.dmcc.chatlibrary.bean.EMMessage;
import com.dmcc.chatlibrary.bean.LatLng;
import com.dmcc.chatlibrary.bean.LocationMessageBody;

public class EaseChatRowLocation extends EaseChatRow{

    private TextView locationView;
    private LocationMessageBody locBody;

	public EaseChatRowLocation(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflatView() {
        inflater.inflate(message.direct == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_received_location : R.layout.ease_row_sent_location, this);
    }

    @Override
    protected void onFindViewById() {
    	locationView = (TextView) findViewById(R.id.tv_location);
    }


    @Override
    protected void onSetUpView() {
		locBody = (LocationMessageBody) message.getBody();
		locationView.setText(locBody.getAddress());

		// deal with send message
		if (message.direct == EMMessage.Direct.SEND) {
		    setMessageSendCallback();
            switch (message.status) {
            case CREATE: 
                progressBar.setVisibility(View.VISIBLE);
                statusView.setVisibility(View.GONE);
                // 发送消息
//                sendMsgInBackground(message);
                break;
            case SUCCESS: // 发送成功
                progressBar.setVisibility(View.GONE);
                statusView.setVisibility(View.GONE);
                break;
            case FAIL: // 发送失败
                progressBar.setVisibility(View.GONE);
                statusView.setVisibility(View.VISIBLE);
                break;
            case INPROGRESS: // 发送中
                progressBar.setVisibility(View.VISIBLE);
                statusView.setVisibility(View.GONE);
                break;
            default:
               break;
            }
        }else{
            if(!message.isAcked() && message.getChatType() == EMMessage.ChatType.Chat){
                message.isAcked = true;
            }
        }
    }
    
    @Override
    protected void onUpdateView() {
        adapter.notifyDataSetChanged();
    }
    
    @Override
    protected void onBubbleClick() {
//        Intent intent = new Intent(context, EaseBaiduMapActivity.class);
//        intent.putExtra("latitude", locBody.getLatitude());
//        intent.putExtra("longitude", locBody.getLongitude());
//        intent.putExtra("address", locBody.getAddress());
//        activity.startActivity(intent);
    }
    
    /*
	 * 点击地图消息listener
	 */
	protected class MapClickListener implements OnClickListener {

		LatLng location;
		String address;

		public MapClickListener(LatLng loc, String address) {
			location = loc;
			this.address = address;

		}

		@Override
		public void onClick(View v) {
		   
		}

	}


}
