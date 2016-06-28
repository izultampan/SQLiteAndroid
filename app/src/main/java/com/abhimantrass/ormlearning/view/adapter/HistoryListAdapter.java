package com.abhimantrass.ormlearning.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abhimantrass.ormlearning.R;
import com.abhimantrass.ormlearning.db.dao.AttendanceDao;
import com.abhimantrass.ormlearning.utility.CommonUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zul on 6/27/2016.
 */
public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {

    public interface HistoryAdapterClickListener {
        void onClicked(int id, AttendanceDao attendance);
    }

    private HistoryAdapterClickListener clickListener;

    private List<AttendanceDao> dataList;

    public HistoryListAdapter() {
        this(null);
    }
    public HistoryListAdapter(List<AttendanceDao> dataList) {
        setDataList(dataList, false);
    }

    public void setDataList(List<AttendanceDao> dataList, boolean isNotify) {
        if (dataList != null) {
            this.dataList = dataList;
        }
        else {
            this.dataList = new ArrayList<>();
        }

        if (isNotify) {
            notifyDataSetChanged();
        }
    }

    public AttendanceDao getData(int index) {
        return dataList.get(index);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = View.inflate(parent.getContext(), R.layout.item_history_list, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AttendanceDao dao = dataList.get(position);

        holder.tvTime.setText(new SimpleDateFormat("dd/MM/yy").format(new Date(Long.parseLong(dao.attendanceTime))));
        holder.tvType.setText(CommonUtil.fromIdToAttendanceType(holder.tvType.getContext(), dao.attendanceType));
        holder.tvRemark.setText(holder.tvRemark.getContext().getString(R.string.remark));
        holder.setData(dao);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setClickListener(HistoryAdapterClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener {

        public TextView tvTime;
        public TextView tvType;
        public TextView tvRemark;

        public AttendanceDao data;

        public ViewHolder(View view) {
            super(view);
            tvTime = (TextView) view.findViewById(R.id.txt_time);
            tvType = (TextView) view.findViewById(R.id.txt_type);
            tvRemark = (TextView) view.findViewById(R.id.txt_remark);

            view.findViewById(R.id.btn_update).setOnClickListener(this);
            view.findViewById(R.id.btn_delete).setOnClickListener(this);
        }

        public void setData(AttendanceDao data) {
            this.data = data;
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onClicked(v.getId(), data);
            }
        }
    }
}