package com.faits;

public class FaitsBooleans extends InterfaceFait {

	
	protected Boolean valeur;
	
	public FaitsBooleans(String pnom, Boolean pvaleur) {
		super(pnom);
		this.valeur = pvaleur;
	}
	
	

	@Override
	public Object getValeur() {
		return this.valeur;
	}

	
	
	public String toString() {
		String str = this.getNom() + " = " + getValeur();
		return str;
	}
	
	private boolean equals(FaitsBooleans fait){
		return this.nom.equals(fait.nom) && this.valeur.equals(fait.valeur);
	}

	@Override
	public boolean evaluer(BaseDeFaits bdf) {
		InterfaceFait fait=bdf.chercher(this.getNom());
		return fait.getValeur().equals(this.getValeur());
	}

	@Override
	public boolean equals(InterfaceFait fait) {
		if(fait instanceof FaitsEntiers)
			return false;
		else
			return equals((FaitsBooleans) fait);
	}

}
