package com.ednerdaza.codigoriginal.redditapplication.mvc.models.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by administrador on 16/02/17.
 */
public class DataRoot implements Serializable {

    public String modhash;
    public List<Children> children;
    public String after;
    public String before;

    public String getModhash() {
        return modhash;
    }

    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    public List<Children> getChildren() {
        return children;
    }

    public void setChildren(List<Children> children) {
        this.children = children;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

}
