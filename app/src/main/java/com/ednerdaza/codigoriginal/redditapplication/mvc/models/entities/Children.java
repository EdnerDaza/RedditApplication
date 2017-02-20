package com.ednerdaza.codigoriginal.redditapplication.mvc.models.entities;

import java.io.Serializable;

/**
 * Created by administrador on 16/02/17.
 */
public class Children implements Serializable {

    public String kind;
    public Data data;

    //region GETTERS Y SETTERS

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

    //endregion
}
