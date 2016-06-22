package com.dmcc.dmcc_crm.ui.project;

import android.content.Context;
import android.view.ViewGroup;

import com.dmcc.dmcc_crm.bean.Project;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by Mr.Jude on 2015/7/18.
 */
public class PeojectListAdapter extends RecyclerArrayAdapter<Project> {
    public PeojectListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new PeojectListViewHolder(parent);
    }
}
