package com.mercacortex.videoview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoView_Activity extends AppCompatActivity {

    private VideoView mViView;
    private MediaController mMCnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view_);

        mViView = (VideoView) findViewById(R.id.videoView);

        // Accedo al espacio de nombres y le pongo la ruta completa
        mViView.setVideoPath("android.resource://"+getPackageName()+"/"+ R.raw.video);

        // Otra forma. El sistema lo parsea a formato uri
        // Uri miUri = Uri.parse("android:resource://"+getPackageName()+"/"+R.raw.video);

        // Otra forma
        // mViView.setVideoPath(Environment.getExternalStorageDirectory().getAbsolutePath()+"video");

        mMCnt = new MediaController(this);
        mViView.setMediaController(mMCnt);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mViView.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViView.resume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Guarda el segundo en el que se queda el video
        int posicion = mViView.getCurrentPosition();
        outState.putInt("position", posicion);
        // Guarda si el vídeo esta pausado
        outState.putBoolean("playing", mViView.isPlaying());
        // Al girar la pantalla pasa de nuevo por este método, por lo que no
        // se debe alterar el estado de la aplicación en onPause
    }

    @Override
    protected void onStop() {
        super.onStop();
        mViView.suspend();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mViView.pause();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Recupera el segundo en el que se quedó el video
        mViView.seekTo(savedInstanceState.getInt("position"));

        if(!savedInstanceState.getBoolean("playing"))
            mViView.pause();
    }
}
