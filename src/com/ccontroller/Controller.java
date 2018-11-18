package com.ccontroller;

import java.awt.Color;
import java.util.Vector;

import com.faits.InterfaceFait;
import com.gui.SEGUI;
import com.gui.Tree;
import com.moteurInference.MoteurInferences;
import com.parser.AnalyseurSyntaxique;
import com.regles.Regles;

public class Controller implements Runnable{
	private MoteurInferences moteur;
	private SEGUI view;
	private AnalyseurSyntaxique analyseur;
	public Controller(){
		this.view=new SEGUI(this);
		this.moteur=new MoteurInferences(this);
		this.analyseur=new AnalyseurSyntaxique();
	}
	public void parseFactFile(String filePath){
		if(analyseur.parseFacts(filePath, moteur))
			view.setLabelInfoFait("fichier analysé ",Color.GREEN);
		else
			view.setLabelInfoFait("Erreur fichier ",Color.red);
	}
	
	public void parseRuleFile(String filePath){
		if(analyseur.parseRules(filePath, moteur))
			view.setLabelInfoRegle("fichier analysé ",Color.green);
		else
			view.setLabelInfoRegle("Erreur fichier ",Color.red);
	}
	
	public void addFact(String fait){
		InterfaceFait fact=analyseur.getFact(fait,0);
		if(fact != null){
			moteur.addFact(fact);
		}
		else
			view.setLabeladdFact("Erreur syntaxique ",Color.red);
	}
	
	public void addRule(String rule){
		Regles regle=analyseur.getRegle(rule,0);
		if(regle != null){
			moteur.addRule(regle);
			view.setLabeladdRule("Règle ajouté ",Color.green);
		}
		else
			view.setLabeladdRule("Erreur syntaxique ",Color.red);
	}
	
	
	public void prouverBut(String but,String algo,String critere){
		InterfaceFait fait=analyseur.getFact(but, 0);
		if(fait != null){
			moteur.setBut(fait);
			moteur.setCritere(critere);
			if(algo=="chainage avant"){
				moteur.chainageAvant();
				view.setLabelMessage("But "+but+"chainage avant lancer..."+"ciritere : "+critere,Color.green);
			}else{
				moteur.chainagearriere(fait);
				this.setTree(fait);
				view.setLabelMessage("But "+but+"chainage avant lancer..."+"ciritere : "+critere,Color.green);
			}
				
		}
		else{
			view.setLabelMessage("But Erreur syntaxique ",Color.red);
		}
	}
	
	public void saturerBase(String critere){
		moteur.setCritere(critere);
		moteur.chainageAvant();
		view.setLabelMessage("chainage avant lancer pour saturation de la base..."+"ciritere : "+critere,Color.green);
	}
	
	public void appendText(String text){
		view.appendText(text);
	}
	
	public void addMessage(String text,Color c){
		view.setLabelMessage(text, c);
	}
	
	public void setTree(InterfaceFait fait){
		view.setTree(new Tree(fait));
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
