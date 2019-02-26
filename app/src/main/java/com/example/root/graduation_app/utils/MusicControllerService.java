package com.example.root.graduation_app.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.RemoteController;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.view.KeyEvent;
import com.example.root.graduation_app.R;

/**
 * @author kuky.
 */
public class MusicControllerService extends NotificationListenerService implements RemoteController.OnClientUpdateListener {
    public  boolean registered;
    private RemoteController mRemoteController;
    private IBinder mBinder = new MusicBinder();
    private RemoteController.OnClientUpdateListener mMainOnClientUpdateListener, mSubOnClientUpdateListener;
    public String songTitle = "";
    public String songArtist = "";
    public long songDuration = -1;
    public Bitmap songArtwork = null;
    public int songState = -1;

    @Override
    public IBinder onBind(Intent intent) {
        registerController();
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public long getPosition() {
        if (mRemoteController != null && registered) {
            return mRemoteController.getEstimatedMediaPosition();
        }
        return 0;
    }

    public void seekTo(int progress, long duration) {
        try {
            if (mRemoteController != null && registered) {
                mRemoteController.seekTo(progress * duration / 100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册 controller
     */
    private void registerController() {
        mRemoteController = new RemoteController(this, this);

        try {
            registered = ((AudioManager) getSystemService(AUDIO_SERVICE)).registerRemoteController(mRemoteController);
        } catch (Exception e) {
            registered = false;
            e.printStackTrace();
        }

        if (registered) {
            try {
                /**
                 * 获取封面图片大小
                 */
//                mRemoteController.setArtworkConfiguration(ScreenUtils.dip2px(this, 35), ScreenUtils.dip2px(this, 35));
                mRemoteController.setSynchronizationMode(RemoteController.POSITION_SYNCHRONIZATION_CHECK);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
    }

    public void setMainClientUpdateListener(RemoteController.OnClientUpdateListener listener) {
        this.mMainOnClientUpdateListener = listener;
    }

    public void setSubClientUpdateListener(RemoteController.OnClientUpdateListener listener) {
        this.mSubOnClientUpdateListener = listener;
    }

    @Override
    public void onClientChange(boolean clearing) {
        if (mMainOnClientUpdateListener != null) {
            mMainOnClientUpdateListener.onClientChange(clearing);
        }

        if (mSubOnClientUpdateListener != null) {
            mSubOnClientUpdateListener.onClientChange(clearing);
        }
    }

    @Override
    public void onClientPlaybackStateUpdate(int state) {
        if (mMainOnClientUpdateListener != null) {
            mMainOnClientUpdateListener.onClientPlaybackStateUpdate(state);
        }

        if (mSubOnClientUpdateListener != null) {
            mSubOnClientUpdateListener.onClientPlaybackStateUpdate(state);
        }
    }

    @Override
    public void onClientPlaybackStateUpdate(int state, long stateChangeTimeMs, long currentPosMs, float speed) {
        songState = state;

        if (mMainOnClientUpdateListener != null) {
            mMainOnClientUpdateListener.onClientPlaybackStateUpdate(state, stateChangeTimeMs, currentPosMs, speed);
        }

        if (mSubOnClientUpdateListener != null) {
            mSubOnClientUpdateListener.onClientPlaybackStateUpdate(state, stateChangeTimeMs, currentPosMs, speed);
        }
    }

    @Override
    public void onClientTransportControlUpdate(int transportControlFlags) {
        if (mMainOnClientUpdateListener != null) {
            mMainOnClientUpdateListener.onClientTransportControlUpdate(transportControlFlags);
        }

        if (mSubOnClientUpdateListener != null) {
            mSubOnClientUpdateListener.onClientTransportControlUpdate(transportControlFlags);
        }
    }

    @Override
    public void onClientMetadataUpdate(RemoteController.MetadataEditor metadataEditor) {
        songTitle = metadataEditor.
                getString(MediaMetadataRetriever.METADATA_KEY_TITLE, "");

        songArtist = metadataEditor.
                getString(MediaMetadataRetriever.METADATA_KEY_ARTIST, "");

        songDuration = metadataEditor.
                getLong(MediaMetadataRetriever.METADATA_KEY_DURATION, -1);

        Bitmap defaultCover = BitmapFactory.decodeResource(getResources(), R.drawable.default_album);
        songArtwork = metadataEditor.
                getBitmap(RemoteController.MetadataEditor.BITMAP_KEY_ARTWORK, defaultCover);

        if (mMainOnClientUpdateListener != null) {
            mMainOnClientUpdateListener.onClientMetadataUpdate(metadataEditor);
        }

        if (mSubOnClientUpdateListener != null) {
            mSubOnClientUpdateListener.onClientMetadataUpdate(metadataEditor);
        }
    }

    public boolean sendMusicKeyEvent(int keyCode) {
        if (mRemoteController != null) {
            KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_DOWN, keyCode);
            boolean down = mRemoteController.sendMediaKeyEvent(keyEvent);
            keyEvent = new KeyEvent(KeyEvent.ACTION_UP, keyCode);
            boolean up = mRemoteController.sendMediaKeyEvent(keyEvent);
            return down && up;
        } else {
            long eventTime = SystemClock.uptimeMillis();
            KeyEvent key = new KeyEvent(eventTime, eventTime, KeyEvent.ACTION_DOWN, keyCode, 0);
            dispatchMediaKeyToAudioService(key);
            dispatchMediaKeyToAudioService(KeyEvent.changeAction(key, KeyEvent.ACTION_UP));
        }
        return false;
    }

    private void dispatchMediaKeyToAudioService(KeyEvent event) {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioManager != null) {
            try {
                audioManager.dispatchMediaKeyEvent(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class MusicBinder extends Binder {
        public MusicControllerService getServer() {
            return MusicControllerService.this;
        }
    }
}
