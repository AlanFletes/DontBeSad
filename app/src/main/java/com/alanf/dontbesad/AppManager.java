package com.alanf.dontbesad;

import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class AppManager {
    private boolean muted, switching, working=false;
    private static AppManager instance;
    private int objs, font,avatar, ha, ma,tr;
    private int[][] relojes;
    private SharedPreferences prefs;
    private Set<String> frases;
    private String[] frasess;

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
    public String[] recoverFrases(){
        frases = prefs.getStringSet("frases", new HashSet<String>());
        return Arrays.copyOf(frases.toArray(), frases.toArray().length, String[].class);
    }
    public int getFont(){
        return this.font;
    }
    public void setFont(int font){
        this.font=font;
    }
    public int getAvatar(){
        return this.avatar;
    }
    public void setAvatar(int avatar){
        this.avatar=avatar;
    }
    public void addFrase(String frase){
        Set<String> set = new HashSet<String>();
        for(String s : frases){
            set.add(s);
        }
        set.add(frase);
        prefs.edit().putStringSet("frases",set).apply();
    }
    public void deleteFrase(String frase){
        Set<String> set = new HashSet<String>();
        for(String f : frases){
            if(!f.equals(frase)){
                set.add(f);
            }
        }
        prefs.edit().putStringSet("frases",set).apply();
    }
    public void updSFrases(){
        frasess = recoverFrases();
    }
    public String getRandomFrase(){
        updSFrases();
        Random random = new Random();
        if(frasess.length==0)return "Puedes agregar frases desde el menú de inicio!";
        int indx = random.nextInt(frasess.length);
        return frasess[indx];
    }
    public boolean getWorking(){
        this.working=prefs.getBoolean("working",false);
        return this.working;
    }
    public void setWorking(boolean working){
        prefs.edit().putBoolean("working",working).apply();
        this.working=working;
    }
    public void setHa(int ha){
        prefs.edit().putInt("ha",ha).apply();
        this.ha=ha;
    }
    public int getHa(){
        this.ha=prefs.getInt("ha",0);
        return this.ha;
    }
    public void setMa(int ma){
        prefs.edit().putInt("ma",ma).apply();
        this.ma = ma;
    }
    public int getMa(){
        this.ma=prefs.getInt("ma",0);
        return this.ma;
    }
    public void setTr(int tr){
        prefs.edit().putInt("tr",tr);
        this.tr=tr;
    }
    public int getTr(){
        this.tr=prefs.getInt("tr",ha);
        return this.tr;
    }
}
