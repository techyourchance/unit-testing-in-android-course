package com.techyourchance.unittesting.screens.common.controllers;

import androidx.appcompat.app.AppCompatActivity;

import com.techyourchance.unittesting.common.CustomApplication;
import com.techyourchance.unittesting.common.dependencyinjection.ControllerCompositionRoot;

public class BaseActivity extends AppCompatActivity {

    private ControllerCompositionRoot mControllerCompositionRoot;

    protected ControllerCompositionRoot getCompositionRoot() {
        if (mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((CustomApplication) getApplication()).getCompositionRoot(),
                    this
            );
        }
        return mControllerCompositionRoot;
    }

}
