package com.moteurInference;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.ccontroller.Controller;
import com.faits.BaseDeFaits;
import com.faits.InterfaceFait;
import com.regles.BaseDeRegles;
import com.regles.Regles;
import com.regles.critereChoixRegle;

public class MoteurInferences implements Sujet{

	protected BaseDeFaits mbdf;
	protected BaseDeRegles mbdr;
	protected InterfaceFait mbut;
	protected critereChoixRegle mcritere; 
	private int niveauMaxRegle;
	private Controller controller;
	private List<Observateur> observateurs = new ArrayList<>();

	public MoteurInferences(Controller c) {
		this.controller=c;
		String str = "++++++++++++++++++++++++++++++\r\n" + 
				"+ Moteur Inférences Ordre 0+ +\r\n" + 
				"++++++++++++++++++++++++++++++\n";
		this.controller.appendText(str);
		this.mbdf = new BaseDeFaits(c);
		this.mbdr = new BaseDeRegles(c);
		this.mbut=null;
	}

	public void chainageAvant() {
		//en profondeur
		this.controller.appendText(this.mbdf.toString());
		BaseDeRegles bdr=this.mbdr;
		this.controller.appendText(bdr.toString());
		BaseDeRegles bdrApplicable=bdr.reglesApplicable(this.mbdf);
		while((this.mbut == null || !this.mbdf.faitExists(this.mbut)) && !bdrApplicable.isEmpty()){
			this.controller.appendText("les règles apllicables "+bdrApplicable.toString());
			Regles regle=bdrApplicable.choisirRegle(this.mcritere,this.mbdf);
			this.controller.appendText("Règle choisie "+regle.toString()+"\n");
			//désactivation de la regle 
			bdrApplicable.effacerRegle(regle);
			bdr.effacerRegle(regle);
			//declenchement de la régle et ajout de la conclusion dans la base des faits
			if(regle.evaluer(this.mbdf) && !this.mbdf.faitExists(regle.getConclusion())){
				this.controller.appendText("Ajout nouveau fait "+regle.getConclusion().toString()+"\n");
				this.mbdf.ajouterFait(regle.getConclusion());
			}
			bdrApplicable=bdr.reglesApplicable(this.mbdf);
		}
		if(this.mbut==null){
			//saturation base des faits
			this.controller.appendText("********Base Saturé********\n");
			this.controller.appendText("Nouvelle Base de fait : \n"+this.mbdf.toString());
			
		}else if(this.mbdf.faitExists(this.mbut)){
			//but établi
			this.controller.appendText("*****BUT ETABLI :"+this.mbut.toString());
			
		}else {
			//but n'est pas établi
			this.controller.appendText("*****BUT NON ETABLI :"+this.mbut.toString());	
		}
	}

	public boolean chainagearriere(InterfaceFait F) {
		this.controller.appendText("Base de Règle initiale : \n"+this.mbdr+"\n");
		this.controller.appendText("Base de Fait initiale : "+this.mbdf+"\n");
		// Cas où le but est déjà dans la base de faits initialement.
		Vector<InterfaceFait> kids=new Vector<InterfaceFait>();
		if (this.mbdf.faitExists(F)) {
			this.controller.appendText("Fait établi (existe dans la base des faits) : "+F.toString()+"\n");
			return true;
		}else {
			BaseDeRegles ER = new BaseDeRegles(this.controller); //recuppérer les règles dont la conclusion égale au fait à démontrer
			for (Regles regle : this.mbdr.getRegles()) {
				if (regle.getConclusion().equals(F)) {
					ER.ajouterRegle(regle);
					System.out.println(regle.toString());
				}
			}
			this.controller.appendText("Base de règle avec conclusion "+ER.toString()+"\n");
			boolean valide;
			do {
				valide = true;
				if (ER.isEmpty()) {
					break;
				}
				Regles R = ER.choisirRegle(this.mcritere,this.mbdf);
				this.controller.appendText("Règle choisi : "+R.toString()+"\n");
				ER.effacerRegle(R);
				for (InterfaceFait Fr : R.getPremisses()) {
					this.controller.appendText("Lancement chainage arrière pour but : "+Fr.toString()+"\n");
					kids.add(Fr);
					valide = valide && this.chainagearriere(Fr);
				}
			} while (valide || ER!=null);
			if (valide && ER.isEmpty()) {
				this.controller.appendText("Ajout dans la base de faits de la conclusion : ["+F.getNom()+"]\n");
				this.mbdf.ajouterFait(F);
				this.controller.appendText("===========================BASE DE FAITS=================================\n");
				this.controller.appendText("La base de faits a été mise à jour. Affichage de la nouvelle base de faits (Nombre de faits : "+this.mbdf.getFaits().size()+") : \n");
				this.controller.appendText(this.mbdf.toString());
				this.controller.appendText("=============================================================================\n");
				if (this.mbut.equals(F)) {
					this.controller.appendText("Fin de l'algorithme du chaînage arrière. Le but [" + this.mbut.getNom()+ "] est atteint.\n");
				}
			}else {
				this.controller.appendText("Fin de l'algorithme du chaînage arrière. Le but [" + F.getNom()+ "] n'a pas étè atteint.\n");
				return false;
			}
			InterfaceFait.linkFamily(F, kids);
			return valide;
		}
	}
	
	public void addFact(InterfaceFait fait){
		this.mbdf.ajouterFait(fait);
	}
	
	public void addRule(Regles regle){
		this.mbdr.ajouterRegle(regle);
	}
	
	public void setBut(InterfaceFait fait){
		this.mbut=fait;
	}
	
	public void setCritere(String c){
		this.controller.appendText("Critere choisi "+c+"\n");
		switch(c){
			case"premiere règle":
						this.mcritere=critereChoixRegle.PREMIERE;
						break;
			case "complexe":
						this.mcritere=critereChoixRegle.COMPLEXE;
						break;
			case "plus de premisses à satisfaire":
						this.mcritere=critereChoixRegle.PLUSPREM;
						break;
			case "récence règle":
						this.mcritere=critereChoixRegle.RECENCE;
						break;
		}
	}
	
	public int getNiveauMaxRegle() {
		return niveauMaxRegle;
	}

	public void setNiveauMaxRegle(int niveauMaxRegle) {
		this.niveauMaxRegle = niveauMaxRegle;
	}

    public void enregistrerObservateur(Observateur observateur){observateurs.add(observateur);}
	
	public void supprimerObservateur(Observateur observateur){observateurs.remove(observateur);}

	public void notifierObservateur() {
		for(int i = 0; i< observateurs.size(); i++) {
			Observateur observateur = observateurs.get(i);
			observateur.actualiser();
		}
	}
	

}
