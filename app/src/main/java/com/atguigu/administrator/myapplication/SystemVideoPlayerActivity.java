package com.atguigu.administrator.myapplication;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class SystemVideoPlayerActivity extends AppCompatActivity {

    private VideoView videoview;
    //视频播放的地址
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_video_player);
        videoview = (VideoView) findViewById(R.id.videoview);

        getData();
        //设置视频加载的监听
        setListener();
        setData();
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
        videoview.setMediaController(new MediaController(this));
    }

    class MyOnPreparedListener implements MediaPlayer.OnPreparedListener {

        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            //开始播放
            videoview.start();
        }
    }

    class MyOnErrorListener implements MediaPlayer.OnErrorListener {
        @Override
        public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
            Toast.makeText(SystemVideoPlayerActivity.this,"视频播放出错了",Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    class MyOnCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            Toast.makeText(SystemVideoPlayerActivity.this,"视频播放完成",Toast.LENGTH_SHORT).show();

        }
    }
    /**
     * 得到视频播放的地址
     */
    private void getData() {
         uri = getIntent().getData();
    }


}
