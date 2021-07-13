package com.example.bmmo;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.DecimalFormat;

@ParseClassName("Profile")
public class Profile extends ParseObject {

    private static DecimalFormat df2 = new DecimalFormat("#.#");

    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_HEIGHT = "height";
    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_BMO = "bmo";
    public static final String KEY_STAMINA = "Stamina";
    public static final String KEY_HEALTH = "health";
    public static final String KEY_STRENGTH = "strength";
    public static final String KEY_EXPERIENCE = "experience";


    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public int getLevel() {return getInt(KEY_LEVEL);}
    public double getHeight() {return getDouble(KEY_HEIGHT);}
    public double getWeight() {return getDouble(KEY_WEIGHT);}
    public double getBMO() {return getDouble(KEY_BMO);}
    public int getStamina() {return getInt(KEY_STAMINA);}
    public int getHealth() {return getInt(KEY_HEALTH);}
    public int getStrength() {return getInt(KEY_STRENGTH);}
    public double getExperience() {return getDouble(KEY_EXPERIENCE);}

    public void setUser(ParseUser user){
        put(KEY_USER,user);
    }
    public void setHeightWeight(double height, double weight) {
        put(KEY_HEIGHT,height);
        put(KEY_WEIGHT,weight);
        double h = height/100;
        double BMI = weight;
        BMI /= (h*h);
        BMI = Double.parseDouble(df2.format(BMI));
        Log.i("hi",String.valueOf(BMI));
        put(KEY_BMO, BMI);

    }
    public void setLevel(int level) { put(KEY_LEVEL,level);}
    public void setStamina(int stamina) { put(KEY_STAMINA,stamina);}
    public void setHealth(int health) { put(KEY_HEALTH,health);}
    public void setStrength(int strength) { put(KEY_STRENGTH,strength); }
    public void setExperience(double exp) { put(KEY_EXPERIENCE,exp+getExperience());}
}