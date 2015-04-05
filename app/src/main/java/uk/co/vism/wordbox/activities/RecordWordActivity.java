package uk.co.vism.wordbox.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import uk.co.vism.wordbox.R;

@EActivity(R.layout.activity_record_word)
public class RecordWordActivity extends Activity { // implements View.OnClickListener, SurfaceHolder.Callback {
    private static final int REQUEST_VIDEO_CAPTURE = 42;

    @ViewById(R.id.videoView)
    VideoView videoView;

    @AfterViews
    void init() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            videoView.setVideoURI(videoUri);

            // we have the uri of the video, so we need to launch an upload task in the background
            // providing feedback to the user about the upload state, and finish once it's done
            finish();
        }
    }
}