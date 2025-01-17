/*
 * Copyright (c) 2015-present, Horcrux.
 * All rights reserved.
 *
 * This source code is licensed under the MIT-style license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.horcrux.svg;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import com.facebook.react.bridge.ReactContext;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

class ParsedPath {
  final Path path;
  final List<PathElement> elements;

  ParsedPath(Path path, List<PathElement> elements) {
    this.path = path;
    this.elements = elements;
  }
}

@SuppressLint("ViewConstructor")
class PathView extends RenderableView {
  private Path mPath;
  private static final Map<String, ParsedPath> sPathCache = new HashMap<>();

  public PathView(ReactContext reactContext) {
    super(reactContext);
    PathParser.mScale = mScale;
    mPath = new Path();
  }

  public void setD(String d) {
    mPath = PathParser.parse(d);
    elements = PathParser.elements;
    for (PathElement elem : elements) {
      for (Point point : elem.points) {
        point.x *= mScale;
        point.y *= mScale;
      }
    }
    invalidate();
  }

  void setDByParsing(String d) {
     mPath = PathParser.parse(d);
     elements = PathParser.elements;
     for (PathElement elem : elements) {
         point.y *= mScale;
       }
     }
  }

  public void setD(String d) {
    ParsedPath cached = sPathCache.get(d);
    if (cached != null) {
      mPath = cached.path;
      elements = cached.elements;
    } else {
      setDByParsing(d);
      sPathCache.put(d, new ParsedPath(mPath, elements));
    }
     invalidate();
  }

  @Override
  Path getPath(Canvas canvas, Paint paint) {
    return mPath;
  }
}
