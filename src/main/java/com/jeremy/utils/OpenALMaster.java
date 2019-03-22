//package com.jeremy.utils;
//
//import com.jeremy.models.AudioSource;
//import org.lwjgl.openal.AL;
//import org.lwjgl.openal.AL10;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public final class OpenALMaster {
//
//    private static List<Integer> bufferIdList = new ArrayList<>();
//    private static AudioSource audioSource;
//
//    public static synchronized void init() {
//        AL.createCapabilities();
//    }
//
//    public static void setListener() {
//        AL10.alListener3f(AL10.AL_POSITION , 0 , 0 , 0);
//        AL10.alListener3f(AL10.AL_VELOCITY , 0 , 0 , 0);
//    }
//
//    public static synchronized void loadSound(String fileName) {
//        final WaveData waveData = WaveData.getInstance(fileName);
//        final AudioSource audioSource = new AudioSource();
//        final int buffered_id = audioSource.getBuffer_id();
//
//        AL10.alBufferData(buffered_id , waveData.getFormat() , waveData.getData() , waveData.getSamplerate());
//        waveData.dispose();
//        bufferIdList.add(buffered_id);
//    }
//
//    public static void clean() {
//        AL.init();
//    }
//
//    public static AudioManager getAudioManager() {
//        return new AudioManager();
//    }
//
//    private static class AudioManager {
//
//        public void playSource() {
//            AL10.alSourcei(audioSource.getSource_id() , AL10.AL_BUFFER , audioSource.getBuffer_id());
//            AL10.alSourcePlay(audioSource.getSource_id());
//        }
//
//        public void pauseSource() {
//            AL10.alSourcePause(audioSource.getSource_id());
//        }
//
//        public void resumeSource() {
//            AL10.alSourcePlay(audioSource.getSource_id());
//        }
//
//        public void stopSource() {
//            AL10.alSourceStop(audioSource.getSource_id());
//        }
//
//        public void removeSource() {
//            AL10.alDeleteSources(audioSource.getSource_id());
//        }
//
//        public void clean() {
//            AL10.alDeleteBuffers(audioSource.getBuffer_id());
//            AL10.alDeleteSources(audioSource.getSource_id());
//        }
//    }
//
//}
