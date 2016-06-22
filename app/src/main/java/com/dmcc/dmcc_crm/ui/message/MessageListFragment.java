package com.dmcc.dmcc_crm.ui.message;

import android.Manifest;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.dmcc.dmcc_crm.APP;
import com.dmcc.dmcc_crm.R;
import com.dmcc.dmcc_crm.base.MvpBaseFragmentV4;
import com.dmcc.dmcc_crm.customview.AppTitle;
import com.dmcc.dmcc_crm.ui.message.messageDetail.MessageDetailActivity;
import com.dmcc.dmcc_crm.ui.message.messageDetail.MessageDetailAdapter;
import com.dmcc.dmcc_crm.util.CursorUtil;
import com.gun0912.tedpermission.PermissionListener;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 记录模块住Fragment
 * Created by wushange on 2016/05/31.
 */
public class MessageListFragment extends MvpBaseFragmentV4<MessageListContract.MessageListView, MessageListPresenter> implements MessageListContract.MessageListView {
    @ViewInject(R.id.apptitle)
    AppTitle appTitle;
    @ViewInject(R.id.smlv_msg_list)
    SwipeMenuListView mListView;
    List<String> mList = new ArrayList<String>();
    private View mHeaderView;
    MessageDetailAdapter mAdapter;

    @Override
    public int bindLayout() {
        return R.layout.fragment_message_main_view;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public void initParms(Bundle parms) {

    }


    @Override
    public void initView(View view) {
        appTitle.init(AppTitle.AppTitleStyle.TITLE_RIGHT_BTN);
        appTitle.setTitle("记录");
        appTitle.setRightBtnClickClisener(R.mipmap.jiahao, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APP.checkPermission(getContext(), new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        showSweetSuccess("授权成功", "..");
                        CursorUtil.getPhoneContacts(getContext());
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> arrayList) {
                        showSweetError("授权失败", ".." + arrayList.toString());


                    }
                }, Manifest.permission.READ_CONTACTS);
            }
        });
        initHeadView();
        mAdapter = new MessageDetailAdapter(getContext(), mList);
        mListView.setAdapter(mAdapter);
        initMenuList();
    }


    @Override
    public void doBusiness(Context mContext) {

        presenter.getRecordDatas(6);

    }


    @Override
    public void lazyInitBusiness(Context mContext) {
    }


    /**
     * 初始化列表侧滑菜单
     */
    public void initMenuList() { // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getContext());
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                openItem.setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn_width_img) - 30);
                openItem.setTitle("Open");
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn_width_img) - 30);
                deleteItem.setTitle("删除");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        mListView.setMenuCreator(creator);
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                String item = mList.get(position);
                switch (index) {
                    case 0:
                        MessageDetailActivity.startActivity(getContext());
                        break;
                    case 1:
                        mList.remove(position);
                        LogUtil.e("---" + mListView.getChildCount());
                        showItemAnim(mListView.getChildAt(position +1));
                        mAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageDetailActivity.startActivity(getContext());
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(getContext(), position + " long click", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }


    public void showItemAnim(final View view) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_bottom_in);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setAlpha(1);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        view.startAnimation(animation);
    }

    /**
     * 初始化头部信息
     */
    private void initHeadView() {
        mHeaderView = LayoutInflater.from(getContext()).inflate(
                R.layout.item_message_header_view, null);
        RelativeLayout tuijian = (RelativeLayout) mHeaderView.findViewById(R.id.message_header_tuijian_layout);
        RelativeLayout share = (RelativeLayout) mHeaderView.findViewById(R.id.message_header_share_layout);
        tuijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("推荐");
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("收到的分享");
            }
        });
        mListView.addHeaderView(mHeaderView);
    }


    @Override
    public void OnGetRecordDatas(List<String> datas) {
        mList = datas;
        mAdapter.updataList(mList);
    }

    @Override
    public MessageListPresenter initPresenter() {
        return new MessageListPresenter();
    }

    @Override
    public void startLoading() {

    }

    @Override
    public void endLoading() {

    }
}

