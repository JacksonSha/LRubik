package com.stang.tang.rubik.scan;

import java.awt.*;

import javax.swing.*;
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
public class JCanvas extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage image = null;
    public JCanvas() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    private void jbInit() throws Exception {
        this.setLayout(null);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
    }
    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
