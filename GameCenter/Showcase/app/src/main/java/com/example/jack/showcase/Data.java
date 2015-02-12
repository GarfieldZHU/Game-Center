package com.example.jack.showcase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jack on 2014/10/7.
 */
public class Data {

    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String ICON_URL = "icon_url";
    public static final String DOWNLOAD_URL = "download_url";
    public static final String TUTORIAL_URL = "tutorial_url";

    private String title;
    private String description;
    private String icon;
    private String downloadUrl;
    private String tutorialUrl;

    public Data() {
        super();
    }

    public Data(String title, String description, String icon, String downloadUrl, String tutorialUrl) {
        super();
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.downloadUrl = downloadUrl;
        this.tutorialUrl = tutorialUrl;
    }

    public Data(String jsonStr) {
        super();
        try {
            JSONObject json = new JSONObject(jsonStr);
            title = json.optString(TITLE);
            description = json.optString(DESCRIPTION);
            icon = json.optString(ICON_URL);
            downloadUrl = json.optString(DOWNLOAD_URL);
            tutorialUrl = json.optString(TUTORIAL_URL);
        } catch (JSONException e) {}
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getTutorialUrl() {
        return tutorialUrl;
    }
    public void setTutorialUrl(String tutorialUrl) {
        this.tutorialUrl = tutorialUrl;
    }

    @Override
    public String toString() {
        try {
            JSONObject json = new JSONObject();
            json.put(TITLE, title);
            json.put(DESCRIPTION, description);
            json.put(ICON_URL, icon);
            json.put(DOWNLOAD_URL, downloadUrl);
            json.put(TUTORIAL_URL, tutorialUrl);
            return json.toString();
        } catch (JSONException e) {}
        return "{}";
    }

}
