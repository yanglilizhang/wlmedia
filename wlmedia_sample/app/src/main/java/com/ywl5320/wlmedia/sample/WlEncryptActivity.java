package com.ywl5320.wlmedia.sample;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ywl5320.wlmedia.WlMedia;
import com.ywl5320.wlmedia.enums.WlComplete;
import com.ywl5320.wlmedia.enums.WlPlayModel;
import com.ywl5320.wlmedia.enums.WlSourceType;
import com.ywl5320.wlmedia.listener.WlOnCompleteListener;
import com.ywl5320.wlmedia.listener.WlOnDecryptListener;
import com.ywl5320.wlmedia.listener.WlOnErrorListener;
import com.ywl5320.wlmedia.listener.WlOnPreparedListener;
import com.ywl5320.wlmedia.listener.WlOnVideoViewListener;
import com.ywl5320.wlmedia.log.WlLog;
import com.ywl5320.wlmedia.surface.WlSurfaceView;

/**
 * Created by wanli on 2019/12/4
 */
public class WlEncryptActivity extends AppCompatActivity {

    private WlSurfaceView wlSurfaceView;
    private WlMedia wlMedia;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt_layout);
        wlSurfaceView = findViewById(R.id.wlsurfaceview);

        wlMedia = new WlMedia();
        wlMedia.setPlayModel(WlPlayModel.PLAYMODEL_AUDIO_VIDEO);
        wlMedia.setSourceType(WlSourceType.ENCRYPT_FILE);
        wlSurfaceView.setWlMedia(wlMedia);
        wlMedia.setOnDecryptListener(new WlOnDecryptListener() {
            @Override
            public byte[] decrypt(byte[] encrypt_data) {
                int length = encrypt_data.length;
                for(int i = 0; i < length; i++)
                {
                    encrypt_data[i] = (byte) ((int)encrypt_data[i] ^ 666);
                }
                WlLog.d("decrypt");
                return encrypt_data;
            }
        });

        wlMedia.setOnErrorListener(new WlOnErrorListener() {
            @Override
            public void onError(int code, String msg) {
                WlLog.d("error:" + msg);
                Toast.makeText(WlEncryptActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });

        wlMedia.setOnPreparedListener(new WlOnPreparedListener() {
            @Override
            public void onPrepared() {
                wlMedia.start();
            }
        });
        wlMedia.setOnCompleteListener(new WlOnCompleteListener() {
            @Override
            public void onComplete(WlComplete type) {
                WlLog.d("onComplete");
            }
        });

        wlSurfaceView.setOnVideoViewListener(new WlOnVideoViewListener() {
            @Override
            public void initSuccess() {
                WlLog.d("initSuccess");
                wlMedia.setSource(WlAssetsUtil.getAssetsFilePath(WlEncryptActivity.this, "fhcq-ylgzy-dj-encrypt.mkv"));
                wlMedia.prepared();
            }

            @Override
            public void moveX(double value, int move_type) {

            }

            @Override
            public void onSingleClick() {

            }

            @Override
            public void onDoubleClick() {

            }

            @Override
            public void moveLeft(double value, int move_type) {

            }

            @Override
            public void moveRight(double value, int move_type) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        wlMedia.exit();
    }
}
