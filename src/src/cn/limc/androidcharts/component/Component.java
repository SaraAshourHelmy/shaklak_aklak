/*
 * Component.java
 * Android-Charts
 *
 * Created by limc on 2014.
 *
 * Copyright 2011 limc.cn All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.limc.androidcharts.component;

import cn.limc.androidcharts.diagram.GridChart;
import cn.limc.androidcharts.handler.ComponentHandler;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

/**
 * <p>
 * en
 * </p>
 * <p>
 * jp
 * </p>
 * <p>
 * cn
 * </p>
 * 
 * @author limc
 * @version v1.0 2014/06/24 17:19:47
 * 
 */
public interface Component {

    static final float DEFAULT_PADDING_TOP = 5f;
    static final float DEFAULT_PADDING_BOTTOM = 5f;
    static final float DEFAULT_PADDING_LEFT = 5f;
    static final float DEFAULT_PADDING_RIGHT = 5f;
    
    void draw(Canvas canvas);
    
    boolean isValidTouchPoint(PointF pt);
    boolean isValidTouchPoint(float x, float y);
    
    RectF getFrame();
    void  setFrame(RectF frame);
    GridChart getParent();
    void setParent(GridChart parent);
    ComponentHandler getComponentController();
    void setComponentHandler(ComponentHandler componentHandler);
    

    float getPaddingTop();

    float getPaddingLeft();

    float getPaddingBottom();

    float getPaddingRight();

    void setPaddingTop(float value);

    void setPaddingLeft(float value);

    void setPaddingBottom(float value);

    void setPaddingRight(float value);

    float getWidth();

    float getHeight();

    float getStartX();

    float getStartY();

    float getEndX();

    float getEndY();

    float getPaddingStartX();

    float getPaddingEndX();

    float getPaddingStartY();

    float getPaddingEndY();

    float getPaddingWidth();

    float getPaddingHeight();
    
    float getWidthRate(float value);
    
    float getHeightRate(float value);
}
