package com.rdc.android_test_app_b.domain.main;

public interface MainContract {
    interface View {

        void transitionToAnotherActivity(String type, String idLink);

        void closeActivity();
    }

    interface Presenter {
        void checking(boolean check, String type, String idLink);

        void setView(MainContract.View view);
    }
}
