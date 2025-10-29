package GUI.Model;

import DS.AVL.AVL;
import DS.AVL.AVL_Node;
import Data.Osoba;
import Data.PCR_Test;

import java.util.ArrayList;

public class Model {
    private AVL<Osoba> osobyAVL;
    private AVL<PCR_Test> pcrTestsAVL;

    public Model() {
        this.osobyAVL = new AVL<>();
        this.pcrTestsAVL = new AVL<>();
    }

    public void vlozPCRTest(AVL_Node<PCR_Test> pcrTest) {

    }

    public AVL_Node<PCR_Test> vyhladajVysledokPCRTestu(AVL_Node<PCR_Test> pcrTest) {
        return null;
    }

    public ArrayList<AVL_Node<PCR_Test>> vyhladajVsetkyTestyPreOsobu(AVL_Node<Osoba> osoba) {
        return null;
    }

    public ArrayList<AVL_Node<PCR_Test>> vyhladajPozitivneTestyPreDatumAOkres(AVL_Node<PCR_Test> pcrTest) {
        return null;
    }

    public ArrayList<AVL_Node<PCR_Test>> vyhladajTestyPreDatumAOkres(AVL_Node<PCR_Test> pcrTest) {
        return null;
    }

    public ArrayList<AVL_Node<PCR_Test>> vyhladajPozitivneTestyPreDatumAKraj(AVL_Node<PCR_Test> pcrTest) {
        return null;
    }

    public ArrayList<AVL_Node<PCR_Test>> vyhladajTestyPreDatumAKraj(AVL_Node<PCR_Test> pcrTest) {
        return null;
    }

    public ArrayList<AVL_Node<PCR_Test>> vyhladajPozitivneTestyPreDatum(AVL_Node<PCR_Test> pcrTest) {
        return null;
    }

    public ArrayList<AVL_Node<PCR_Test>> vyhladajTestyPreDatum(AVL_Node<PCR_Test> pcrTest) {
        return null;
    }

    public ArrayList<AVL_Node<Osoba>> vypisChorychOsobPreDatumAOkres() {
        return null;
    }

    public ArrayList<AVL_Node<Osoba>> vypisChorychOsobPreDatumAOkresUsporiadaneHodnotouTestu() {
        return null;
    }

    public ArrayList<AVL_Node<Osoba>> vypisChorychOsobPreDatumAKraj() {
        return null;
    }

    public ArrayList<AVL_Node<Osoba>> vypisChorychOsobPreDatum() {
        return null;
    }

    public ArrayList<AVL_Node<Osoba>> vypisOsobyPreDatumKazdyOkresSNajvyssouHodnotouTestu() {
        return null;
    }

    public ArrayList<AVL_Node> vypisOkresovUsporiadanychPoctomChorychPreDatum() {
        return null;
    }

    public ArrayList<AVL_Node> vypisKrajovUsporiadanychPoctomChorychPreDatum() {
        return null;
    }

    public ArrayList<AVL_Node> vypisTestovPreDatumAPracovisko() {
        return null;
    }

    public AVL_Node<PCR_Test> vyhladaniePCRTestu(AVL_Node<PCR_Test> pcrTest) {
        return null;
    }

    public void vlozOsobu(AVL_Node<Osoba> osoba) {

    }

    public void vymazPCRTest(AVL_Node<PCR_Test> pcrTest) {

    }

    public void vymazOsobu(AVL_Node<Osoba> osoba) {

    }
}
