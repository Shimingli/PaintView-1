package com.paintviewlib;

import android.graphics.Path;

import java.util.HashSet;

/**
 * Created by huanglongfei on 2017/8/2.
 */

public class Pencil implements IPen {

  private HashSet<HashSet<PaintPoint>> mLines;
  private HashSet<PaintPoint> mLine;
  private Path mPath;

  public Pencil(HashSet<HashSet<PaintPoint>> lines, HashSet<PaintPoint> line, Path path) {
    this.mLines = lines;
    this.mLine = line;
    this.mPath = path;
  }

  @Override
  public void paintUp() {
    mLines.add(mLine);

  }

  @Override
  public void paintMove(float previousX, float previousY, float x, float y) {
    float cX = (x + previousX) / 2;
    float cY = (y + previousY) / 2;

    //这里终点设为两点的中心点的目的在于使绘制的曲线更平滑，如果终点直接设置为x,y，效果和lineto是一样的,实际是折线效果
    mPath.quadTo(previousX, previousY, cX, cY);

    mLine.add(new PaintPoint(x, y));
  }

  @Override
  public void paintDown(float x, float y) {
    mLine = new HashSet<>();
    mPath.moveTo(x, y);
    mLine.add(new PaintPoint(x, y));
  }
}
