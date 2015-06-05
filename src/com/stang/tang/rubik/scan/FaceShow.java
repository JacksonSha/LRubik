package com.stang.tang.rubik.scan;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class FaceShow extends JFrame implements MouseMotionListener{
	private static final long serialVersionUID = 1L;
	private int N=3;
	private int length=300;
	private JPanel jPanel;
	private JLabel[][] jLabels;

	public FaceShow() {
		this.setTitle("魔方颜色扫描");
		this.setLocation(0, 0);
		this.setSize(length, length);
		this.setLayout(null);
		jPanel=new JPanel();
		jPanel.setBounds(0, 0, length, length);
		jPanel.setLayout(new GridLayout(N,N));
		jPanel.setBackground(Color.orange);
		jLabels=new JLabel[N][N];
		this.addMouseMotionListener(this);
		RelativePoint oPoint=new RelativePoint(300,300);
		this.init(oPoint);
		this.setContentPane(jPanel);
		this.setVisible(true);
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void init(RelativePoint oPoint){
		ScanFace sf=new ScanFace(N,length,oPoint);
		int[][] rgb=sf.scanning();
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				jLabels[i][j]=new JLabel(i+";"+j+"="+rgb[i][j]);
				int r=(rgb[i][j] & 0xff0000) >> 16;
				int g=(rgb[i][j] & 0xff00) >> 8;
				int b=(rgb[i][j] & 0xff);
				jLabels[i][j].setBackground(new Color(r,g,b));
				jLabels[i][j].setOpaque(true);
				jLabels[i][j].setVisible(true);
				jLabels[i][j].setSize(100, 100);
//				jLabels[i][j].setBounds(0, jLabels[i][j-1].getY(), jFrame.getWidth()/N, jFrame.getHeight()/N);
				jPanel.add(jLabels[i][j]);
			}
		}
	}
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.
                                             getSystemLookAndFeelClassName());
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                new FaceShow();
            }
        });
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		System.out.println(e.getX()+":"+e.getY()+";"+e.getXOnScreen()+":"+e.getYOnScreen());
		RelativePoint oPoint=new RelativePoint(e.getXOnScreen(),e.getYOnScreen());
		init(oPoint);
	}

}
