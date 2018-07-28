package com.rdc.android_test_app_b.domain.main;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;

    @Override
    public void setView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void checking(boolean check, String type) {
        if(check){
            view.transitionToAnotherActivity(type);
        } else {
            view.closeActivity();
        }
    }

}
