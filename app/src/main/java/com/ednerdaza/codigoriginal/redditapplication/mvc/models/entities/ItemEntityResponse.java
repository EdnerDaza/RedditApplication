package com.ednerdaza.codigoriginal.redditapplication.mvc.models.entities;

import java.io.Serializable;

/**
 * Created by administrador on 16/02/17.
 */
public class ItemEntityResponse implements Serializable  {
    public String kind;
    public DataRoot data;

    //region GETTERS Y SETTERS

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public DataRoot getDataRoot() {
            return data;
        }

        public void setDataRoot(DataRoot data) {
            this.data = data;
        }

    //endregion
}
