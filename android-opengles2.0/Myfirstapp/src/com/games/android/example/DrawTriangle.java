package com.games.android.example;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class DrawTriangle extends Activity {
	GLSurfaceView view;
 
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(this.getClass().getName(), "Into onCreate Draw triangle");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        if (detectOpenGLES20()){

            Log.d("GLES20", "GL ES 2.0 Supported..............!");

          } else {

            Log.d("GLES20", "GL ES 2.0 Not Supported...............!");

          }
        
        view = new GLSurfaceView(this);
        view.setEGLContextClientVersion(2);
        view.setEGLConfigChooser(8 , 8, 8, 8, 16, 0);
        view.setRenderer(new TriangleRenderer(this));
       // view.setRenderer(new Square());
        setContentView(view);
    }
    
    private boolean detectOpenGLES20() {

        ActivityManager am =      (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

        ConfigurationInfo info = am.getDeviceConfigurationInfo();

        return (info.reqGlEsVersion >= 0x20000);

    }
}
