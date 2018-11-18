package com.regles;


import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.StringJoiner;

import com.faits.BaseDeFaits;
import com.faits.InterfaceFait;

public class Regles {

	protected String nom;
	protected ArrayList<InterfaceFait> premisses;
	protected InterfaceFait conclusion;
	protected LocalDateTime lastUsed;
	
	public Regles(String pnom, ArrayList<InterfaceFait> pPremisses, InterfaceFait pconclusion) {
		this.nom = pnom;
		this.premisses = pPremisses;
		this.conclusion = pconclusion;
		this.lastUsed=LocalDateTime.of(2001, Month.JANUARY, 1, 1, 1, 1);
	}
	
	public ArrayList<InterfaceFait> getPremisses(){
		return this.premisses;
	}
	
	public void setPremisses(ArrayList<InterfaceFait> ppremisses) {
		this.premisses = ppremisses;
	}
	
	public InterfaceFait getConclusion() {
		return this.conclusion;
	}
	
	public void setConclusiosn(InterfaceFait pconclusion) {
		this.conclusion = pconclusion;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void setNom(String pnom) {
		this.nom = pnom;
	}
	
	public boolean equals(Regles regle){
		if(this.premisses.equals(regle.getPremisses()) && this.conclusion.equals(regle.getConclusion()) ){
			return true;
		}
		return false;
	}
	
	public boolean evaluer(BaseDeFaits bdf){
		boolean evaluated=true;
		for(InterfaceFait premisse : this.premisses){
			evaluated=evaluated && premisse.evaluer(bdf);
		}
		return evaluated;
	}
	
	public boolean applicable(BaseDeFaits bdf){
		boolean applicable=true;
		for(InterfaceFait fait : this.premisses){
			if(!bdf.faitExists(fait))
				applicable=false;
		}
		return applicable;
	}
	
	public int nbPremisseaSatisfaire(BaseDeFaits bdf){
		int nb=0;
		for(InterfaceFait fait : this.premisses){
			if(!bdf.faitExists(fait))
				nb++;
		}
		return nb;
	}
	
	public void updateLastUsed(){
		this.lastUsed=LocalDateTime.now();
	}
	
	public String toString(){
		String str = this.nom + " : SI (" ;
		StringJoiner sj = new StringJoiner(" ET ");
		for (InterfaceFait fait : premisses) {
			sj.add(fait.toString());
		}
		
		str += sj.toString() + ") ALORS " + this.conclusion.toString();
		
		return str;
	}

	public int nbPremisse() {
		return this.premisses.size();
	}
}
