package com.stang.tang.rubik.scan;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.awt.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class GuiCamera {
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    public GuiCamera(){
    }
    public  int[]  CaptureFullScreen(Point p){
            int[] rgb=new int[3];
            //拷贝屏幕到一个BufferedImage对象screenshot
            try {
                BufferedImage screenshot = (new Robot()).createScreenCapture(new
                        Rectangle(0, 0, (int) d.getWidth(), (int) d.getHeight()));
//                (new Robot()).createScreenCapture(new
//                        Rectangle(0, 0, (int) d.getWidth(), (int) d.getHeight()));
                int pixel = screenshot.getRGB(p.x, p.y);
                rgb[0] = (pixel & 0xff0000) >> 16;
                rgb[1] = (pixel & 0xff00) >> 8;
                rgb[2] = (pixel & 0xff);
                return rgb;
            } catch (AWTException e) {
                e.printStackTrace();
                return null;
            }
    }
}
