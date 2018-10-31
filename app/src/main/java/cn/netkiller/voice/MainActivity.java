package cn.netkiller.voice;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RECORD_AUDIO = 10;
    private Button record;
    private Button play;
    private MediaRecorder mediaRecorder;
    private TextView status;

    private MediaPlayer mediaPlayer;
    private String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        status = (TextView) findViewById(R.id.status);
        record = (Button) findViewById(R.id.record);
        play = (Button) findViewById(R.id.play);

        record.setOnClickListener(this);
        play.setOnClickListener(this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO);
        }

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.record:
//                Audio audio = Audio.getInstance();
                if (record.getText().equals("Record")) {
//                    audio.start();
                    this.start();

                } else {
//                    audio.stop();
                    this.stop();
                }
                break;
            case R.id.play:
                play();
                status.setText("播放录音");
                break;
        }
    }

    //
    private void start() {

        record.setText("Stop");
        status.setText("开启录音，请对准话筒讲话");

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        String dir = Environment.getExternalStorageDirectory().getPath();
        String date = new SimpleDateFormat("yyyy-MM-ddhhmmss").format(new Date());
//            new File(dir,date).mkdirs();

        filename = String.format("%s/%s.3gp", dir, date);

        Log.e("Voice", "voice path " + filename);

        mediaRecorder.setOutputFile(filename);

//            mediaRecorder.setOutputFile("/dev/null");
        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.start();

    }

    private void stop() {
        record.setText("Record");
        status.setText("录音停止");

        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void play() {
        this.stop();
        if (filename == null) {
            Toast.makeText(getApplicationContext(), "请先录音", Toast.LENGTH_SHORT).show();
            return;
        }
        try {

            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                mediaPlayer.pause();
                mediaPlayer.reset();
            } else {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(filename);
                mediaPlayer.prepare();
                mediaPlayer.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

}
