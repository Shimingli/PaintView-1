package com.paintviewlib;

/**
 * Created by huanglongfei on 2017/8/2.
 */

public interface IPen {

  void paintUp();

  void paintMove(float previousX, float previousY, float x, float y);

  void paintDown(float x, float y);

}
