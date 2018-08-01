package com.hecticus.eleta.model_new;

import io.realm.RealmObject;

public class MultimediaProfile extends RealmObject {
    private Integer  id;
    private String name;
    private MultimediaCDN multimediaCDN;

    public MultimediaProfile() {
    }

    public MultimediaProfile(Integer id, String name, MultimediaCDN multimediaCDN) {
        this.id = id;
        this.name = name;
        this.multimediaCDN = multimediaCDN;
    }

    public MultimediaProfile(String name, MultimediaCDN multimediaCDN) {

        this.name = name;
        this.multimediaCDN = multimediaCDN;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultimediaCDN getMultimediaCDN() {
        return multimediaCDN;
    }

    public void setMultimediaCDN(MultimediaCDN multimediaCDN) {
        this.multimediaCDN = multimediaCDN;
    }
}
