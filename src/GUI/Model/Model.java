package GUI.Model;

import DS.AVL.AVL;
import DS.AVL.AVL_Node;
import DS.BST.BST_Node;
import Data.Osoba.Osoba;
import Data.PCR_Test.*;

import java.util.*;

public class Model {
    private AVL<Osoba> osobyAVL;
    private AVL<PCR_Test> pcrTestsAVL;
    private AVL<PCR_TestDatumWrapper> pcrTestsDatumAVL;
    private AVL<PCR_TestDatumPracoviskoWrapper> pcrTestsDatumPracoviskoAVL;
    private AVL<PCR_TestDatumWrapper> pcrPozitivneTestsDatumAVL;
    private AVL<PCR_TestDatumWrapper> pcrNegativneTestsDatumAVL;
    private AVL<PCR_TestUUIDOsobyDatumWrapper> pcrTestUUIDOsobyDatumWrapperAVL;
    private AVL<PCR_TestOkresDatumWrapper> pcrPozitivneTestsOkresDatumAVL;
    private AVL<PCR_TestOkresDatumWrapper> pcrTestOkresDatumAVL;
    private AVL<PCR_TestKrajDatumWrapper> pcrPozitivneTestsKrajDatumAVL;
    private AVL<PCR_TestKrajDatumWrapper> pcrTestsKrajDatumAVL;
    private AVL<PCR_TestUUIDOsobyKodTestuWrapper> pcrTestUUIDOsobyKodTestuWrapperAVL;


    public Model() {
        this.osobyAVL = new AVL<>();
        this.pcrTestsAVL = new AVL<>();
        this.pcrTestsDatumAVL = new AVL<>();
        this.pcrTestsDatumPracoviskoAVL = new AVL<>();
        this.pcrPozitivneTestsDatumAVL = new AVL<>();
        this.pcrNegativneTestsDatumAVL = new AVL<>();
        this.pcrTestUUIDOsobyDatumWrapperAVL = new AVL<>();
        this.pcrPozitivneTestsOkresDatumAVL = new AVL<>();
        this.pcrTestOkresDatumAVL = new AVL<>();
        this.pcrPozitivneTestsKrajDatumAVL = new AVL<>();
        this.pcrTestsKrajDatumAVL = new AVL<>();
        this.pcrTestUUIDOsobyKodTestuWrapperAVL = new AVL<>();
    }
    //1
    public void vlozPCRTest(Date datumACasTestu, String UUIDOsoby, int kodPCR,
                            int UUIDPracoviska, int kodOkresu, int kodKraja,
                            boolean vysledokTestu, double hodnotaTestu, String poznamka) {

        PCR_Test test = new PCR_Test(datumACasTestu, UUIDOsoby, kodPCR,
                UUIDPracoviska, kodOkresu, kodKraja,
                vysledokTestu, hodnotaTestu, poznamka);

        this.pcrTestsAVL.insert(new AVL_Node<>(test, test));
        PCR_TestDatumWrapper testDatumWrapper = new PCR_TestDatumWrapper(test);
        PCR_TestOkresDatumWrapper okresDatumWrapper = new PCR_TestOkresDatumWrapper(test);
        PCR_TestKrajDatumWrapper krajDatumWrapper = new PCR_TestKrajDatumWrapper(test);
        PCR_TestDatumPracoviskoWrapper testDatumPracoviskoWrapper = new PCR_TestDatumPracoviskoWrapper(test);
        PCR_TestUUIDOsobyDatumWrapper testUUIDOsobyDatumWrapper = new PCR_TestUUIDOsobyDatumWrapper(test);
        PCR_TestUUIDOsobyKodTestuWrapper testUUIDOsobyKodTestuWrapper = new PCR_TestUUIDOsobyKodTestuWrapper(test);

        this.pcrTestsDatumAVL.insert(new AVL_Node<>(testDatumWrapper, testDatumWrapper));
        this.pcrTestOkresDatumAVL.insert(new AVL_Node<>(okresDatumWrapper, okresDatumWrapper));
        this.pcrTestsKrajDatumAVL.insert(new AVL_Node<>(krajDatumWrapper, krajDatumWrapper));
        this.pcrTestsDatumPracoviskoAVL.insert(new AVL_Node<>(testDatumPracoviskoWrapper, testDatumPracoviskoWrapper));

        if (test.isVysledokTestu()) {
            this.pcrPozitivneTestsDatumAVL.insert(new AVL_Node<>(testDatumWrapper, testDatumWrapper));
            this.pcrPozitivneTestsOkresDatumAVL.insert(new AVL_Node<>(okresDatumWrapper, okresDatumWrapper));
            this.pcrPozitivneTestsKrajDatumAVL.insert(new AVL_Node<>(krajDatumWrapper, krajDatumWrapper));
        } else {
            this.pcrNegativneTestsDatumAVL.insert(new AVL_Node<>(testDatumWrapper, testDatumWrapper));
        }
        this.pcrTestUUIDOsobyDatumWrapperAVL.insert(new AVL_Node<>(testUUIDOsobyDatumWrapper, testUUIDOsobyDatumWrapper));
        this.pcrTestUUIDOsobyKodTestuWrapperAVL.insert(new AVL_Node<>(testUUIDOsobyKodTestuWrapper, testUUIDOsobyKodTestuWrapper));
    }
    //2
    public PCR_Test vyhladajVysledokPCRTestu(int kodPCR, String UUIDOsoby) {
        PCR_TestUUIDOsobyKodTestuWrapper dummy = new PCR_TestUUIDOsobyKodTestuWrapper(new PCR_Test(new Date(), UUIDOsoby, kodPCR, 0, 0, 0, false, 0.0, ""));
        AVL_Node<PCR_TestUUIDOsobyKodTestuWrapper> node = new AVL_Node<>(dummy, dummy);
        return this.pcrTestUUIDOsobyKodTestuWrapperAVL.search(node.getKey()).getData().getTest();
    }
    //3
    public ArrayList<PCR_Test> vyhladajVsetkyTestyPreOsobu(String uuidPacienta) {
        ArrayList<PCR_Test> result = new ArrayList<>();
        PCR_Test dummyLow = new PCR_Test(new Date(Long.MIN_VALUE), uuidPacienta, 0, 0, 0, 0, false, 0.0, "");
        PCR_Test dummyHigh = new PCR_Test(new Date(Long.MAX_VALUE), uuidPacienta, 0, 0, 0, 0, false, 0.0, "");
        PCR_TestUUIDOsobyDatumWrapper low = new PCR_TestUUIDOsobyDatumWrapper(dummyLow);
        PCR_TestUUIDOsobyDatumWrapper high = new PCR_TestUUIDOsobyDatumWrapper(dummyHigh);
        ArrayList<BST_Node<PCR_TestUUIDOsobyDatumWrapper>> nodes = this.pcrTestUUIDOsobyDatumWrapperAVL.rangeSearch(low, high);
        for (BST_Node<PCR_TestUUIDOsobyDatumWrapper> node : nodes) {
            result.add(node.getData().getTest());
        }
        return result;
    }
    //4
    public ArrayList<PCR_Test> vyhladajPozitivneTestyPreDatumAOkres(int kodOkresu, Date dat_od, Date dat_do) {
        ArrayList<PCR_Test> result = new ArrayList<>();
        PCR_Test low = new PCR_Test(dat_od, "", 0, 0, kodOkresu, 0, true, 0, "");
        PCR_Test high = new PCR_Test(dat_do, "", 0, 0, kodOkresu, 0, true, 0, "");
        PCR_TestOkresDatumWrapper wLow = new PCR_TestOkresDatumWrapper(low);
        PCR_TestOkresDatumWrapper wHigh = new PCR_TestOkresDatumWrapper(high);
        ArrayList<BST_Node<PCR_TestOkresDatumWrapper>> nodes = this.pcrPozitivneTestsOkresDatumAVL.rangeSearch(wLow, wHigh);
        for (BST_Node<PCR_TestOkresDatumWrapper> node : nodes) {
            result.add(node.getData().getTest());
        }
        return result;
    }
    //5
    public ArrayList<PCR_Test> vyhladajTestyPreDatumAOkres(int kodOkresu, Date dat_od, Date dat_do) {
        ArrayList<PCR_Test> result = new ArrayList<>();
        PCR_Test low = new PCR_Test(dat_od, "", 0, 0, kodOkresu, 0, false, 0, "");
        PCR_Test high = new PCR_Test(dat_do, "", 0, 0, kodOkresu, 0, false, 0, "");
        PCR_TestOkresDatumWrapper wLow = new PCR_TestOkresDatumWrapper(low);
        PCR_TestOkresDatumWrapper wHigh = new PCR_TestOkresDatumWrapper(high);
        ArrayList<BST_Node<PCR_TestOkresDatumWrapper>> nodes = this.pcrTestOkresDatumAVL.rangeSearch(wLow, wHigh);
        for (BST_Node<PCR_TestOkresDatumWrapper> node : nodes) {
            result.add(node.getData().getTest());
        }
        return result;
    }
    //6
    public ArrayList<PCR_Test> vyhladajPozitivneTestyPreDatumAKraj(int kodKraja, Date dat_od, Date dat_do) {
        ArrayList<PCR_Test> result = new ArrayList<>();
        PCR_Test low = new PCR_Test(dat_od, "", 0, 0, 0, kodKraja, true, 0, "");
        PCR_Test high = new PCR_Test(dat_do, "", 0, 0, 0, kodKraja, true, 0, "");
        PCR_TestKrajDatumWrapper wLow = new PCR_TestKrajDatumWrapper(low);
        PCR_TestKrajDatumWrapper wHigh = new PCR_TestKrajDatumWrapper(high);
        ArrayList<BST_Node<PCR_TestKrajDatumWrapper>> nodes =
                this.pcrPozitivneTestsKrajDatumAVL.rangeSearch(wLow, wHigh);
        for (BST_Node<PCR_TestKrajDatumWrapper> node : nodes) {
            result.add(node.getData().getTest());
        }
        return result;
    }
    //7
    public ArrayList<PCR_Test> vyhladajTestyPreDatumAKraj(int kodKraja, Date dat_od, Date dat_do) {
        ArrayList<PCR_Test> result = new ArrayList<>();
        PCR_Test low = new PCR_Test(dat_od, "", 0, 0, 0, kodKraja, true, 0, "");
        PCR_Test high = new PCR_Test(dat_do, "", 0, 0, 0, kodKraja, true, 0, "");
        PCR_TestKrajDatumWrapper wLow = new PCR_TestKrajDatumWrapper(low);
        PCR_TestKrajDatumWrapper wHigh = new PCR_TestKrajDatumWrapper(high);
        ArrayList<BST_Node<PCR_TestKrajDatumWrapper>> nodes =
                this.pcrTestsKrajDatumAVL.rangeSearch(wLow, wHigh);
        for (BST_Node<PCR_TestKrajDatumWrapper> node : nodes) {
            result.add(node.getData().getTest());
        }
        return result;
    }
    //8
    public ArrayList<PCR_Test> vyhladajPozitivneTestyPreDatum(Date dat_od, Date dat_do) {
        ArrayList<PCR_Test> result = new ArrayList<>();
        PCR_Test low = new PCR_Test(dat_od, "", 0, 0, 0, 0, true, 0, "");
        PCR_Test high = new PCR_Test(dat_do, "", 0, 0, 0, 0, true, 0, "");
        PCR_TestDatumWrapper wLow = new PCR_TestDatumWrapper(low);
        PCR_TestDatumWrapper wHigh = new PCR_TestDatumWrapper(high);
        ArrayList<BST_Node<PCR_TestDatumWrapper>> nodes =
                this.pcrPozitivneTestsDatumAVL.rangeSearch(wLow, wHigh);
        for (BST_Node<PCR_TestDatumWrapper> node : nodes) {
            result.add(node.getData().getTest());
        }
        return result;
    }
    //9
    public ArrayList<PCR_Test> vyhladajTestyPreDatum(Date dat_od, Date dat_do) {
        ArrayList<PCR_Test> result = new ArrayList<>();
        PCR_Test low = new PCR_Test(dat_od, "", 0, 0, 0, 0, false, 0, "");
        PCR_Test high = new PCR_Test(dat_do, "", 0, 0, 0, 0, false, 0, "");
        PCR_TestDatumWrapper wLow = new PCR_TestDatumWrapper(low);
        PCR_TestDatumWrapper wHigh = new PCR_TestDatumWrapper(high);
        ArrayList<BST_Node<PCR_TestDatumWrapper>> nodes = this.pcrTestsDatumAVL.rangeSearch(wLow, wHigh);
        for (BST_Node<PCR_TestDatumWrapper> node : nodes) {
            result.add(node.getData().getTest());
        }
        return result;
    }
    //10
    public ArrayList<Osoba> vypisChorychOsobPreDatumAOkres(int kodOkresu, Date datum, int trvanieChoroby) {
        ArrayList<Osoba> result = new ArrayList<>();
        long trvanieMillis = (long) trvanieChoroby * 24 * 60 * 60 * 1000;
        Date datumDo = new Date(datum.getTime() + trvanieMillis);
        PCR_Test low = new PCR_Test(datum, "", 0, 0, kodOkresu, 0, true, 0, "");
        PCR_Test high = new PCR_Test(datumDo, "", 0, 0, kodOkresu, 0, true, 0, "");
        PCR_TestOkresDatumWrapper wLow = new PCR_TestOkresDatumWrapper(low);
        PCR_TestOkresDatumWrapper wHigh = new PCR_TestOkresDatumWrapper(high);
        ArrayList<BST_Node<PCR_TestOkresDatumWrapper>> nodes = this.pcrPozitivneTestsOkresDatumAVL.rangeSearch(wLow, wHigh);
        for (BST_Node<PCR_TestOkresDatumWrapper> node : nodes) {
            String uuid = node.getData().getTest().getUUIDOsoby();
            AVL_Node<Osoba> osobaNode = (AVL_Node<Osoba>) this.osobyAVL.search(new Osoba("", "", new Date(), uuid));
            if (osobaNode != null) {
                result.add(osobaNode.getData());
            }
        }
        return result;
    }
    //11
    public ArrayList<Osoba> vypisChorychOsobPreDatumAOkresUsporiadaneHodnotouTestu(int kodOkresu, Date datum, int trvanieChoroby) {
        ArrayList<Osoba> result = new ArrayList<>();
        long trvanieMillis = (long) trvanieChoroby * 24 * 60 * 60 * 1000;
        Date datumDo = new Date(datum.getTime() + trvanieMillis);
        PCR_Test low = new PCR_Test(datum, "", 0, 0, kodOkresu, 0, true, 0, "");
        PCR_Test high = new PCR_Test(datumDo, "", 0, 0, kodOkresu, 0, true, 0, "");
        PCR_TestOkresDatumWrapper wLow = new PCR_TestOkresDatumWrapper(low);
        PCR_TestOkresDatumWrapper wHigh = new PCR_TestOkresDatumWrapper(high);
        ArrayList<BST_Node<PCR_TestOkresDatumWrapper>> nodes = this.pcrPozitivneTestsOkresDatumAVL.rangeSearch(wLow, wHigh);
        nodes.sort((n1, n2) -> {
            double hodnota1 = n1.getData().getTest().getHodnotaTestu();
            double hodnota2 = n2.getData().getTest().getHodnotaTestu();
            return Double.compare(hodnota2, hodnota1); // Zoradenie zostupne
        });
        for (BST_Node<PCR_TestOkresDatumWrapper> node : nodes) {
            String uuid = node.getData().getTest().getUUIDOsoby();
            AVL_Node<Osoba> osobaNode = (AVL_Node<Osoba>) this.osobyAVL.search(new Osoba("", "", new Date(), uuid));
            if (osobaNode != null) {
                result.add(osobaNode.getData());
            }
        }
        return result;
    }
    //12
    public ArrayList<Osoba> vypisChorychOsobPreDatumAKraj(int kodKraja, Date datum, int trvanieChoroby) {
        ArrayList<Osoba> result = new ArrayList<>();
        long trvanieMillis = (long) trvanieChoroby * 24 * 60 * 60 * 1000;
        Date datumDo = new Date(datum.getTime() + trvanieMillis);
        PCR_Test low = new PCR_Test(datum, "", 0, 0, 0, kodKraja, true, 0, "");
        PCR_Test high = new PCR_Test(datumDo, "", 0, 0, 0, kodKraja, true, 0, "");
        PCR_TestKrajDatumWrapper wLow = new PCR_TestKrajDatumWrapper(low);
        PCR_TestKrajDatumWrapper wHigh = new PCR_TestKrajDatumWrapper(high);
        ArrayList<BST_Node<PCR_TestKrajDatumWrapper>> nodes = this.pcrPozitivneTestsKrajDatumAVL.rangeSearch(wLow, wHigh);
        for (BST_Node<PCR_TestKrajDatumWrapper> node : nodes) {
            String uuid = node.getData().getTest().getUUIDOsoby();
            AVL_Node<Osoba> osobaNode = (AVL_Node<Osoba>) this.osobyAVL.search(new Osoba("", "", new Date(), uuid));
            if (osobaNode != null) {
                result.add(osobaNode.getData());
            }
        }
        return result;
    }
    //13
    public ArrayList<Osoba> vypisChorychOsobPreDatum(Date datum, int trvanieChoroby) {
        ArrayList<Osoba> result = new ArrayList<>();
        long trvanieMillis = (long) trvanieChoroby * 24 * 60 * 60 * 1000;
        Date datumDo = new Date(datum.getTime() + trvanieMillis);
        PCR_Test low = new PCR_Test(datum, "", 0, 0, 0, 0, true, 0, "");
        PCR_Test high = new PCR_Test(datumDo, "", 0, 0, 0, 0, true, 0, "");
        PCR_TestDatumWrapper wLow = new PCR_TestDatumWrapper(low);
        PCR_TestDatumWrapper wHigh = new PCR_TestDatumWrapper(high);
        ArrayList<BST_Node<PCR_TestDatumWrapper>> nodes = this.pcrPozitivneTestsDatumAVL.rangeSearch(wLow, wHigh);
        for (BST_Node<PCR_TestDatumWrapper> node : nodes) {
            String uuid = node.getData().getTest().getUUIDOsoby();
            AVL_Node<Osoba> osobaNode = (AVL_Node<Osoba>) this.osobyAVL.search(new Osoba("", "", new Date(), uuid));
            if (osobaNode != null) {
                result.add(osobaNode.getData());
            }
        }
        return result;
    }
    //14
    public ArrayList<Osoba> vypisOsobyPreDatumKazdyOkresSNajvyssouHodnotouTestu(Date datum, int trvanieChoroby) {
        ArrayList<Osoba> result = new ArrayList<>();
        long trvanieMillis = (long) trvanieChoroby * 24 * 60 * 60 * 1000;
        Date datumDo = new Date(datum.getTime() + trvanieMillis);
        PCR_Test low = new PCR_Test(datum, "", 0, 0, 0, 0, true, 0, "");
        PCR_Test high = new PCR_Test(datumDo, "", 0, 0, Integer.MAX_VALUE, 0, true, 0, "");
        PCR_TestOkresDatumWrapper wLow = new PCR_TestOkresDatumWrapper(low);
        PCR_TestOkresDatumWrapper wHigh = new PCR_TestOkresDatumWrapper(high);
        ArrayList<BST_Node<PCR_TestOkresDatumWrapper>> nodes = this.pcrPozitivneTestsOkresDatumAVL.rangeSearch(wLow, wHigh);
        nodes.sort((n1, n2) -> {
            double hodnota1 = n1.getData().getTest().getHodnotaTestu();
            double hodnota2 = n2.getData().getTest().getHodnotaTestu();
            return Double.compare(hodnota2, hodnota1); // Zoradenie zostupne
        });
        ArrayList<Integer> processedOkresy = new ArrayList<>();
        for (BST_Node<PCR_TestOkresDatumWrapper> node : nodes) {
            int kodOkresu = node.getData().getTest().getKodOkresu();
            if (!processedOkresy.contains(kodOkresu)) {
                String uuid = node.getData().getTest().getUUIDOsoby();
                AVL_Node<Osoba> osobaNode = (AVL_Node<Osoba>) this.osobyAVL.search(new Osoba("", "", new Date(), uuid));
                if (osobaNode != null) {
                    result.add(osobaNode.getData());
                    processedOkresy.add(kodOkresu);
                }
            }
        }
        return result;
    }
    //15
    public ArrayList<HelperPocetChorych> vypisOkresovUsporiadanychPoctomChorychPreDatum(Date datum, int trvanieChoroby) {
        ArrayList<HelperPocetChorych> result = new ArrayList<>();
        long trvanieMillis = (long) trvanieChoroby * 24 * 60 * 60 * 1000;
        Date datumDo = new Date(datum.getTime() + trvanieMillis);
        PCR_Test low = new PCR_Test(datum, "", 0, 0, 0, 0, true, 0, "");
        PCR_Test high = new PCR_Test(datumDo, "", 0, 0, Integer.MAX_VALUE, 0, true, 0, "");
        PCR_TestOkresDatumWrapper wLow = new PCR_TestOkresDatumWrapper(low);
        PCR_TestOkresDatumWrapper wHigh = new PCR_TestOkresDatumWrapper(high);
        ArrayList<BST_Node<PCR_TestOkresDatumWrapper>> nodes = this.pcrPozitivneTestsOkresDatumAVL.rangeSearch(wLow, wHigh);
        nodes.sort((n1,n2)-> {
            int kodOkresu1 = n1.getData().getTest().getKodOkresu();
            int kodOkresu2 = n2.getData().getTest().getKodOkresu();
            return Integer.compare(kodOkresu1, kodOkresu2);
        });
        int i = 0;
        while (i < nodes.size()) {
            int kodOkresu = nodes.get(i).getData().getTest().getKodOkresu();
            HelperPocetChorych helper = new HelperPocetChorych(kodOkresu, 0);

            while (i < nodes.size() && nodes.get(i).getData().getTest().getKodOkresu() == kodOkresu) {
                helper.incrementPocetChorych();
                i++;
            }

            result.add(helper);
        }
        result.sort((h1, h2) -> Integer.compare(h2.getPocetChorych(), h1.getPocetChorych()));
        return result;
    }
    //16
    public ArrayList<HelperPocetChorych> vypisKrajovUsporiadanychPoctomChorychPreDatum(Date datum, int trvanieChoroby) {
        ArrayList<HelperPocetChorych> result = new ArrayList<>();
        long trvanieMillis = (long) trvanieChoroby * 24 * 60 * 60 * 1000;
        Date datumDo = new Date(datum.getTime() + trvanieMillis);
        PCR_Test low = new PCR_Test(datum, "", 0, 0, 0, 0, true, 0, "");
        PCR_Test high = new PCR_Test(datumDo, "", 0, 0, 0, Integer.MAX_VALUE, true, 0, "");
        PCR_TestKrajDatumWrapper wLow = new PCR_TestKrajDatumWrapper(low);
        PCR_TestKrajDatumWrapper wHigh = new PCR_TestKrajDatumWrapper(high);
        ArrayList<BST_Node<PCR_TestKrajDatumWrapper>> nodes = this.pcrPozitivneTestsKrajDatumAVL.rangeSearch(wLow, wHigh);
        nodes.sort((n1,n2)-> {
            int kodKraja1 = n1.getData().getTest().getKodKraja();
            int kodKraja2 = n2.getData().getTest().getKodKraja();
            return Integer.compare(kodKraja1, kodKraja2);
        });
        int i = 0;
        while (i < nodes.size()) {
            int kodKraja = nodes.get(i).getData().getTest().getKodKraja();
            HelperPocetChorych helper = new HelperPocetChorych(kodKraja, 0);

            while (i < nodes.size() && nodes.get(i).getData().getTest().getKodKraja() == kodKraja) {
                helper.incrementPocetChorych();
                i++;
            }

            result.add(helper);
        }
        result.sort((h1, h2) -> Integer.compare(h2.getPocetChorych(), h1.getPocetChorych()));
        return result;
    }
    //17
    public ArrayList<PCR_Test> vypisTestovPreDatumAPracovisko(int kodPracoviska, Date dat_od, Date dat_do) {
        ArrayList<PCR_Test> result = new ArrayList<>();
        PCR_Test low = new PCR_Test(dat_od, "", kodPracoviska, 0, 0, 0, false, 0, "");
        PCR_Test high = new PCR_Test(dat_do, "", kodPracoviska, 0, 0, 0, false, 0, "");
        PCR_TestDatumPracoviskoWrapper wLow = new PCR_TestDatumPracoviskoWrapper(low);
        PCR_TestDatumPracoviskoWrapper wHigh = new PCR_TestDatumPracoviskoWrapper(high);
        ArrayList<BST_Node<PCR_TestDatumPracoviskoWrapper>> nodes = this.pcrTestsDatumPracoviskoAVL.rangeSearch(wLow, wHigh);
        for (BST_Node<PCR_TestDatumPracoviskoWrapper> node : nodes) {
            result.add(node.getData().getTest());
        }
        return result;
    }
    //18
    public PCR_Test vyhladaniePCRTestu(int kodPCR) {
        PCR_Test dummy = new PCR_Test(new Date(), "", kodPCR, 0, 0, 0, false, 0.0, "");
        AVL_Node<PCR_Test> pcrTest = new AVL_Node<>(dummy, dummy);
        return this.pcrTestsAVL.search(pcrTest.getKey()).getData();
    }
    //19
    public void vlozOsobu(String meno, String priezvisko, Date datumNarodenia, String UUID) {
        Osoba osoba = new Osoba(meno, priezvisko, datumNarodenia, UUID);
        AVL_Node<Osoba> node = new AVL_Node<>(osoba, osoba);
        this.osobyAVL.insert(node);
    }
    //20
    public void vymazPCRTest(int kodPCR) {
        PCR_Test dummy = new PCR_Test(new Date(), "", kodPCR, 0, 0, 0, false, 0.0, "");
        AVL_Node<PCR_Test> node = new AVL_Node<>(dummy, dummy);
        AVL_Node<PCR_Test> deletedNode = (AVL_Node<PCR_Test>) this.pcrTestsAVL.search(node.getKey());
        if (deletedNode != null) {
            vymazTestPreOsobu(deletedNode);
        }
    }
    private void vymazTestPreOsobu(AVL_Node<PCR_Test> pcrTest) {
        this.pcrTestsAVL.delete(pcrTest);
        PCR_TestDatumWrapper testDatumWrapper = new PCR_TestDatumWrapper(pcrTest.getData());
        this.pcrTestsDatumAVL.delete(new AVL_Node<>(testDatumWrapper, testDatumWrapper));
        PCR_TestOkresDatumWrapper okresDatumWrapper = new PCR_TestOkresDatumWrapper(pcrTest.getData());
        this.pcrTestOkresDatumAVL.delete(new AVL_Node<>(okresDatumWrapper, okresDatumWrapper));
        PCR_TestKrajDatumWrapper krajDatumWrapper = new PCR_TestKrajDatumWrapper(pcrTest.getData());
        this.pcrTestsKrajDatumAVL.delete(new AVL_Node<>(krajDatumWrapper, krajDatumWrapper));
        PCR_TestDatumPracoviskoWrapper testDatumPracoviskoWrapper = new PCR_TestDatumPracoviskoWrapper(pcrTest.getData());
        this.pcrTestsDatumPracoviskoAVL.delete(new AVL_Node<>(testDatumPracoviskoWrapper, testDatumPracoviskoWrapper));
        if (pcrTest.getData().isVysledokTestu()) {
            this.pcrPozitivneTestsDatumAVL.delete(new AVL_Node<>(testDatumWrapper, testDatumWrapper));
            this.pcrPozitivneTestsOkresDatumAVL.delete(new AVL_Node<>(okresDatumWrapper, okresDatumWrapper));
            this.pcrPozitivneTestsKrajDatumAVL.delete(new AVL_Node<>(krajDatumWrapper, krajDatumWrapper));
        } else {
            this.pcrNegativneTestsDatumAVL.delete(new AVL_Node<>(testDatumWrapper, testDatumWrapper));
        }
        PCR_TestUUIDOsobyDatumWrapper testUUIDOsobyWrapper = new PCR_TestUUIDOsobyDatumWrapper(pcrTest.getData());
        this.pcrTestUUIDOsobyDatumWrapperAVL.delete(new AVL_Node<>(testUUIDOsobyWrapper, testUUIDOsobyWrapper));
        PCR_TestUUIDOsobyKodTestuWrapper testUUIDOsobyKodTestuWrapper = new PCR_TestUUIDOsobyKodTestuWrapper(pcrTest.getData());
        this.pcrTestUUIDOsobyKodTestuWrapperAVL.delete(new AVL_Node<>(testUUIDOsobyKodTestuWrapper, testUUIDOsobyKodTestuWrapper));
    }
    //21
    public void vymazOsobu(String UUID) {
        Osoba osoba = new Osoba("", "", new Date(), UUID);
        AVL_Node<Osoba> nodeOsoba = new AVL_Node<>(osoba, osoba);
        PCR_Test dummyLow = new PCR_Test(new Date(), nodeOsoba.getData().getUUID(), 0, 0, 0, 0, false, 0, "");
        PCR_Test dummyHigh = new PCR_Test(new Date(), nodeOsoba.getData().getUUID(), 0, 0, 0, 0, false, 0, "");
        PCR_TestUUIDOsobyDatumWrapper wLow = new PCR_TestUUIDOsobyDatumWrapper(dummyLow);
        PCR_TestUUIDOsobyDatumWrapper wHigh = new PCR_TestUUIDOsobyDatumWrapper(dummyHigh);

        ArrayList<BST_Node<PCR_TestUUIDOsobyDatumWrapper>> testyOsoby = this.pcrTestUUIDOsobyDatumWrapperAVL.rangeSearch(wLow, wHigh);

        for (BST_Node<PCR_TestUUIDOsobyDatumWrapper> node : testyOsoby) {
            AVL_Node<PCR_Test> pcrTestNode = (AVL_Node<PCR_Test>) this.pcrTestsAVL.search(node.getData().getTest());
            if (pcrTestNode != null) {
                vymazTestPreOsobu(pcrTestNode);
            }
        }

        this.osobyAVL.delete(this.osobyAVL.search(nodeOsoba.getData()));
    }

    //Ulozenie do CSV
    public void ulozDoCSV(String cestaSuboru) {
        try (java.io.FileWriter writer = new java.io.FileWriter(cestaSuboru + "_osoby.csv")) {
            ArrayList<BST_Node<Osoba>> osobyNodes = this.osobyAVL.levelOrder();
            for (BST_Node<Osoba> node : osobyNodes) {
                writer.write(node.getData().toString() + "\n");
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        try (java.io.FileWriter writer = new java.io.FileWriter(cestaSuboru + "_testy.csv")) {
            ArrayList<BST_Node<PCR_Test>> testNodes = this.pcrTestsAVL.levelOrder();
            for (BST_Node<PCR_Test> node : testNodes) {
                writer.write(node.getData().toString() + "\n");
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    //Nacitanie z CSV
    public void nacitajZCSV(String cestaSuboru) {
        this.osobyAVL = new AVL<>();
        this.pcrTestsAVL = new AVL<>();
        this.pcrTestsDatumAVL = new AVL<>();
        this.pcrTestsDatumPracoviskoAVL = new AVL<>();
        this.pcrPozitivneTestsDatumAVL = new AVL<>();
        this.pcrNegativneTestsDatumAVL = new AVL<>();
        this.pcrTestUUIDOsobyDatumWrapperAVL = new AVL<>();
        this.pcrPozitivneTestsOkresDatumAVL = new AVL<>();
        this.pcrTestOkresDatumAVL = new AVL<>();
        this.pcrPozitivneTestsKrajDatumAVL = new AVL<>();
        this.pcrTestsKrajDatumAVL = new AVL<>();
        this.pcrTestUUIDOsobyKodTestuWrapperAVL = new AVL<>();
        try (java.io.BufferedReader reader =
                     new java.io.BufferedReader(new java.io.FileReader(cestaSuboru + "_osoby.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                try {
                    String meno = line.split(",")[0];
                    String priezvisko = line.split(",")[1];
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    Date datumNarodenia = sdf.parse(line.split(",")[2]);
                    String UUID = line.split(",")[3];
                    this.vlozOsobu(meno, priezvisko, datumNarodenia, UUID);
                } catch (Exception e) {
                    System.err.println("Chyba pri načítaní osoby: " + line);
                    e.printStackTrace();
                }
            }
        } catch (java.io.IOException e) {
            System.err.println("Nepodarilo sa načítať osoby CSV: " + e.getMessage());
        }

        try (java.io.BufferedReader reader =
                     new java.io.BufferedReader(new java.io.FileReader(cestaSuboru + "_testy.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                try {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    Date datumVykonaniaTestu = sdf.parse(line.split(",")[0]);
                    String UUIDOsoby = line.split(",")[1];
                    int kodPCR = Integer.parseInt(line.split(",")[2]);
                    int kodPracoviska = Integer.parseInt(line.split(",")[3]);
                    int kodOkresu = Integer.parseInt(line.split(",")[4]);
                    int kodKraja = Integer.parseInt(line.split(",")[5]);
                    boolean vysledokTestu = Boolean.parseBoolean(line.split(",")[6]);
                    double hodnotaTestu = Double.parseDouble(line.split(",")[7]);
                    String poznamka = line.split(",")[8];
                    this.vlozPCRTest(datumVykonaniaTestu, UUIDOsoby, kodPCR, kodPracoviska, kodOkresu, kodKraja, vysledokTestu, hodnotaTestu, poznamka);
                } catch (Exception e) {
                    System.err.println("Chyba pri načítaní testu: " + line);
                    e.printStackTrace();
                }
            }
        } catch (java.io.IOException e) {
            System.err.println("Nepodarilo sa načítať testy CSV: " + e.getMessage());
        }
    }

    public void generujData(int n) {
        Random rnd = new Random();
        Set<Integer> pouziteKombinacieTestov = new HashSet<>();

        String[] mena = {"Peter", "Ján", "Marek", "Tomáš", "Lucia", "Zuzana", "Katarína", "Mária"};
        String[] priezviska = {"Novák", "Horváth", "Kováč", "Tóth", "Varga", "Baláž", "Molnár", "Krištof"};

        for (int i = 0; i < n; i++) {
            String meno = mena[rnd.nextInt(mena.length)];
            String priezvisko = priezviska[rnd.nextInt(priezviska.length)];
            Date datumNarodenia = randomDate(1950, 2010);
            String uuidOsoby = UUID.randomUUID().toString();
            this.vlozOsobu(meno, priezvisko, datumNarodenia, uuidOsoby);
            int pocetTestov = 1 + rnd.nextInt(5);
            for (int j = 0; j < pocetTestov; j++) {
                Date datum = randomDate(2020, 2025);
                int kodPCR;
                do {
                    kodPCR = 100000 + rnd.nextInt(900000);
                } while (pouziteKombinacieTestov.contains(kodPCR));
                pouziteKombinacieTestov.add(kodPCR);

                int uuidPracoviska = 100 + rnd.nextInt(50);
                int kodOkresu = 1 + rnd.nextInt(79);
                int kodKraja = 1 + rnd.nextInt(8);
                boolean vysledok = rnd.nextBoolean();
                double hodnota = vysledok ? 15 + rnd.nextDouble() * 10 : rnd.nextDouble() * 15;
                String poznamka = vysledok ? "Pozitívny" : "Negatívny";
                this.vlozPCRTest(datum, uuidOsoby, kodPCR, uuidPracoviska, kodOkresu, kodKraja, vysledok, hodnota, poznamka);
            }
        }
    }

    private Date randomDate(int yearFrom, int yearTo) {
        Calendar cal = Calendar.getInstance();
        int year = yearFrom + new Random().nextInt(yearTo - yearFrom + 1);
        int dayOfYear = 1 + new Random().nextInt(cal.getActualMaximum(Calendar.DAY_OF_YEAR));
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_YEAR, dayOfYear);
        cal.set(Calendar.HOUR_OF_DAY, new Random().nextInt(24));
        cal.set(Calendar.MINUTE, new Random().nextInt(60));
        cal.set(Calendar.SECOND, new Random().nextInt(60));
        return cal.getTime();
    }
}
