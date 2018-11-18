package com.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.faits.BaseDeFaits;
import com.faits.FaitsBooleans;
import com.faits.FaitsEntiers;
import com.faits.InterfaceFait;
import com.faits.SigneComparaison;
import com.moteurInference.MoteurInferences;
import com.regles.*;

public class AnalyseurSyntaxique {
	public Scanner openFile(String filePath){
		Scanner sc;
		try {
			sc= new Scanner(new File(filePath));
			return sc;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<String> readLine(Scanner sc){
		ArrayList<String> lignes=new ArrayList<String>();
		while(sc.hasNextLine()){
			lignes.add(sc.nextLine());
		}
		sc.close();
		return lignes;
	}
	
	public InterfaceFait getFact(String ligne,int index){
		InterfaceFait fait=null;
		System.out.println(ligne);
		if(!ligne.isEmpty()){
			String[] parts=ligne.split(" ",3);
			for(String a : parts)
				System.out.println(a);
			//fait booleens 
			if((parts[2].equals("true")) || (parts[2].equals("false"))){
				if(parts[1].equals("=")){
					boolean valeur=false;
					if(parts[2].equals("true")){
						valeur=true;
					}
					fait=new FaitsBooleans(parts[0],valeur);
				}else{
					System.out.println("Erreur Syntaxe ligne "+index);
					return null;
				}
			}
			//fait entier
			else{
				try{
				int valeur=Integer.parseInt(parts[2]);
				fait=new FaitsEntiers(parts[0],valeur,getSigne(parts[1]));
				}catch(NumberFormatException e){
					System.out.println("Valeur non prise en compte");
				}
			}
		}
		return fait;
	}
	public ArrayList<InterfaceFait> getPremisses(String premisses){
		System.out.println(premisses);
		String[] parts=premisses.split(" & ");
		ArrayList<InterfaceFait> faits=new ArrayList<InterfaceFait>();
		int index=1;
		for(String premisse : parts){
			InterfaceFait fait=getFact(premisse,index);
			if(fait != null){
				faits.add(fait);
				index++;
			}else{
				return new ArrayList<InterfaceFait>();
			}
		}
		return faits;
	}
	public SigneComparaison getSigne(String signe){
		switch(signe){
			case ">=" : return SigneComparaison.SUPERIEUREGALE;
			case "<=" : return SigneComparaison.INFERIEUREGALE;
			case "=" : return SigneComparaison.EGALE;
			default :return SigneComparaison.EGALE;
		}
	}
	
	public boolean parseFacts(String filePath,MoteurInferences moteur){
		Scanner sc=openFile(filePath);
		if(sc != null){
			ArrayList<String> lignes=readLine(sc);
			for(String ligne : lignes){
				InterfaceFait fait=getFact(ligne,lignes.indexOf(ligne)+1);
				if(fait != null)
					moteur.addFact(fait);
				else
					return false;
			}
			return true;
		}else{
			//file isn't found
			return false;
		}
	
	}
	
	public Regles getRegle(String ligne,int index){
		String[] parts=ligne.split(" := ", 2);
		if(parts[0].equals(ligne))
			return null;
		InterfaceFait conclusion=getFact(parts[1],index);
		ArrayList<InterfaceFait> premisse=getPremisses(parts[0]);
		if(!premisse.isEmpty() && conclusion != null)
			return new Regles("R"+index,premisse,conclusion);
		else
			return null;
	}
	
	public boolean parseRules(String filePath,MoteurInferences moteur){
		Scanner sc=openFile(filePath);
		if(sc != null){
			ArrayList<String> lignes=readLine(sc);
			for(String ligne : lignes){
				int index=lignes.indexOf(ligne)+1;
				Regles rule=getRegle(ligne,index);
				if(rule != null)
					moteur.addRule(rule);
				else 
					return false;
			}
			return true;
		}else{
			//file isn't found
			return false;
		}
		
	}
}
