package com.jeremy.utils;

import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class AudioReader {

    private static AudioReader audioReader;

    private final int format;
    private final int samplerate;
    private final int totalBytes;
    private final int bytesPerFrame;
    private final ByteBuffer data;
 
    private final AudioInputStream audioStream;
    private final byte[] dataArray;

    private AudioReader(AudioInputStream stream) {
        this.audioStream = stream;
        AudioFormat audioFormat = stream.getFormat();
        format = getOpenAlFormat(audioFormat.getChannels(), audioFormat.getSampleSizeInBits());
        this.samplerate = (int) audioFormat.getSampleRate();
        this.bytesPerFrame = audioFormat.getFrameSize();
        this.totalBytes = (int) (stream.getFrameLength() * bytesPerFrame);
        this.data = BufferUtils.createByteBuffer(totalBytes);
        this.dataArray = new byte[totalBytes];
        loadData();
    }

    public int getFormat() {
        return format;
    }

    public int getSamplerate() {
        return samplerate;
    }

    public int getTotalBytes() {
        return totalBytes;
    }

    public int getBytesPerFrame() {
        return bytesPerFrame;
    }

    public ByteBuffer getData() {
        return data;
    }

    public AudioInputStream getAudioStream() {
        return audioStream;
    }

    public byte[] getDataArray() {
        return dataArray;
    }

    protected void dispose() {
        try {
            audioStream.close();
            data.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
    private ByteBuffer loadData() {
        try {
            int bytesRead = audioStream.read(dataArray, 0, totalBytes);
            data.clear();
            data.put(dataArray, 0, bytesRead);
            data.flip();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Couldn't read bytes from audio stream!");
        }
        return data;
    }

    public static synchronized AudioReader getInstance(String fileName){
        if (audioReader == null) {
            InputStream stream = Class.class.getResourceAsStream("/"+fileName);
            if(stream == null){
                System.err.println("Couldn't find file: " + fileName);
                throw new RuntimeException("Input is not valid!");
            }
            try {
                audioReader = new AudioReader(AudioSystem.getAudioInputStream(new BufferedInputStream(stream)));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UnsupportedAudioFileException e) {
                e.printStackTrace();
            }
        }
        return audioReader;
    }

    private static int getOpenAlFormat(int channels, int bitsPerSample) {
        if (channels == 1) return bitsPerSample == 8 ? AL10.AL_FORMAT_MONO8 : AL10.AL_FORMAT_MONO16;
        else return bitsPerSample == 8 ? AL10.AL_FORMAT_STEREO8 : AL10.AL_FORMAT_STEREO16;
    }
}