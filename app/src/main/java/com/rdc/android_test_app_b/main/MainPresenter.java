package com.rdc.android_test_app_b.main;


/**
 * Responsible for handling actions from the View and updating the UI as required
 */
public class MainPresenter implements MainContract.MainPresenter {
    private MainContract.MainView mView;

    MainPresenter(MainContract.MainView view) {
        mView = view;
    }

    @Override
    public void handlePictureScreen(String url, String tabName) {
        mView.showPictureScreen(url, tabName);
    }

    @Override
    public void handleWarningDialog() {
        mView.showWarningDialog();
    }
}
