package me.gpmcplugins.reportssystem.Statics;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TranslatableComponent;

public class Adapters {
    public static String getNameFromAudience(Audience audience){
        String[] audienceSplited = audience.toString().split("=");
        if(audienceSplited.length<2)
            return null;
        else{
            return audienceSplited[1].split("}")[0];
        }

    }
    public static String getArgumentsString(TranslatableComponent translatableComponent){
        String args = "";
        for (Component arg : translatableComponent.args()) {
            args+=((TextComponent) arg).content()+";";
        }
        return args.substring(0,args.length()-1);
    }
}
