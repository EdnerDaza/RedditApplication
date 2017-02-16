package com.ednerdaza.codigoriginal.redditapplication.mvc.controllers.interfaces;

/**
 * Created by administrador on 16/02/17.
 */
public interface ItemModelInterface<T> {

    public void completeSuccess(T entity);
    public void completeFail(String message);

}
