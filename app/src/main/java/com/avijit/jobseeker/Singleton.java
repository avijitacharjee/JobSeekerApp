package com.avijit.jobseeker;

/**
 * Created by Avijit on 12/24/2019.
 */

public class Singleton {
    private static Singleton instance = new Singleton();

    public String url;
    private Singleton(){}
    public static Singleton getInstance()
    {
        return instance;
    }
}
