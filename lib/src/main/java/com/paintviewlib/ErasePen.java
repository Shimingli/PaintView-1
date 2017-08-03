package com.paintviewlib;

import android.graphics.Path;

import java.util.HashSet;

/**
 * Created by huanglongfei on 2017/8/2.
 */

public class ErasePen implements IPen {

  private HashSet<HashSet<PaintPoint>> mLines;
  private HashSet<PaintPoint> mLine;
  private Path mPath;

  public ErasePen(HashSet<HashSet<PaintPoint>> lines, HashSet<PaintPoint> line, Path path) {
    this.mLines = lines;
    this.mLine = line;
    this.mPath = path;
  }

  @Override
  public void paintUp() {

  }

  @Override
  public void paintMove(float previousX, float previousY, float x, float y) {

    PaintPoint paintPoint = new PaintPoint(x, y);
    for (HashSet<PaintPoint> line : mLines) {
      if (line.contains(paintPoint)) {
        line.remove(paintPoint);
      }

    }

  }

  @Override
  public void paintDown(float x, float y) {


  }
}
