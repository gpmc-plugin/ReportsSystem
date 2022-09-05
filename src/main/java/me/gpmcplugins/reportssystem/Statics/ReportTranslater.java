package me.gpmcplugins.reportssystem.Statics;

import me.gpmcplugins.reportssystem.objects.ReportCreator;

public class ReportTranslater {
    public static String fromReportShortDescription(ReportCreator.ReportShortDescription shortDescription) {
        switch (shortDescription) {
            case Death_Bug:
                return "Błąd powodujący śmierć";
            case User_Scam:
                return "Użytkownik mnie oszukał";
            case Message_Scam:
                return "Wiadomość jest scamem";
            case User_Cheating:
                return "Użytkownik używa cheatów lub exploitów";
            case Message_Bad_Words:
                return "Używanie wulgaryzmów";
            case Message_Offensive:
                return "Wiadomość obraża innego użytkownika";
            case Death_Other_Player:
                return "Inny użytkownik mnie zabił";
            case Message_Hate_Speach:
                return "Wiadomość zawiera mowę nienawiści";
            case Other:
                return "Inne";
        }
        return "Nie wiem";
    }
}
