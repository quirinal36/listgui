package kr.bacoder.dev.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Swing implements ActionListener{

	public JFrame frame;
	JButton button1;
	JButton button2;
	JTextField text2;
	JTextField text3;
	JTextField text4;
	
	public Swing(){
		frame = new JFrame("2018-05-18");
		frame.setBounds(100, 100, 1000, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTextField text1 = new JTextField(10);
//		JButton button1 = new JButton("추가"); // ********** 삭제
		button1 = new JButton("추가");
		button1.addActionListener(this);
		
		JPanel panel1 = new JPanel();
		panel1.add(text1);
		panel1.add(button1);
		
		frame.getContentPane().add(panel1, BorderLayout.NORTH);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == button1){
			System.out.println("버튼1이 클릭 되었습니다.");
			JPanel panel2 = new JPanel();
			
			JLabel label2 = new JLabel("알파벳:");
//			JTextField text2 = new JTextField(10);
			text2 = new JTextField(10);
			panel2.add(label2);
			panel2.add(text2);
			
			JLabel label3 = new JLabel("영어이름:");
			text3 = new JTextField(10);
			panel2.add(label3);
			panel2.add(text3);
			
			JLabel label4 = new JLabel("Kor이름:");
			text4 = new JTextField(10);
			panel2.add(label4);
			panel2.add(text4);			
			
			button2 = new JButton("저장");
			button2.addActionListener(this);
			panel2.add(button2);
			
			frame.getContentPane().add(panel2, BorderLayout.SOUTH);
			
			frame.revalidate();
			frame.repaint();
		}else if(arg0.getSource() == button2){
			System.out.println("버튼2이(가) 클릭 되었습니다.");
			
			System.out.println("text2.getText()::::::"  +  text2.getText());
			System.out.println("text3.getText()::::::"  +  text3.getText());
			System.out.println("text4.getText()::::::"  +  text4.getText());
		}
	}
}
