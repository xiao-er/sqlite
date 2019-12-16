package com.xiaoxiao.sqlite;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaoxiao.sqlite.adapter.BookAdapter;
import com.xiaoxiao.sqlite.data.BookData;
import com.xiaoxiao.sqlite.utils.DBUtils;
import com.xiaoxiao.sqlite.utils.PermissionUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BookAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ImageView img_add;
    private TextView txt_not_data;

    ///////////
    private AlertDialog.Builder builder;
    private DBUtils dbUtils;
    private List<BookData> bookDataList = new ArrayList<>();
    private BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findview();
        init();
        setListener();

    }

    private void init() {
        PermissionUtils.isPowerRequest(MainActivity.this, PermissionUtils.permission, 101);
        dbUtils = new DBUtils();
        dbUtils.createBookTable();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        bookAdapter = new BookAdapter(this, bookDataList, this);
        recyclerView.setAdapter(bookAdapter);
        updateBookData();
    }

    private void findview() {
        txt_not_data = findViewById(R.id.txt_not_data);
        recyclerView = findViewById(R.id.recyclerView);
        img_add = findViewById(R.id.img_add);
    }

    private void setListener() {
        img_add.setOnClickListener(this);
    }

    /**
     * 获取数据
     */
    private void updateBookData() {
        bookDataList.clear();
        Cursor cursor = dbUtils.queryAllBookData();
        if (cursor == null) {
            return;
        }
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String createDate = cursor.getString(cursor.getColumnIndex("createDate"));
            String editDate = cursor.getString(cursor.getColumnIndex("editDate"));
            BookData data = new BookData();
            data.setName(name);
            data.setCreateDate(createDate);
            data.setEditDate(editDate);
            data.setId(id);
            bookDataList.add(data);
        }
        if (bookDataList.size() == 0) {
            txt_not_data.setVisibility(View.VISIBLE);
        } else {
            txt_not_data.setVisibility(View.GONE);
            bookAdapter.update(bookDataList);
        }

    }


    /**
     * 一个输入框的 dialog
     */
    private void showInput() {
        final EditText editText = new EditText(this);
        builder = new AlertDialog.Builder(this).setTitle("输入框dialog").setView(editText)
                .setPositiveButton("读取输入框内容", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String value = editText.getText().toString().trim();
                        if (!value.equals("")) {
                            dbUtils.add(value);
                            updateBookData();
                        }
                    }
                });
        builder.create().show();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_add:
                showInput();
                break;
        }
    }

    @Override
    public void OnItemClick() {

    }

    @Override
    public void OnEdtClick(final int id) {
        final EditText editText = new EditText(this);
        builder = new AlertDialog.Builder(this).setTitle("输入框dialog").setView(editText)
                .setPositiveButton("读取输入框内容", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String value = editText.getText().toString().trim();
                        if (!value.equals("")) {
                            dbUtils.edit(id, value);
                            updateBookData();
                        }
                    }
                });
        builder.create().show();
    }

    @Override
    public void OnDeleteClick(final int id) {

        new AlertDialog.Builder(MainActivity.this).setTitle("确定")//设置对话框标题
                .setMessage("确定要删除吗？")//设置显示的内容
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        dbUtils.delete(id);
                        updateBookData();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//取消按钮的响应事件

            }
        }).show();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }
}
