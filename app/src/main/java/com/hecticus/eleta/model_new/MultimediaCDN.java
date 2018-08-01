package com.hecticus.eleta.model_new;

import io.realm.RealmObject;

public class MultimediaCDN extends RealmObject {

    //private int id;
    private String mediaBase64;
    private String url;

    public MultimediaCDN() {
    }

    public MultimediaCDN(String mediaBAse64) {
        this.mediaBase64 = mediaBAse64;
    }

    public MultimediaCDN(String mediaBase64, String url) {
        this.mediaBase64 = mediaBase64;
        this.url = url;
    }

    public String getMediaBAse64() {
        return mediaBase64;
    }

    public void setMediaBAse64(String mediaBAse64) {
        this.mediaBase64 = mediaBAse64;
    }

    /*public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
