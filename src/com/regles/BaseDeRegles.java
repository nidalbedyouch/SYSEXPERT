package com.regles;

import java.awt.Color;
import java.util.ArrayList;

import com.ccontroller.Controller;
import com.faits.BaseDeFaits;

public class BaseDeRegles {

	protected ArrayList<Regles> regles;
	private Controller controller;
	
	public BaseDeRegles(Controller c) {
		this.regles = new ArrayList<Regles>();
		this.controller=c;
	}
	
	
	public ArrayList<Regles> getRegles(){
		return this.regles;
	}
	
	public void setRegles(ArrayList<Regles> pRegles) {
		for (Regles regle : pRegles) {
			Regles copie = new Regles(regle.nom, regle.premisses, regle.conclusion);
			this.regles.add(copie);
		}
	}
	
	public void viderBaseDeRegles() {
		this.regles.clear();
	}
	
	public void ajouterRegle(Regles pR) {
		boolean peutEtreAjouter=true;
		if(!this.regles.isEmpty()){
			for(Regles regle : this.regles){
				if(regle.equals(pR)){
					this.controller.addMessage("Ajout impossible " +pR.toString()+" existe déjà",Color.red);
					peutEtreAjouter=false;
				}
				if(regle.conclusion.equals(pR.conclusion))
					this.controller.addMessage("Deux rêgles ont la même conclusion (Redondance)",Color.orange);
				if(regle.premisses.equals(pR.premisses))
					this.controller.addMessage("Deux rêgles ont les même prémisses (Redondance)",Color.orange);
			}
		}
		if(peutEtreAjouter)
			this.regles.add(pR);
	}
	
	public void effacerRegle(Regles pR) {
		this.regles.remove(pR);
	}
	
	public String toString() {
		String str = "Base de regles : \n";
		for (Regles regle : regles) {
			str += "	-" + regle.toString()+"\n";
		}
		
		return str;
	}

	public BaseDeRegles reglesApplicable(BaseDeFaits bdf) {
		BaseDeRegles reglesApplicables=new BaseDeRegles(this.controller);
		for(Regles regle : this.regles){
			if(regle.applicable(bdf))
				reglesApplicables.ajouterRegle(regle);
		}
		return reglesApplicables;
	}

	public boolean isEmpty() {
		return regles.isEmpty();
	}

	public Regles choisirRegle(critereChoixRegle critere,BaseDeFaits bdf) {
		switch(critere){
			case PREMIERE : 
				return this.regles.get(0);
			case COMPLEXE:
				return this.getComplexeRule();
			case PLUSPREM:
				return this.getPlusPrem(bdf);
			case RECENCE:
				return this.getRecentRule();
			default :
				return this.regles.get(this.regles.size()-1);
		}
	}


	private Regles getRecentRule() {
		Regles returnRegle=this.regles.get(0);
		for(Regles regle : this.regles){
			if(regle.lastUsed.isAfter(returnRegle.lastUsed))
				returnRegle=regle;
		}
		return returnRegle;
	}


	private Regles getPlusPrem(BaseDeFaits bdf) {
		Regles r=this.regles.get(0);
		int maxPrem=-1;
		for(Regles regle : this.regles){
			int nbPrem=regle.nbPremisseaSatisfaire(bdf);
			if(nbPrem > maxPrem){
				maxPrem=nbPrem;
				r=regle;
			}
		}
		return r;
	}


	private Regles getComplexeRule() {
		Regles r=this.regles.get(0);
		int maxComplx=-1;
		for(Regles regle : this.regles){
			int nbPrem=regle.nbPremisse();
			if(nbPrem > maxComplx){
				maxComplx=nbPrem;
				r=regle;
			}
		}
		return r;
	}

	
}
