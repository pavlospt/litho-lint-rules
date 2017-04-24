package com.github.pavlospt.litholintrulessample;

import android.app.Application;
import com.facebook.soloader.SoLoader;

public class LithoLintRulesApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    SoLoader.init(this, false);
  }
}
