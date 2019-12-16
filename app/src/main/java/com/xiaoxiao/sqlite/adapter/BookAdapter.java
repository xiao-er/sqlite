package com.xiaoxiao.sqlite.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoxiao.sqlite.R;
import com.xiaoxiao.sqlite.data.BookData;

import java.util.List;

/**
 * @author: 潇潇
 * @create on:  2019/12/10
 * @describe:DOTO
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    private Context context;
    private OnItemClickListener onItemClickListener;
    private List<BookData> dataList;

    public BookAdapter(Context context, List<BookData> dataList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.dataList = dataList;
    }

    public void update(List<BookData> dataList) {
        this.dataList=dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setPosition(position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public int position;
        public TextView txt_name, txt_create_date, txt_edit_date;
        public ImageView img_edit, img_delete;


        public MyViewHolder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_create_date = itemView.findViewById(R.id.txt_create_date);
            txt_edit_date = itemView.findViewById(R.id.txt_edit_date);
            img_edit = itemView.findViewById(R.id.img_edit);
            img_delete = itemView.findViewById(R.id.img_delete);

            img_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.OnEdtClick(dataList.get(position).getId());
                    }
                }
            });
            img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.OnDeleteClick(dataList.get(position).getId());
                    }
                }
            });

        }


        public void setPosition(int pos) {
            this.position = pos;
            BookData data = dataList.get(position);
            txt_name.setText(data.getName());
            txt_create_date.setText("创建时间：" + data.getCreateDate());
            txt_edit_date.setText("修改时间：" + data.getEditDate());
        }
    }


    public interface OnItemClickListener {
        void OnItemClick();

        void OnEdtClick(int id);

        void OnDeleteClick(int id);
    }


}
