package cn.netkiller.voice;

import android.media.MediaRecorder;

import java.io.File;
import java.io.IOException;

public class Audio {

    private boolean isRecord = false;

    private MediaRecorder mediaRecorder;

    private Audio() {

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        mediaRecorder.setOutputFile("a.3gp");

    }

    private static Audio instance;

    public synchronized static Audio getInstance() {
        if (instance == null)
            instance = new Audio();
        return instance;
    }

    public void start() {

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();

            isRecord = true;

        } catch (IOException ex) {
            ex.printStackTrace();

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
