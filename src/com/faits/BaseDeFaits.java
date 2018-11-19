package com.faits;

import java.awt.Color;
import java.util.ArrayList;

import com.ccontroller.Controller;

public class BaseDeFaits {

	protected ArrayList<InterfaceFait> faits ;
	private Controller controller;
	
	public BaseDeFaits(Controller c) {
		this.faits = new ArrayList<InterfaceFait>();
		this.controller=c;
	}
	
	public ArrayList<InterfaceFait> getFaits(){
		return this.faits;
	}
	
	public void viderBaseDeFaits() {
		this.faits.clear();
	}
	
	public void ajouterFait(InterfaceFait pFait) {
		if(!faitExists(pFait)){
				this.faits.add(pFait);
				this.controller.addMessage("fait ajouté",Color.green);
		}else if(!pFait.evaluer(this)){
				this.controller.addMessage("fait oposé existe déjà",Color.red);
			}
			else
				this.controller.addMessage("fait existe déjà",Color.ORANGE);
	}
	
	public InterfaceFait chercher(String nom) {
		for(InterfaceFait fait : this.faits) {
			if (fait.getNom().equals(nom)) {
				return fait;
			}
		}
		return null;
	}
	
	public boolean faitExists(InterfaceFait fait){
		if(chercher(fait.getNom()) != null)
			return true;
		else
			return false;
	}
	
	public Object recupererValeur(String nom) {
		for(InterfaceFait fait : this.faits) {
			if (fait.getNom().equals(nom)) {
				return fait.getValeur();
			}
		}
		return null;
	}
	
	public String toString() {
		String str = "Base de faits : \n ";
		
		for (InterfaceFait fait : this.faits) {
			str += "	-" + fait.toString() + "\n";
		}
		
		return str; 
	}

	public boolean vide() {
		// TODO Auto-generated method stub
		return this.faits.isEmpty();
	}
	
}
