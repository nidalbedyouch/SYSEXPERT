package com.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TextPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea textArea;
	
	public TextPanel(){
		textArea=new JTextArea();
		textArea.setLineWrap(true);
        textArea.setEditable(false);
		this.setLayout(new BorderLayout());
		
		this.add(new JScrollPane(textArea),BorderLayout.CENTER);
	}
	
	public void appendText(String text){
		textArea.append(text);
	}
}
