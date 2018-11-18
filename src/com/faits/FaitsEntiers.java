package com.faits;


public class FaitsEntiers extends InterfaceFait {

	protected int valeur;
	protected SigneComparaison signe;
	
	
	public FaitsEntiers(String pnom, int pvaleur,SigneComparaison psigne) {
		super(pnom);
		this.valeur = pvaleur;
		this.signe=psigne;
	}
	

	public Object getValeur() {
		return this.valeur;
	}

	public String signeToString(){
		String chaine="";
		switch(this.signe){
			case SUPERIEUREGALE:
				chaine= ">=";break;
			case INFERIEUREGALE:
				chaine="<=";break;
			case EGALE:
				chaine="=";break;
			default:
				chaine="=";
		}
		return chaine;
	}
	
	private boolean equals(FaitsEntiers fait){
		return this.nom.equals(fait.nom) && this.valeur==fait.valeur && this.signe.equals(fait.signe);
	}
	
	public String toString() {
		String str = this.getNom() + this.signeToString() + getValeur();
		return str;
	}

	@Override
	public boolean evaluer(BaseDeFaits bdf) {
		boolean b=true;
		InterfaceFait fait=bdf.chercher(this.getNom());
		int valeurFait=(int) fait.getValeur(),valeurRef=(int) this.getValeur();
		
		switch(this.signe){
		case SUPERIEUREGALE:
			b=valeurFait >= valeurRef;break;
		case INFERIEUREGALE:
			b=valeurFait <= valeurRef;break;
		case EGALE:
			b=valeurFait == valeurRef;break;
		default:
			b=valeurFait == valeurRef;
	}
		return b;
	}

	@Override
	public boolean equals(InterfaceFait fait) {
		if(fait instanceof FaitsEntiers)
			return equals((FaitsEntiers) fait);
		else
			return false;
	}

}
