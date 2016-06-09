package com.phoneguardian.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.phoneguardian.R;
import com.phoneguardian.adapter.TelecomAdapter;
import com.phoneguardian.base.BaseActivity;
import com.phoneguardian.db.TeleComDao;
import com.phoneguardian.entity.TeleComNum;
import com.phoneguardian.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Random;

import butterknife.InjectView;

/**
 * Created by Administrator on 2016/6/1.
 */
public class TelecommunicationsActivity extends BaseActivity {
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.btn_add_number)
    Button btnAddNumber;
    @InjectView(R.id.lv_list)
    ListView lvList;
    @InjectView(R.id.pb_loading)
    ProgressBar pbLoading;


   /* @InjectView(R.id.et_number)
    EditText etNumber;
    @InjectView(R.id.rb_phone)
    RadioButton rbPhone;
    @InjectView(R.id.rb_sms)
    RadioButton rbSms;
    @InjectView(R.id.rb_all)
    RadioButton rbAll;
    @InjectView(R.id.rg_group)
    RadioGroup rgGroup;
    @InjectView(R.id.btn_cancel)
    Button btnCancel;
    @InjectView(R.id.btn_ok)
    Button btnOk;*/

    private TelecomAdapter telecomAdapter;
    private ArrayList<TeleComNum> teleComNumList;
    private boolean isLoading = false;
    private TeleComDao mNumberDao;
    private Handler mHandler;

    public static void staticActivity(Context context) {
        Intent intent = new Intent(context, TelecommunicationsActivity.class);
        context.startActivity(intent);
    }



    @Override
    public void initVariables() {
        mNumberDao = mNumberDao.getInstance(this);//获得实例

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (telecomAdapter == null) {// 第一页
                    telecomAdapter = new TelecomAdapter(TelecommunicationsActivity.this, mNumberDao, teleComNumList);
                    // 给listview设置数据
                    lvList.setAdapter(telecomAdapter);// 这个方法导致数据默认跑到第0个位置
                } else {
                    //更新列表数据
                    telecomAdapter.notifyDataSetChanged();
                }

                pbLoading.setVisibility(View.GONE);
                isLoading = false;

            }
        };

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_telecom_listview);
       // ButterKnife.inject(this);


        //listview 添加数据
        lvList.setOnScrollListener(new AbsListView.OnScrollListener() {//这边AbsListView,学习一下
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    // 获取最后一个可见item的位置
                    int lastPosition = lvList.getLastVisiblePosition();
                    if (lastPosition >= teleComNumList.size() - 1 && !isLoading) {
                        int totalCount = mNumberDao.getTotalCount();
                        if (teleComNumList.size() >= totalCount) {
                            //说明没有更多的数据了
                            ToastUtils.showToast(TelecommunicationsActivity.this, "没有更多的数据了");
                            return;
                        }
                        // 加载下一页
                        loadBlackNumber();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        //按钮的点击事件,添加拦截
        btnAddNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddLbackNumber();
            }
        });


    }

    /**
     * 设置对话框，添加自定义的view，并添加事件
     */
    private void AddLbackNumber() {
        final AlertDialog blackDialog = new AlertDialog.Builder(TelecommunicationsActivity.this).create();
        View view = View.inflate(this, R.layout.activity_add_to_blacklist_dialog, null);
        final EditText etNumber = (EditText) view.findViewById(R.id.et_number);
        Button btnOk = (Button) view.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);
        final RadioGroup rgGroup = (RadioGroup) view.findViewById(R.id.rg_group);

        /*添加*/
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputNumber = etNumber.getText().toString().trim();
                if (!TextUtils.isEmpty(inputNumber)) {
                    int getCheckId = rgGroup.getCheckedRadioButtonId();
                    int mode = 1;
                    switch (getCheckId) {
                        case R.id.rb_phone:
                            mode = 1;
                            break;
                        case R.id.rb_sms:
                            mode = 2;
                            break;
                        case R.id.rb_all:
                            mode = 3;
                            break;
                        default:
                            break;
                    }
                    mNumberDao.add(inputNumber, mode);
                    teleComNumList.add(0, new TeleComNum(inputNumber, mode));
                    telecomAdapter.notifyDataSetChanged();
                    blackDialog.dismiss();
                }

            }
        });

//        取消
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blackDialog.dismiss();
            }
        });

        blackDialog.setView(view);
        blackDialog.show();
    }



    /**
     * 加载ListView的下一页
     */
    private void loadBlackNumber() {
        pbLoading.setVisibility(View.VISIBLE);
        isLoading = true;
        new Thread() {
            @Override
            public void run() {
                if (teleComNumList == null) {
                    //加载数据
                    //添加模拟数据
                    if (mNumberDao.getTotalCount() == 0)
                        addMockData();

                    // 加载第一页数据
                    teleComNumList = mNumberDao.findPart(0);// 20条数据
                } else {
                    // 给集合添加一个集合, 集合相加
                    teleComNumList.addAll(mNumberDao.findPart(teleComNumList.size()));
                }
                mHandler.sendEmptyMessage(0);

            }
        }.start();


    }

    private void addMockData() {
        for (int i = 0; i < 100; i++) {
            Random random = new Random();
            int mode = random.nextInt(3) + 1;
            if (i < 10) {
                mNumberDao.add("1341234567" + i, mode);
            } else {
                mNumberDao.add("135123456" + i, mode);
            }
        }
    }

    @Override
    protected void loadData() {
        loadBlackNumber();
    }


}
