package com.moteurInference;



public interface Sujet {

    public void enregistrerObservateur(Observateur observateur);
    public void supprimerObservateur(Observateur observer);
    public void notifierObservateur();
    
}
