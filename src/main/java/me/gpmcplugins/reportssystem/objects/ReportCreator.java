package me.gpmcplugins.reportssystem.objects;

public class ReportCreator {
    private ReportType type;
    private String ReportingPlayer;
    private String ReportedElementID;
    private String Description;
    private ReportShortDescription reportShortDescription;
    public ReportCreator(ReportType type,String reportingPlayer,String reportedElementID){
        this.type=type;
        this.ReportingPlayer=reportingPlayer;
        this.ReportedElementID=reportedElementID;

    }
    public void setShortDescription(ReportShortDescription rsd){
        this.reportShortDescription = rsd;
    }
    public void setDescription(String description){
        this.Description = description;
    }

    public ReportType getType() {
        return type;
    }

    public ReportShortDescription getReportShortDescription() {
        return reportShortDescription;
    }

    public String getDescription() {
        return Description;
    }

    public String getReportingPlayer() {
        return ReportingPlayer;
    }

    public String getReportedElementID() {
        return ReportedElementID;
    }

    public enum ReportType{
        User,
        Message,
        Death
    }
    public enum ReportShortDescription{
        Death_Bug,
        Death_Other_Player,
        User_Cheating,
        User_Scam,
        Message_Bad_Words,
        Message_Scam,
        Message_Racist,
        Message_offensive,
        Message_offensive_to_ptg
    }
}
