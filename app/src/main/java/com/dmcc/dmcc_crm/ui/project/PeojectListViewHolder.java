package com.dmcc.dmcc_crm.ui.project;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmcc.dmcc_crm.R;
import com.dmcc.dmcc_crm.bean.Project;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;


/**
 * Created by Mr.Jude on 2015/2/22.
 */
public class PeojectListViewHolder extends BaseViewHolder<Project> {
    private ImageView mPicon;
    private TextView mPName;
    private TextView mPcom_name;


    public PeojectListViewHolder(ViewGroup parent) {
        super(parent, R.layout.item_project_view);
        mPicon = $(R.id.iv_project_icon);
        mPName = $(R.id.tv_project_name);
        mPcom_name = $(R.id.tv_peoject_comname);
    }

    @Override
    public void setData(final Project person) {
        mPName.setText(person.getPname());
        mPcom_name.setText(person.getCompanyName());
    }
}
