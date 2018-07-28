package com.hecticus.eleta.model_new;

public class Media {

    private String name;

    private MultimediaCDN multimediaCDN;

    public Media() {
    }

    public Media(String name, MultimediaCDN multimediaCDN) {
        this.name = name;
        this.multimediaCDN = multimediaCDN;
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
