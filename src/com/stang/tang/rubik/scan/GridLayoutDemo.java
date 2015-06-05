package com.stang.tang.rubik.scan;

import java.awt.Button;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.JPanel;


public class GridLayoutDemo implements ActionListener {
	JPanel p1, p2, p3, p4;
	int i = 1;
	JFrame f;
	public GridLayoutDemo() {
		f = new JFrame();		// 创建一个JFrame的对象
		Container contentPane = f.getContentPane();
		contentPane.setLayout(new GridLayout(2, 1));
		p1 = new JPanel();
		Button b = new Button("换一个");
		b.addActionListener(this);	// 当按下"换一个"按钮时，进行事件监听，将会有系统操作产生。
		p1.add(b); 			// 处理操作在52-64行.
		contentPane.add(p1);
		p2 = new JPanel();
		p2.setLayout(new FlowLayout());
		p2.add(new JButton("first"));	// 将第一个按钮添加到对象p2中
		p2.add(new JButton("second"));// 将第二个按钮添加到对象p2中
		p2.add(new JButton("third"));	// 将第三个按钮添加到对象p2中
		p3 = new JPanel();
		p3.setLayout(new GridLayout(3, 1));
		p3.add(new JButton("fourth"));
		p3.add(new JButton("fifth"));
		p3.add(new JButton("This is the last button"));
		p4 = new JPanel();
		p4.setLayout(new GridLayout());
		p4.add("one", p2);
		p4.add("two", p3);
		
		contentPane.add(p4);
		f.setTitle("CardLayout");
		f.pack();
		f.setVisible(true);			// 将框架设置为可见状态
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	public void actionPerformed(ActionEvent event) {
		switch (i) {
		case 1:
			break;
		case 2:
			break;
		}
		i++;
		if (i == 3)
			i = 1;
		f.validate();
	}
	public static void main(String[] args) {
		new GridLayoutDemo();
	}
}
