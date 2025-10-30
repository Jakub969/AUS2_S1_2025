package GUI.Model;

import DS.AVL.AVL;
import DS.AVL.AVL_Node;
import DS.BST.BST_Node;
import Data.Osoba.Osoba;
import Data.PCR_Test.*;

import java.util.ArrayList;
import java.util.Date;

public class Model {
    private AVL<Osoba> osobyAVL;
    private AVL<PCR_Test> pcrTestsAVL;
    private AVL<PCR_TestDatumWrapper> pcrTestsDatumAVL;
    private AVL<PCR_TestOkresWrapper> pcrTestsOkresAVL;
    private AVL<PCR_TestKrajWrapper> pcrTestsKrajAVL;
    private AVL<PCR_TestHodnotaTestuWrapper> pcrTestsHodnotaAVL;
    private AVL<PCR_TestDatumWrapper> pcrPozitivneTestsDatumAVL;
    private AVL<PCR_TestDatumWrapper> pcrNegativneTestsDatumAVL;
    private AVL<PCR_TestUUIDOsobyWrapper> pcrTestsUUIDOsobyAVL;
    private AVL<PCR_TestOkresDatumWrapper> pcrPozitivneTestsOkresDatumAVL;
    private AVL<PCR_TestOkresDatumWrapper> pcrTestOkresDatumAVL;

    public Model() {
        this.osobyAVL = new AVL<>();
        this.pcrTestsAVL = new AVL<>();
        this.pcrTestsDatumAVL = new AVL<>();
        this.pcrTestsOkresAVL = new AVL<>();
        this.pcrTestsKrajAVL = new AVL<>();
        this.pcrTestsHodnotaAVL = new AVL<>();
        this.pcrPozitivneTestsDatumAVL = new AVL<>();
        this.pcrNegativneTestsDatumAVL = new AVL<>();
        this.pcrTestsUUIDOsobyAVL = new AVL<>();
        this.pcrPozitivneTestsOkresDatumAVL = new AVL<>();
    }

    public void vlozPCRTest(AVL_Node<PCR_Test> pcrTest) {
        this.pcrTestsAVL.insert(pcrTest);
        PCR_TestDatumWrapper testDatumWrapper = new PCR_TestDatumWrapper(pcrTest.getData());
        AVL_Node<PCR_TestDatumWrapper> pcrTestsDatumWrapperNode = new AVL_Node<>(testDatumWrapper,testDatumWrapper);
        this.pcrTestsDatumAVL.insert(pcrTestsDatumWrapperNode);
        PCR_TestOkresWrapper testOkresWrapper = new PCR_TestOkresWrapper(pcrTest.getData());
        AVL_Node<PCR_TestOkresWrapper> pcrTestsOkresWrapperNode = new AVL_Node<>(testOkresWrapper,testOkresWrapper);
        this.pcrTestsOkresAVL.insert(pcrTestsOkresWrapperNode);
        PCR_TestOkresDatumWrapper okresDatumWrapper = new PCR_TestOkresDatumWrapper(pcrTest.getData());
        AVL_Node<PCR_TestOkresDatumWrapper> node = new AVL_Node<>(okresDatumWrapper, okresDatumWrapper);
        this.pcrTestOkresDatumAVL.insert(node);
        PCR_TestKrajWrapper testKrajWrapper = new PCR_TestKrajWrapper(pcrTest.getData());
        AVL_Node<PCR_TestKrajWrapper> pcrTestsKrajWrapperNode = new AVL_Node<>(testKrajWrapper,testKrajWrapper);
        this.pcrTestsKrajAVL.insert(pcrTestsKrajWrapperNode);
        PCR_TestHodnotaTestuWrapper testHodnotaWrapper = new PCR_TestHodnotaTestuWrapper(pcrTest.getData());
        AVL_Node<PCR_TestHodnotaTestuWrapper> pcrTestsHodnotaWrapperNode = new AVL_Node<>(testHodnotaWrapper,testHodnotaWrapper);
        this.pcrTestsHodnotaAVL.insert(pcrTestsHodnotaWrapperNode);
        if (pcrTest.getData().isVysledokTestu()) {
            this.pcrPozitivneTestsDatumAVL.insert(pcrTestsDatumWrapperNode);
            this.pcrPozitivneTestsOkresDatumAVL.insert(node);
        } else {
            this.pcrNegativneTestsDatumAVL.insert(pcrTestsDatumWrapperNode);
        }
        PCR_TestUUIDOsobyWrapper testUUIDOsobyWrapper = new PCR_TestUUIDOsobyWrapper(pcrTest.getData());
        AVL_Node<PCR_TestUUIDOsobyWrapper> pcrTestsUUIDOsobyWrapperNode = new AVL_Node<>(testUUIDOsobyWrapper,testUUIDOsobyWrapper);
        this.pcrTestsUUIDOsobyAVL.insert(pcrTestsUUIDOsobyWrapperNode);
    }

    public PCR_Test vyhladajVysledokPCRTestu(AVL_Node<PCR_Test> pcrTest) {
        return this.pcrTestsAVL.search(pcrTest.getData()).getData();
    }

    public ArrayList<PCR_Test> vyhladajVsetkyTestyPreOsobu(AVL_Node<Osoba> osoba) {
        ArrayList<PCR_Test> result = new ArrayList<>();

        String uuid = osoba.getData().getUUID();
        PCR_Test dummyLow = new PCR_Test(new Date(Long.MIN_VALUE), uuid, 0, 0, 0, 0, false, 0.0, "");
        PCR_Test dummyHigh = new PCR_Test(new Date(Long.MAX_VALUE), uuid, 0, 0, 0, 0, false, 0.0, "");

        PCR_TestUUIDOsobyWrapper low = new PCR_TestUUIDOsobyWrapper(dummyLow);
        PCR_TestUUIDOsobyWrapper high = new PCR_TestUUIDOsobyWrapper(dummyHigh);

        ArrayList<BST_Node<PCR_TestUUIDOsobyWrapper>> nodes =
                this.pcrTestsUUIDOsobyAVL.rangeSearch(low, high);

        for (BST_Node<PCR_TestUUIDOsobyWrapper> node : nodes) {
            result.add(node.getData().getTest());
        }
        return result;
    }

    public ArrayList<PCR_Test> vyhladajPozitivneTestyPreDatumAOkres(int kodOkresu, Date dat_od, Date dat_do) {
        ArrayList<PCR_Test> result = new ArrayList<>();

        PCR_Test low = new PCR_Test(dat_od, "", 0, 0, kodOkresu, 0, true, 0, "");
        PCR_Test high = new PCR_Test(dat_do, "", 0, 0, kodOkresu, 0, true, 0, "");

        PCR_TestOkresDatumWrapper wLow = new PCR_TestOkresDatumWrapper(low);
        PCR_TestOkresDatumWrapper wHigh = new PCR_TestOkresDatumWrapper(high);

        ArrayList<BST_Node<PCR_TestOkresDatumWrapper>> nodes =
                this.pcrPozitivneTestsOkresDatumAVL.rangeSearch(wLow, wHigh);

        for (BST_Node<PCR_TestOkresDatumWrapper> node : nodes) {
            result.add(node.getData().getTest());
        }

        return result;
    }

    public ArrayList<PCR_Test> vyhladajTestyPreDatumAOkres(int kodOkresu, Date dat_od, Date dat_do) {
        ArrayList<PCR_Test> result = new ArrayList<>();

        PCR_Test low = new PCR_Test(dat_od, "", 0, 0, kodOkresu, 0, false, 0, "");
        PCR_Test high = new PCR_Test(dat_do, "", 0, 0, kodOkresu, 0, false, 0, "");

        PCR_TestOkresDatumWrapper wLow = new PCR_TestOkresDatumWrapper(low);
        PCR_TestOkresDatumWrapper wHigh = new PCR_TestOkresDatumWrapper(high);

        ArrayList<BST_Node<PCR_TestOkresDatumWrapper>> nodes =
                this.pcrTestOkresDatumAVL.rangeSearch(wLow, wHigh);

        for (BST_Node<PCR_TestOkresDatumWrapper> node : nodes) {
            result.add(node.getData().getTest());
        }

        return result;
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
