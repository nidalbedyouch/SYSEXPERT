package com.faits;

import java.util.Vector;


public abstract class InterfaceFait {
	
	protected String nom;
	InterfaceFait father;
	Vector<InterfaceFait> children;
	
	
	public InterfaceFait(String pnom){
		this.nom=pnom;
		this.father=null;
		this.children=new Vector<InterfaceFait>();
	}
	
	public String getNom() {
		return nom;
	}
	
	public abstract Object getValeur();
	public abstract boolean equals(InterfaceFait fait);
	public abstract boolean evaluer(BaseDeFaits bdf);

	public int getChildCount() {
		return children.size();
	}

	public Object getFather() {
		return father;
	}

	public int getIndexOfChild(InterfaceFait child) {
		return children.indexOf(child);
	}

	
	public Object getChildAt(int index) {
		// TODO Auto-generated method stub
		return (InterfaceFait) children.elementAt(index);
	}

	
	public static void linkFamily(InterfaceFait pa,
			Vector<InterfaceFait> kids) {
		if(!kids.isEmpty()){
			 for (InterfaceFait kid : kids) {
				  System.out.println("kid : "+kid.toString()+"		"+pa.toString());
			      pa.children.addElement(kid);
			      kid.father = pa;
		     }
		}
		
	}
	
}
