package com.alanf.dontbesad;

import android.content.SharedPreferences;

public class AppManager {
    private boolean muted, switching;
    private static AppManager instance;
    private int objs;
    private int relojes[][];
    private SharedPreferences prefs;

    private AppManager(){
        relojes = new int[2][3];
        for(int i=0;i<2;i++){
            for(int j=0;j<3;j++){
                relojes[i][j]=0;
            }
        }
    }
    public static synchronized AppManager getAppManagerInstance(){
        if(instance==null){
            synchronized (AppManager.class){
                instance = new AppManager();
            }
        }
        return instance;
    }
    public void setMuted(boolean muted){
        this.muted = muted;
    }
    public boolean getMuted(){
        return this.muted;
    }
    public void setSwitching(boolean switching){
        this.switching=switching;
    }
    public boolean getSwitching(){
        return this.switching;
    }
    public void setObjs(int objs){
        this.objs = objs;
    }
    public int getObjs(){
        return this.objs;
    }
    public int[][] getRelojes(){
        return relojes;
    }
    public void setRelojes(int[][] relojes){
        this.relojes=relojes;
    }
    public boolean setReloj1Hora(int hora){
        if(hora>=0 && hora<=24){
            relojes[0][0] = hora;
            return true;
        }
        return false;
    }
    public boolean setReloj1Minutos(int minuto){
        if(minuto>=0 && minuto<=59){
            relojes[0][1] = minuto;
            return true;
        }
        return false;
    }
    public boolean setReloj1AMPM(int ampm){
        if(ampm>=0 && ampm<=1){
            relojes[0][2] = ampm;
            return true;
        }
        return false;
    }
    public boolean setReloj2Hora(int hora){
        if(hora>=0 && hora<=24){
            relojes[1][0] = hora;
            return true;
        }
        return false;
    }
    public boolean setReloj2Minutos(int minuto){
        if(minuto>=0 && minuto<=59){
            relojes[1][1] = minuto;
            return true;
        }
        return false;
    }
    public boolean setReloj2AMPM(int ampm){
        if(ampm>=0 && ampm<=1){
            relojes[1][2] = ampm;
            return true;
        }
        return false;
    }
    public int getReloj1Hora(){
        return relojes[0][0];
    }
    public int getReloj1Minutos(){
        return relojes[0][1];
    }
    public int getReloj1AMPM(){
        return relojes[0][2];
    }
    public int getReloj2Hora(){
        return relojes[1][0];
    }
    public int getReloj2Minutos(){
        return relojes[1][1];
    }
    public int getReloj2AMPM(){
        return relojes[1][2];
    }
    public void setPrefs(SharedPreferences prefs){
        this.prefs=prefs;
    }
    public SharedPreferences getPrefs(){
        return this.prefs;
    }
}
