/*
 * Copyright (C) 2026 JHotDraw.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 */
package org.jhotdraw.samples.svg.figures;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.geom.Point2D;
import org.junit.jupiter.api.Test;

class SVGRectFigureTest {

  @Test
  void setArcUpdatesBothCornerRadii() {
    SVGRectFigure figure = new SVGRectFigure(10, 20, 100, 50);

    figure.setArc(12, 18);

    assertThat(figure.getArcWidth()).isEqualTo(12);
    assertThat(figure.getArcHeight()).isEqualTo(18);
  }

  @Test
  void setArcWidthAndHeightCanBeChangedIndependently() {
    SVGRectFigure figure = new SVGRectFigure(0, 0, 100, 50, 10, 10);

    figure.setArcWidth(20);
    figure.setArcHeight(30);

    assertThat(figure.getArcWidth()).isEqualTo(20);
    assertThat(figure.getArcHeight()).isEqualTo(30);
  }

  @Test
  void cloneKeepsIndependentRadiusValues() {
    SVGRectFigure original = new SVGRectFigure(0, 0, 100, 50, 10, 15);
    SVGRectFigure clone = original.clone();

    clone.setArc(25, 35);

    assertThat(original.getArcWidth()).isEqualTo(10);
    assertThat(original.getArcHeight()).isEqualTo(15);
    assertThat(clone.getArcWidth()).isEqualTo(25);
    assertThat(clone.getArcHeight()).isEqualTo(35);
  }

  @Test
  void translatedBoundsPreserveTheConfiguredRadius() {
    SVGRectFigure figure = new SVGRectFigure(0, 0, 100, 50, 8, 12);

    figure.setBounds(new Point2D.Double(5, 10), new Point2D.Double(35, 40));

    assertThat(figure.getX()).isEqualTo(5);
    assertThat(figure.getY()).isEqualTo(10);
    assertThat(figure.getWidth()).isEqualTo(30);
    assertThat(figure.getHeight()).isEqualTo(30);
    assertThat(figure.getArcWidth()).isEqualTo(8);
    assertThat(figure.getArcHeight()).isEqualTo(12);
  }
}
