package com.atguigu.administrator.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.atguigu.administrator.myapplication.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemVideoPlayerActivity extends AppCompatActivity implements View.OnClickListener {


    private VideoView videoview;
    //视频播放的地址
    private Uri uri;

    //进度更新
    private static final int PROGRESS = 0;


    private LinearLayout llTop;
    private TextView tvName;
    private ImageView ivBattery;
    private TextView tvSystemtime;
    private Button btnVoice;
    private SeekBar seekbarVoice;
    private Button btnSwichePlayer;
    private LinearLayout llBottom;
    private TextView tvCurrenttime;
    private SeekBar seekbarVideo;
    private TextView tvDuration;
    private Button btnExit;
    private Button btnPre;
    private Button btnStartPause;
    private Button btnNext;
    private Button btnSwichScreen;
    private Utils utils;
    private MyBroadcastReceiver receiver;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-01-09 18:42:27 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        setContentView(R.layout.activity_system_video_player);
        videoview = (VideoView) findViewById(R.id.videoview);
        llTop = (LinearLayout) findViewById(R.id.ll_top);
        tvName = (TextView) findViewById(R.id.tv_name);
        ivBattery = (ImageView) findViewById(R.id.iv_battery);
        tvSystemtime = (TextView) findViewById(R.id.tv_systemtime);
        btnVoice = (Button) findViewById(R.id.btn_voice);
        seekbarVoice = (SeekBar) findViewById(R.id.seekbar_voice);
        btnSwichePlayer = (Button) findViewById(R.id.btn_swiche_player);
        llBottom = (LinearLayout) findViewById(R.id.ll_bottom);
        tvCurrenttime = (TextView) findViewById(R.id.tv_currenttime);
        seekbarVideo = (SeekBar) findViewById(R.id.seekbar_video);
        tvDuration = (TextView) findViewById(R.id.tv_duration);
        btnExit = (Button) findViewById(R.id.btn_exit);
        btnPre = (Button) findViewById(R.id.btn_pre);
        btnStartPause = (Button) findViewById(R.id.btn_start_pause);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnSwichScreen = (Button) findViewById(R.id.btn_swich_screen);

        btnVoice.setOnClickListener(this);
        btnSwichePlayer.setOnClickListener(this);
        btnExit.setOnClickListener(this);
        btnPre.setOnClickListener(this);
        btnStartPause.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnSwichScreen.setOnClickListener(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();
        findViews();
        getData();
        //设置视频加载的监听
        setListener();
        setData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        utils = new Utils();

        //注册监听电池电量的变化
        receiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        //监听电量变化
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(receiver, filter);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PROGRESS://视频播放进度的更新
                    int currentPosition = videoview.getCurrentPosition();
                    //设置视频更新
                    seekbarVideo.setProgress(currentPosition);

                    //设置播放进度的时间
                    tvCurrenttime.setText(utils.stringForTime(currentPosition));

                    //得到系统时间并更新
                    tvSystemtime.setText(getSystemTime());

                    //不断发消息
                    removeMessages(PROGRESS);
                    sendEmptyMessageDelayed(PROGRESS, 1000);

                    break;
            }

        }
    };

    /**
     * 得到系统时间
     * @return
     */
    private String getSystemTime() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        return format.format(new Date());
    }

    class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //得到电量：0—100
            int level = intent.getIntExtra("level", 0);
            //主线程
            setBattery(level);
        }
    }

    private void setBattery(int level) {
        if (level <= 0) {
            ivBattery.setImageResource(R.drawable.ic_battery_0);
        } else if (level <= 10) {
            ivBattery.setImageResource(R.drawable.ic_battery_10);
        } else if (level <= 20) {
            ivBattery.setImageResource(R.drawable.ic_battery_20);
        } else if (level <= 40) {
            ivBattery.setImageResource(R.drawable.ic_battery_40);
        } else if (level <= 60) {
            ivBattery.setImageResource(R.drawable.ic_battery_60);
        } else if (level <= 80) {
            ivBattery.setImageResource(R.drawable.ic_battery_80);
        } else if (level <= 100) {
            ivBattery.setImageResource(R.drawable.ic_battery_100);
        } else {
            ivBattery.setImageResource(R.drawable.ic_battery_100);
        }
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-01-09 18:42:27 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == btnVoice) {
            // Handle clicks for btnVoice
        } else if (v == btnSwichePlayer) {
            // Handle clicks for btnSwichePlayer
        } else if (v == btnExit) {
            // Handle clicks for btnExit
        } else if (v == btnPre) {
            // Handle clicks for btnPre
        } else if (v == btnStartPause) {
            // Handle clicks for btnStartPause
            if (videoview.isPlaying()) {//是否在播放
                //当前播放的要设置为暂停状态
                videoview.pause();
                //按钮状态——播放状态
                btnStartPause.setBackgroundResource(R.drawable.btn_start_selector);
            } else {
                //当前暂停状态要 设置为播放状态
                videoview.start();
                //按钮状态——暂停状态
                btnStartPause.setBackgroundResource(R.drawable.btn_pause_selector);
            }
        } else if (v == btnNext) {
            // Handle clicks for btnNext
        } else if (v == btnSwichScreen) {
            // Handle clicks for btnSwichScreen
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {

        //释放资源——先释放孩子的
        if(receiver !=null){
            unregisterReceiver(receiver);
            receiver = null;
        }

        //消息移除
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    /**
     * 设置视频播放的地址
     */
    private void setData() {
        videoview.setVideoURI(uri);
    }

    private void setListener() {
        //设置视频播放监听：  准备好的监听    播放出错的监听   播放完成的监听
        videoview.setOnPreparedListener(new MyOnPreparedListener());
        videoview.setOnErrorListener(new MyOnErrorListener());
        videoview.setOnCompletionListener(new MyOnCompletionListener());

        //设置控制面板
        //videoview.setMediaController(new MediaController(this));
        //设置视频的拖动监听
        seekbarVideo.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener());
    }

    class MyOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

        /**
         * 状态变化的时候回调
         *
         * @param seekBar
         * @param progress 当前改变的进度——要拖动到的位置
         * @param fromUser 用户导致的改变 true，否则为false
         */
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                videoview.seekTo(progress);
            }
        }

        /**
         * 手指按一下的时候回调
         *
         * @param seekBar
         */
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        /**
         * 当手指离开的时候回调
         *
         * @param seekBar
         */
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    class MyOnPreparedListener implements MediaPlayer.OnPreparedListener {

        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            //开始播放
            videoview.start();

            //准备好的时候
            //视频的总播放时长和seekbar 关联起来
            int duration = videoview.getDuration();
            seekbarVideo.setMax(duration);

            //设置总时长
            tvDuration.setText(utils.stringForTime(duration));

            //发消息
            handler.sendEmptyMessage(PROGRESS);
        }
    }

    class MyOnErrorListener implements MediaPlayer.OnErrorListener {
        @Override
        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
            Toast.makeText(SystemVideoPlayerActivity.this, "视频播放出错了", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    class MyOnCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Toast.makeText(SystemVideoPlayerActivity.this,"视频播放完成",Toast.LENGTH_SHORT).show();
            //开始播放
            videoview.start();
            //准备好的时候
            //视频的总播放时长和seekbar 关联起来
            int duration = videoview.getDuration();
            seekbarVideo.setMax(duration);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {


            return true;
        }
        return true;
    }

    /**
     * 得到视频播放的地址
     */
    private void getData() {
        uri = getIntent().getData();
    }


}
