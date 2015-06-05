package com.stang.tang.rubik.scan;

import java.awt.Toolkit;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.image.BufferedImage;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class PCApplication {
    boolean packFrame = false;
    private static Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    /**
     * Construct and show the application.
     */
    public PCApplication() {
        PCFrame frame = new PCFrame(snapShot());
        // Validate frames that have preset sizes
        // Pack frames that have useful preferred size info, e.g. from their layout
        if (packFrame) {
            frame.pack();
        } else {
            frame.validate();
        }

        // Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2,
                          (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
    }
    //取得全屏图片
    public static BufferedImage snapShot(){
        try {
         return   (new Robot()).createScreenCapture(new
                        Rectangle(0, 0, (int) d.getWidth(), (int) d.getHeight()));
        } catch (AWTException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    /**
     * Application entry point.
     *
     * @param args String[]
     */
    public static void main(String[] args) {
//        new PCApplicati		on();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.
                                             getSystemLookAndFeelClassName());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                new PCApplication();
            }
        });
    }
}
