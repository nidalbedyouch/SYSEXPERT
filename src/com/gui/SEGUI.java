package com.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.ccontroller.Controller;
import com.faits.InterfaceFait;
import com.moteurInference.*;

//Classe pour l'interface graphique utilisateur 
public class SEGUI extends JFrame implements Observateur{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7624870242645119750L;
	
	
	
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fichier = new JMenu("Fichier");
	private JMenu pointInterrogation = new JMenu("?");
	private JMenuItem ouvrir = new JMenuItem("Ouvrir");
	private JMenuItem fermer = new JMenuItem("Fermer");
	private JMenuItem aPropos = new JMenuItem("A propos...");
	
	private Tree tree;
	private FormPanel formPanel;
	private TextPanel textPanel;
	private Controller controller;

	public SEGUI(Controller c) {
		
		//D�finit un titre pour notre fen�tre
		this.setTitle("Intelligence Artificielle - Système Expert");
		//D�finit sa taille : 400 pixels de large et 100 pixels de haut
		this.setSize(1800, 700);
		//Nous demandons maintenant � notre objet de se positionner au centre
		this.setLocationRelativeTo(null);
		//Impossible de redimensionner la fen�tre : 
		this.setResizable(true);
		//Termine le processus lorsqu'on clique sur la croix rouge
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLayout(new BorderLayout());
		this.formPanel=new FormPanel(this,c);
		this.textPanel=new TextPanel();
		
		this.controller=c;
		//JMenuBar
		//On initialise nos menus      
		this.fichier.add(ouvrir);

		//Ajout d'un s�parateur
		this.fichier.addSeparator();
		
		this.pointInterrogation.add(aPropos);
		
		//Fermeture du programme si on clique sur Fermer
		fermer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}        
		});
		
		this.fichier.add(fermer);
		
		this.aPropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Intelligence Artificielle - Système Expert - All Rights Reserved", "A propos", JOptionPane.INFORMATION_MESSAGE);

			}
		});
		

		//L'ordre d'ajout va d�terminer l'ordre d'apparition dans le menu de gauche � droite
		//Le premier ajout� sera tout � gauche de la barre de menu et inversement pour le dernier
		this.menuBar.add(fichier);
		this.menuBar.add(pointInterrogation);
		this.setJMenuBar(menuBar);
		
		//Et enfin, la rendre visible  
		this.add(formPanel,BorderLayout.WEST);
		this.add(textPanel,BorderLayout.CENTER);
		
		this.setVisible(true);
	}

	public void setTree(Tree t){
		this.tree=t;
		this.add(tree,BorderLayout.EAST);
	}
	public void setLabelInfoFait(String text,Color c){
		this.formPanel.setLabelInfoFait(text,c);
	}
	
	public void setLabelInfoRegle(String text,Color c){
		this.formPanel.setLabelInfoRegle(text,c);
	}
	
	public void setLabeladdFact(String text,Color c){
		this.formPanel.setLabeladdFact(text,c);
	}
	
	public void setLabeladdRule(String text,Color c){
		this.formPanel.setLabeladdRule(text,c);
	}
	
	public void setLabeladdBut(String text,Color c){
		this.formPanel.setLabeladdBut(text,c);
	}
	
	public void setLabelMessage(String text,Color c){
		this.formPanel.setLabelMessage(text,c);
	}
	
	public void appendText(String text){
		textPanel.appendText(text);
	}
	
	public void setTreeRoot(InterfaceFait root){
		tree.setTreeRoot(root);
	}
	@Override
	public void actualiser() {
		// TODO Auto-generated method stub
		
	}

}
