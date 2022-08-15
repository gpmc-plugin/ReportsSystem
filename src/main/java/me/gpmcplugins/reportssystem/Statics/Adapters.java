package me.gpmcplugins.reportssystem.Statics;

import net.kyori.adventure.audience.Audience;

public class Adapters {
    public static String getNameFromAudience(Audience audience){
        String[] audienceSplited = audience.toString().split("=");
        if(audienceSplited.length<2)
            return null;
        else{
            return audienceSplited[1].split("}")[0];
        }

    }
}
