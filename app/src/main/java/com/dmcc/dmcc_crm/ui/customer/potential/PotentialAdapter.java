package com.dmcc.dmcc_crm.ui.customer.potential;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.dmcc.dmcc_crm.R;
import com.dmcc.dmcc_crm.bean.User;
import com.dmcc.dmcc_crm.util.StringUtil;
import com.wushange.commsdk.util.DialogUtil;

import java.util.ArrayList;
import java.util.List;

public class PotentialAdapter extends BaseAdapter implements SectionIndexer,
        Filterable {
    private MemberHandler userHandler;
    private List<User> members;
    private LayoutInflater inflater;
    private Context context;

    public PotentialAdapter(Context context, List<User> members) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.members = members;
    }


    public void updateDatas(List<User> members) {
        this.members = members;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return members != null ? members.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        if (members != null && position > 0 && position < members.size()) {
            return members.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (members != null && position > 0 && position < members.size()) {
            return position;
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {

            final User member = members.get(position);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_myfollow_view,
                        null);
                userHandler = new MemberHandler();
                userHandler.userName = (TextView) convertView
                        .findViewById(R.id.item_custom_name);
                userHandler.userCompany = (TextView) convertView
                        .findViewById(R.id.item_custom_comname);
                userHandler.call = (ImageView) convertView
                        .findViewById(R.id.item_custom_call);
                userHandler.firstLetter = (TextView) convertView
                        .findViewById(R.id.first_letter_view);
                convertView.setTag(userHandler);
            } else {
                userHandler = (MemberHandler) convertView.getTag();

            }
            userHandler.userName.setText(member.userName);
            userHandler.call.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    DialogUtil.showCustomDialog(context, true, "拨打电话？", "110", "确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }

                    }, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                }
            });

            // 标示
            boolean flag = false;
            String curLetter = member.firstLetter;
            if (0 == position) {
                if (curLetter != null)
                    flag = true;
            } else {
                String lastLetter = members.get(position - 1).firstLetter;
                if (curLetter != null && lastLetter != null) {
                    if (!curLetter.equals(lastLetter)) {
                        flag = true;
                    }
                } else {
                    flag = true;
                }
            }

            if (flag) {
                userHandler.firstLetter.setVisibility(View.VISIBLE);
                userHandler.firstLetter.setText(member.getFirstLetter());
            } else {
                userHandler.firstLetter.setVisibility(View.GONE);
            }
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        }

        return convertView;
    }

    class MemberHandler {
        public TextView userName;
        public TextView userCompany;
        public TextView firstLetter;
        public ImageView call;
    }

    @Override
    public int getPositionForSection(int section) {
        for (int i = 0; i < members.size(); i++) {
            String l = members.get(i).firstLetter;

            if (l.length() <= 0) {
                l = "0";
            }

            char firstChar = l.toUpperCase().charAt(0);

            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new UserFilter(members);
        return filter;
    }

    UserFilter filter;

    public User getUserForIndex(int index) {
        return members.get(index);
    }

    public class UserFilter extends Filter {

        private List<User> list;

        public UserFilter(List<User> list) {
            this.list = list;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                results.values = list;
                results.count = list.size();
            } else {
                List<User> mList = new ArrayList<User>();
                for (User user : members) {
                    if (StringUtil.isNotBlank(user.userName)
                            && user.userName.toUpperCase().contains(
                            constraint.toString().toUpperCase())
                            || StringUtil.isNotBlank(user.userPhone)
                            && user.userPhone.toUpperCase().contains(
                            constraint.toString().toUpperCase())) {
                        mList.add(user);
                    }
                }
                results.values = mList;
                results.count = mList.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence arg0, FilterResults results) {
            members = (List<User>) results.values;
            notifyDataSetChanged();
        }

    }

}