package cn.netkiller.voice;

import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Audio {

    private boolean isRecord = false;

    private MediaRecorder mediaRecorder;
    private String filename;

    private Audio() {

    }

    private static Audio instance;

    public synchronized static Audio getInstance() {
        if (instance == null)
            instance = new Audio();
        return instance;
    }

    public String getFilename() {
        return filename;
    }

    public void start() {

        if (mediaRecorder == null) {

            String path = Environment.getExternalStorageDirectory().getPath();
            String folder = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String name = new SimpleDateFormat("hhmmss").format(new Date());
            new File(path, folder).mkdirs();

            filename = String.format("%s/%s/%s.3gp", path, folder, name);
            Log.e("Voice", "voice path " + filename);

            try {

                mediaRecorder = new MediaRecorder();
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
                mediaRecorder.setOutputFile(filename);
                mediaRecorder.prepare();
                mediaRecorder.start();

                isRecord = true;

            } catch (IOException ex) {
                ex.printStackTrace();

            }
        }

    }

    public void stop() {
        if (mediaRecorder != null && isRecord) {
            System.out.println("stopRecord");
            isRecord = false;
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

}
