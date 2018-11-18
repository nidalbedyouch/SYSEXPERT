package com.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ccontroller.Controller;

public class FormPanel extends JPanel{
	String[] critere={"premiere règle","complexe","plus de premisses à satisfaire","récence règle"};
	
	private JPanel fileChooser=new JPanel();
	private JPanel fileChooser2=new JPanel();
	private JPanel panelAjoutFR=new JPanel();
	private JPanel panelFaitAjoute=new JPanel();
	private JPanel panelFaitBut=new JPanel();
	private JPanel panelAlgo=new JPanel();
	private JPanel panelCritere=new JPanel();
	private JPanel panelLancer=new JPanel();
	private JButton parcourir1 = new JButton("parcourir"); 
	private JButton parcourir2 = new JButton("parcourir"); 
	private JButton addFB= new JButton("ajout fait "); 
	private JButton addRegle= new JButton("ajout règle");
	private JButton prouverBut= new JButton("Prouver But");
	private JButton saturer= new JButton("Saturer la base");
	private JTextField nomfB=new JTextField();
	private JTextField nomRegle=new JTextField();
	private JTextField faitBut=new JTextField();
	private JRadioButton rbAvant=new JRadioButton("chainage avant");    
	private JRadioButton rbArriere=new JRadioButton("chainage arriere"); 
	private ButtonGroup typeAlgo=new ButtonGroup();   
	private JComboBox<String> criteres=new JComboBox<String>(critere);
	private JTextArea labelPATH1=new JTextArea(" ");
	private JLabel labelINFO1=new JLabel(" ");
	private JTextArea labelPATH2=new JTextArea(" ");
	private JLabel labelINFO2=new JLabel(" ");
	private JLabel labeladdFact=new JLabel(" ");
	private JLabel labeladdRule=new JLabel(" ");
	private JLabel labeladdBut=new JLabel(" ");
	private JLabel labelMessage=new JLabel(" ");
	private JLabel labelFact=new JLabel("<html>choisissez un fichier fait : </html>");
	private JLabel labelRegle=new JLabel("<html>choisissez un fichier règle:</html>");
	
	Controller controller;
	SEGUI parent;
	
	
	public FormPanel(SEGUI p,Controller c){
		
		this.controller=c;
		this.parent=p;
		Dimension dim=this.getPreferredSize();
		dim.width=this.parent.getWidth()*2/5;
		this.setPreferredSize(dim);
		
		labelPATH1.setLineWrap(true);
        labelPATH1.setWrapStyleWord(true);
        labelPATH1.setOpaque(false);
        labelPATH1.setEditable(false);
		
        labelPATH2.setLineWrap(true);
        labelPATH2.setWrapStyleWord(true);
        labelPATH2.setOpaque(false);
        labelPATH2.setEditable(false);
        
		//choose fact file
		fileChooser.setLayout(new GridLayout(1,4));
		fileChooser.add(labelFact);
		fileChooser.add(parcourir1);
		fileChooser.add(labelPATH1);
		fileChooser.add(labelINFO1);
				
		//choose rule file
		fileChooser2.setLayout(new GridLayout(1,4));
		fileChooser2.add(labelRegle);
		fileChooser2.add(parcourir2);
		fileChooser2.add(labelPATH2);
		fileChooser2.add(labelINFO2);
				
				
		//add fact 
				
				
		panelFaitAjoute.setLayout(new GridLayout(1,5));
		panelFaitAjoute.add(new JLabel("Fait : "));
		panelFaitAjoute.add(nomfB);
		panelFaitAjoute.add(new JLabel("<html>Exemple:<br/> nom = (true||false) <br/>ou nom = valeur</html> "));
		panelFaitAjoute.add(addFB);
		panelFaitAjoute.add(labeladdFact);
				
				
		//add rule
		panelAjoutFR.setLayout(new GridLayout(1,5));
		panelAjoutFR.add(new JLabel("Regle : "));
		panelAjoutFR.add(nomRegle);
		panelAjoutFR.add(new JLabel("<html>Exemple:<br/> A & B & C := D</html>"));
		panelAjoutFR.add(addRegle);
		panelAjoutFR.add(labeladdRule);
				
				
		//ajouter fait à prouver
				
		panelFaitBut.setLayout(new GridLayout(1,4));
		panelFaitBut.add(new JLabel("Fait à prouver : "));
		panelFaitBut.add(faitBut);
		panelFaitBut.add(new JLabel("<html>Exemple:<br/> nom = (true||false) <br/> ou nom = valeur</html> "));
		panelFaitBut.add(labeladdBut);
				
		//add choix algo
		panelAlgo.setLayout(new GridLayout(1,3));
		panelAlgo.add(new JLabel("Choisissez un algrotithme  : "));
		typeAlgo.add(rbAvant); typeAlgo.add(rbArriere);
		panelAlgo.add(rbAvant); panelAlgo.add(rbArriere);
				
		//add critere
		panelCritere.setLayout(new GridLayout(1,2));
		panelCritere.add(new JLabel("Choisissez un critere : "));
		panelCritere.add(criteres);
				
		panelLancer.add(prouverBut);
		panelLancer.add(saturer);
				
				
		//set listeners
		parcourir1.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
			        JFileChooser chooser = new JFileChooser();
			        int returnVal = chooser.showOpenDialog(null);
			        if(returnVal == JFileChooser.APPROVE_OPTION) {
		       		String filePath=chooser.getSelectedFile().getAbsolutePath();
		       				labelPATH1.setText(filePath);
				            controller.parseFactFile(filePath);
				    }
				 
			}
		} );
				
		parcourir2.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent e){
		        JFileChooser chooser = new JFileChooser();
		        int returnVal = chooser.showOpenDialog(null);
		        if(returnVal == JFileChooser.APPROVE_OPTION) {
		        	String filePath=chooser.getSelectedFile().getAbsolutePath();
		        	labelPATH2.setText(filePath);
			        controller.parseRuleFile(filePath);
		        }
				 
		    }
		} );
				
		addFB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				controller.addFact(nomfB.getText());
			}
		});
				
		addRegle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				controller.addRule(nomRegle.getText());
			}
		});
				
		prouverBut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				String algo="";
				Enumeration<AbstractButton> allRadioButton=typeAlgo.getElements();  
				while(allRadioButton.hasMoreElements()) {  
				         JRadioButton temp=(JRadioButton)allRadioButton.nextElement();  
					     if(temp.isSelected()) {  
					            algo=temp.getText();
					     }  
				}            
				if(algo == "")
					setLabelMessage("selectionnez un algorithme ....",Color.red);
				else{
					labelMessage.setText("");
					controller.prouverBut(faitBut.getText(),algo,String.valueOf(criteres.getSelectedItem()));
				}
			}
		});
				
		saturer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				controller.saturerBase(String.valueOf(criteres.getSelectedItem()));
			}
		});		
				
		this.setLayout(new GridLayout(15,1));
		this.add(fileChooser);
		this.add(fileChooser2);
		this.add(new JLabel("Ajout d'un fait : "));
		this.add(panelFaitAjoute);
		this.add(new JLabel("Ajout d'une régle : "));
		this.add(panelAjoutFR);
		this.add(panelFaitBut);
		this.add(panelAlgo);
		this.add(panelCritere);
		this.add(panelLancer,"East");
		this.add(labelMessage);

	}
	
	public void setLabelInfoFait(String text,Color c){
		this.labelINFO1.setForeground(c);
		this.labelINFO1.setText(text);
	}
	
	public void setLabelInfoRegle(String text,Color c){
		this.labelINFO2.setForeground(c);
		this.labelINFO2.setText(text);
	}
	
	public void setLabeladdFact(String text,Color c){
		this.labeladdFact.setForeground(c);
		this.labeladdFact.setText(text);
	}
	
	public void setLabeladdRule(String text,Color c){
		this.labeladdRule.setForeground(c);
		this.labeladdRule.setText(text);
	}
	
	public void setLabeladdBut(String text,Color c){
		this.labeladdBut.setForeground(c);
		this.labeladdBut.setText(text);
	}
	
	public void setLabelMessage(String text,Color c){
		this.labelMessage.setForeground(c);
		this.labelMessage.setText(text);
	}
	
}
