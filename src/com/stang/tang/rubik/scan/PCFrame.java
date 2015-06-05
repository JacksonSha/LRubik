package com.stang.tang.rubik.scan;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.*;
import javax.swing.DebugGraphics;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.MouseMotionAdapter;

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
public class PCFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel contentPane;
    JPanel PaneShowColor = new JPanel();
    //private JCanvas canvas = new JCanvas();

    public PCFrame(BufferedImage image) {
        try {
            //EXIT_ON_CLOSE（在 JFrame 中定义）：使用 System exit 方法退出应用程序。仅在应用程序中使用。
            setDefaultCloseOperation(EXIT_ON_CLOSE);//设置用户在此窗体上发起 "close" 时默认执行的操作。必须指定以下选项之一
            jbInit();
            if(image == null){
                System.exit(0);
            }
            /*
            canvas.setImage(image);
            this.getContentPane().add(canvas, BorderLayout.CENTER);
            */
            ImageIcon img=new ImageIcon(image);
            JLabel hy = new JLabel(img);
            this.getLayeredPane().add(hy, new Integer(Integer.MIN_VALUE));
            hy.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
            ((JPanel) getContentPane()).setOpaque(false);


            this.setExtendedState(JFrame.MAXIMIZED_BOTH);//全屏 --最大化
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Component initialization.
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {

        this.addMouseMotionListener(new PCFrame_this_mouseMotionAdapter(this));
        setAlwaysOnTop(true);//更改始终位于顶层的窗口状态
        setResizable(false);//是否可由用户调整大小
        setUndecorated(true);//禁用或启用此 frame 的装饰   --只有在 frame 不可显示时才调用此方法
        pack();//调整此窗口的大小，以适合其子组件的首选大小和布局。如果该窗口和/或其所有者仍不可显示，则两者在计算首选大小之前变得可显示。在计算首选大小之后，将会验证该 Window。

        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(null);
        this.getContentPane().setBackground(SystemColor.control);
        setSize(new Dimension(336, 257));
        setTitle("获取屏幕颜色");
        this.addMouseListener(new PCFrame_this_mouseAdapter(this));
        contentPane.setDebugGraphicsOptions(DebugGraphics.NONE_OPTION);
        PaneShowColor.setBackground(Color.red);
        PaneShowColor.setBounds(new Rectangle(1, 1, 39, 28));
        contentPane.add(PaneShowColor);
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }




    public void this_mouseClicked(MouseEvent e) {
        //JPShowColor.setBackground(pickColor());
        GuiCamera gc = new GuiCamera();
        int rgb[] = gc.CaptureFullScreen(e.getPoint());
        Color c = new Color(rgb[0],rgb[1],rgb[2]);
        PaneShowColor.setBackground(c);
        System.exit(0);
    }
    public Color pickColor() {
        Color pixel = new Color(0, 0, 0);
        Robot robot = null;
        Point mousepoint;
        
        @SuppressWarnings("unused")
		int R, G, B;

        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
            System.exit(1);
        }
        mousepoint = MouseInfo.getPointerInfo().getLocation();
        pixel = robot.getPixelColor(mousepoint.x, mousepoint.y);
        R = pixel.getRed();
        G = pixel.getGreen();
        return pixel;
    }

    public void this_mouseMoved(MouseEvent e) {
        GuiCamera gc = new GuiCamera();
        int rgb[] = gc.CaptureFullScreen(e.getPoint());
        Color c = new Color(rgb[0],rgb[1],rgb[2]);
        PaneShowColor.setBackground(c);
    }
}


class PCFrame_this_mouseAdapter extends MouseAdapter {
    private PCFrame adaptee;
    PCFrame_this_mouseAdapter(PCFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseClicked(MouseEvent e) {
        adaptee.this_mouseClicked(e);
    }
}


class PCFrame_this_mouseMotionAdapter extends MouseMotionAdapter {
    private PCFrame adaptee;
    PCFrame_this_mouseMotionAdapter(PCFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void mouseMoved(MouseEvent e) {
        adaptee.this_mouseMoved(e);
    }
}
