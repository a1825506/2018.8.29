package com.gikee.app.beans;

import java.util.List;

public class RelatedLinksBean {


    private List<String> browser;
    private List<String> website;
    private String whitePaper;

    public List<String> getBrowser() {
        return browser;
    }

    public void setBrowser(List<String> browser) {
        this.browser = browser;
    }

    public List<String> getWebsite() {
        return website;
    }

    public void setWebsite(List<String> website) {
        this.website = website;
    }

    public String getWhitePaper() {
        return whitePaper;
    }

    public void setWhitePaper(String whitePaper) {
        this.whitePaper = whitePaper;
    }
}
