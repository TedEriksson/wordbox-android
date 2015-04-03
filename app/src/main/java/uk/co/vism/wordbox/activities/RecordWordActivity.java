package uk.co.vism.wordbox.activities;

import android.app.Activity;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

import uk.co.vism.wordbox.R;

@EActivity(R.layout.activity_record_word)
public class RecordWordActivity extends Activity implements View.OnClickListener, SurfaceHolder.Callback
{
    @ViewById(R.id.cameraSurfaceView)
    SurfaceView cameraView;

    private MediaRecorder recorder;
    private SurfaceHolder holder;
    private boolean recording = false;

    @AfterViews
    void init()
    {
        recorder = new MediaRecorder();
        initRecorder();

        holder = cameraView.getHolder();
        holder.addCallback(RecordWordActivity.this);

        cameraView.setClickable(true);
        cameraView.setOnClickListener(RecordWordActivity.this);
    }

    private void initRecorder()
    {
        CamcorderProfile profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);

        recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        recorder.setProfile(profile);
        recorder.setOutputFile(Environment.getExternalStorageDirectory().getPath() + "/video.mp4");
        recorder.setMaxDuration(50000);     // 50 seconds
        recorder.setMaxFileSize(5000000);   // approximately 5 megabytes
    }

    private void prepareRecorder()
    {
        recorder.setPreviewDisplay(holder.getSurface());

        try                             { recorder.prepare(); }
        catch(IllegalStateException e)  { e.printStackTrace(); finish(); }
        catch(IOException e)            { e.printStackTrace(); finish(); }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) { prepareRecorder(); }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        if(recording)
        {
            recorder.stop();
            recording = false;
        }

        recorder.release();
        finish();
    }

    @Override
    public void onClick(View v)
    {
        if(recording)
        {
            recording = false;
            recorder.stop();

            initRecorder();
            prepareRecorder();
        }
        else
        {
            try
            {
                recording = true;
                recorder.start();
            }
            catch(Exception e)      { e.printStackTrace(); recording = false; }
        }
    }
}