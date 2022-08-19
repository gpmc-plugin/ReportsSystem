package me.gpmcplugins.reportssystem.objects;

import me.gpmcplugins.reportssystem.objects.ReportObject;

import java.util.List;

public class ReportsWithIsLastPage {
    public List<ReportObject> content;
    public boolean isLastPage;
    public ReportsWithIsLastPage(List<ReportObject> content, boolean isLastPage)
    {
        this.content = content;
        this.isLastPage = isLastPage;
    }
}
