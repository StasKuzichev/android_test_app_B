package com.rdc.android_test_app_b.main;

/**
 * This defines the contract between the View {@link MainActivity} and Presenter
 * {@link MainPresenter}.
 */
public interface MainContract {
    interface MainView {
        void showPictureScreen(String url, String tabName);
        void showWarningDialog();
    }
    interface MainPresenter {
        void handlePictureScreen(String url, String tabName);
        void handleWarningDialog();
    }
}