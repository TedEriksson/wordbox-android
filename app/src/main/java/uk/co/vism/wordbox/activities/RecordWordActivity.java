package uk.co.vism.wordbox.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import uk.co.vism.wordbox.R;
import uk.co.vism.wordbox.models.Word;

@EActivity(R.layout.activity_record_word)
public class RecordWordActivity extends Activity implements SurfaceHolder.Callback {
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    @ViewById
    SurfaceView cameraView;
    @ViewById
    Button cameraButton;

    private Word word;
    private Camera camera;
    private MediaRecorder recorder;
    private SurfaceHolder holder;
    private boolean recording = false;
    private File path;

    @AfterViews
    void init() {
        //loadWord();

        holder = cameraView.getHolder();
        holder.addCallback(this);

        recorder = new MediaRecorder();
        camera = getCamera();
        initRecorder();
    }

    private void initRecorder() {
        path = getOutputMediaFile(MEDIA_TYPE_VIDEO);
        CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW);

        // step 1: unlock and set camera
        camera.unlock();
        recorder.setCamera(camera);

        // step 2: set sources
        recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // step 3: set profile
        recorder.setProfile(profile);

        // step 4: set output file
        recorder.setOutputFile(path.toString());

        // step 5: set the preview
        recorder.setPreviewDisplay(holder.getSurface());
        recorder.setMaxDuration(50000); // 50 seconds
        recorder.setMaxFileSize(5000000); // Approximately 5 megabytes
    }

    private Camera getCamera() {
        Camera camera = null;

        for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);

            if(cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    camera = Camera.open(i);
                    camera.setDisplayOrientation(90);

                    Camera.Parameters params = camera.getParameters();
                    params.setRecordingHint(true);  // recording videos
                    params.setPreviewSize(params.getSupportedVideoSizes().get(0).width, params.getSupportedVideoSizes().get(0).height);
                    camera.setParameters(params);
                } catch (Exception e){
                    Log.d("getCamera:" + e.getClass(), e.getMessage());
                }
            }
        }

        return camera;
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "wordbox");

        // This location works best if you want the created images to be shared between applications and persist after your app has been uninstalled.
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "word_.jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "word_.mp4");
        } else {
            return null;
        }

        return mediaFile;
    }

    private void prepareRecorder() {
        recorder.setPreviewDisplay(holder.getSurface());

        try {
            recorder.prepare();
        } catch (IOException|IllegalStateException e) {
            e.printStackTrace();
            finish();
        }
    }

    @Click(R.id.cameraButton)
    public void toggleRecord() {
        if (recording) {
            recording = false;
            recorder.stop();
            camera.lock();
            cameraButton.setText("Retry");

            MediaPlayer mediaPlayer = MediaPlayer.create(RecordWordActivity.this, Uri.fromFile(path));
            mediaPlayer.start();

            initRecorder();     // let's initRecorder so we can record again
            prepareRecorder();
        } else {
            recording = true;
            recorder.start();
            cameraButton.setText("Stop");
        }
    }

    @Click
    public void sendButton() {
        Toast.makeText(RecordWordActivity.this, "Send recording", Toast.LENGTH_SHORT).show();
    }

    public void surfaceCreated(SurfaceHolder holder) {
        prepareRecorder();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (recording) {
            recording = false;
            recorder.stop();
            camera.lock();
        }

        recorder.release();
        finish();
    }

    private void loadWord() {
        Realm realm = null;
        try {
            realm = Realm.getInstance(RecordWordActivity.this);
            word = realm.where(Word.class).equalTo("id", getIntent().getIntExtra("word_id", 0)).findFirst();
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
    }
}