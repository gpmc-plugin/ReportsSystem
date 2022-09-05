package me.gpmcplugins.reportssystem.objects;

import me.gpmcplugins.reportssystem.Managers.NotificationManager.NotificationType;

public class NotificationConfigObject {
    private final NotificationType translationKey;
    private final String translatableString;
    private final boolean canBeDelayed;
    public NotificationConfigObject(NotificationType type,String translatableString,Boolean canBeDelayed){
        this.translatableString = translatableString;
        this.translationKey = type;
        this.canBeDelayed = canBeDelayed;
    }
    @SuppressWarnings("unused")
    public NotificationType getTranslationKey() {
        return translationKey;
    }

    public String getTranslatableString() {
        return translatableString;
    }

    public boolean getCanBeDelayed() {
        return canBeDelayed;
    }
}
