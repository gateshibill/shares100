package com.cofc.pojo.video;

public class VodBeanWithBLOBs extends VodBean {
    private String vodContent;

    private String vodPlayUrl;

    private String vodDownUrl;

    public String getVodContent() {
        return vodContent;
    }

    public void setVodContent(String vodContent) {
        this.vodContent = vodContent == null ? null : vodContent.trim();
    }

    public String getVodPlayUrl() {
        return vodPlayUrl;
    }

    public void setVodPlayUrl(String vodPlayUrl) {
        this.vodPlayUrl = vodPlayUrl == null ? null : vodPlayUrl.trim();
    }

    public String getVodDownUrl() {
        return vodDownUrl;
    }

    public void setVodDownUrl(String vodDownUrl) {
        this.vodDownUrl = vodDownUrl == null ? null : vodDownUrl.trim();
    }
}