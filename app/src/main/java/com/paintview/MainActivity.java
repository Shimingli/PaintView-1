package com.paintview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.paintviewlib.PaintSurfaceView;
import com.paintviewlib.PaintView;

public class MainActivity extends AppCompatActivity {

  private PaintSurfaceView surfaceView;
  private PaintView paintView;
  private Button erase;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    paintView = (PaintView) findViewById(R.id.paintView);
    surfaceView = (PaintSurfaceView) findViewById(R.id.surfaceView);
    erase = (Button) findViewById(R.id.erase);


    erase.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        paintView.setMode(PaintView.PaintMode.ERASE_MODE);
      }
    });


  }
}
