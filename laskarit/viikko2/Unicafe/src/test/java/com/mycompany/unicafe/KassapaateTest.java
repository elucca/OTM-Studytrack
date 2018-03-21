package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {

    Kassapaate paate;

    @Before
    public void setUp() {
        this.paate = new Kassapaate();
    }

    //luodun kassapäätteen rahamäärä ja myytyjen lounaiden määrä on oikea (rahaa 1000, lounaita myyty 0)
    @Test
    public void rahanMaaraAlussaOikein() {
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void myytyjenLounaidenMaaraAlussaOikein() {
        assertEquals(0, paate.edullisiaLounaitaMyyty());
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    //käteisosto toimii sekä edullisten että maukkaiden lounaiden osalta
    //jos maksu riittävä: kassassa oleva rahamäärä kasvaa lounaan hinnalla ja vaihtorahan suuruus on oikea
    @Test
    public void edullinenOstoRahamaaraKasvaaOikeinKunMaksuRiittava() {
        paate.syoEdullisesti(240);
        assertEquals(100000 + 240, paate.kassassaRahaa());
    }

    @Test
    public void edullinenOstoOikeaVaihtorahaKunMaksuRiittava() {
        assertEquals(60, paate.syoEdullisesti(300));
    }

    @Test
    public void maukasOstoRahamaaraKasvaaOikeinKunMaksuRiittava() {
        paate.syoMaukkaasti(400);
        assertEquals(100000 + 400, paate.kassassaRahaa());
    }

    @Test
    public void maukasOstoOikeaVaihtorahaKunMaksuRiittava() {
        assertEquals(60, paate.syoMaukkaasti(460));
    }

    //jos maksu on riittävä: myytyjen lounaiden määrä kasvaa
    @Test
    public void edullinenOstoMyytyjenMaaraKasvaaKunMaksuRiittava() {
        paate.syoEdullisesti(240);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void maukasOstoMyytyjenMaaraKasvaaKunMaksuRiittava() {
        paate.syoMaukkaasti(400);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }

    //jos maksu ei ole riittävä: kassassa oleva rahamäärä ei muutu, kaikki rahat palautetaan vaihtorahana ja myytyjen lounaiden määrässä ei muutosta
    @Test
    public void edullinenOstoRahamaaraEiMuutuKunMaksuEiRiittava() {
        paate.syoEdullisesti(10);
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void maukasOstoRahamaaraEiMuutuKunMaksuEiRiittava() {
        paate.syoMaukkaasti(10);
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void edullinenOstoOikeaVaihtorahaKunMaksuEiRiittava() {
        assertEquals(200, paate.syoEdullisesti(200));
    }

    @Test
    public void maukasOstoOikeaVaihtorahaKunMaksuEiRiittava() {
        assertEquals(200, paate.syoMaukkaasti(200));
    }

    @Test
    public void edullistenLounaidenMaaraEiKasvaKunMaksuEiRiittava() {
        paate.syoEdullisesti(10);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void maukkaidenLounaidenMaaraEiKasvaKunMaksuEiRiittava() {
        paate.syoMaukkaasti(10);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    //korttiosto toimii sekä edullisten että maukkaiden lounaiden osalta
    //jos kortilla on tarpeeksi rahaa, veloitetaan summa kortilta ja palautetaan true
    @Test
    public void onnistunutEdullinenOstoKortillaVeloittaaSummanJaPalauttaaTrue() {
        Maksukortti kortti = new Maksukortti(240);
        assertTrue(paate.syoEdullisesti(kortti));
        assertEquals(0, kortti.saldo());
    }

    @Test
    public void onnistunutMaukasOstoKortillaVeloittaaSummanJaPalauttaaTrue() {
        Maksukortti kortti = new Maksukortti(400);
        assertTrue(paate.syoMaukkaasti(kortti));
        assertEquals(0, kortti.saldo());
    }

    //jos kortilla on tarpeeksi rahaa, myytyjen lounaiden määrä kasvaa
    @Test
    public void onnistunutEdullisenKorttiostoKasvattaaMyytyjenMaaraa() {
        Maksukortti kortti = new Maksukortti(240);
        paate.syoEdullisesti(kortti);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void onnistunutMaukkaanKorttiostoKasvattaaMyytyjenMaaraa() {
        Maksukortti kortti = new Maksukortti(400);
        paate.syoMaukkaasti(kortti);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }

    //jos kortilla ei ole tarpeeksi rahaa, kortin rahamäärä ei muutu, myytyjen lounaiden määrä muuttumaton ja palautetaan false
    @Test
    public void epaonnistunutEdullisenKorttiOstoKortinRahamaaraEiMuutu() {
        Maksukortti kortti = new Maksukortti(20);
        paate.syoEdullisesti(kortti);
        assertEquals(20, kortti.saldo());
    }

    @Test
    public void epaonnistunutMaukkaanKorttiOstoKortinRahamaaraEiMuutu() {
        Maksukortti kortti = new Maksukortti(20);
        paate.syoMaukkaasti(kortti);
        assertEquals(20, kortti.saldo());
    }

    @Test
    public void epaonnistunutEdullisenKorttiostoMyytyjenMaaraEiMuutuJaPalautetaanFalse() {
        Maksukortti kortti = new Maksukortti(20);
        assertFalse(paate.syoEdullisesti(kortti));
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }

    @Test
    public void epaonnistunutMaukkaanKorttiostoMyytyjenMaaraEiMuutuJaPalautetaanFalse() {
        Maksukortti kortti = new Maksukortti(20);
        assertFalse(paate.syoMaukkaasti(kortti));
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }

    //kassassa oleva rahamäärä ei muutu kortilla ostettaessa
    @Test
    public void edullisenKorttiostoKassanRahamaaraEiMuutu() {
        Maksukortti kortti = new Maksukortti(400);
        paate.syoEdullisesti(kortti);
        assertEquals(100000, paate.kassassaRahaa());
    }

    @Test
    public void maukkaanKorttiostoKassanRahamaaraEiMuutu() {
        Maksukortti kortti = new Maksukortti(400);
        paate.syoMaukkaasti(kortti);
        assertEquals(100000, paate.kassassaRahaa());
    }

    //kortille rahaa ladattaessa kortin saldo muuttuu ja kassassa oleva rahamäärä kasvaa ladatulla summalla
    @Test
    public void kortinLatausKortinSaldoMuuttuu() {
        Maksukortti kortti = new Maksukortti(0);
        paate.lataaRahaaKortille(kortti, 1000);
        assertEquals(1000, kortti.saldo());
    }

    @Test
    public void kortinLatausKassanRahamaaraMuuttuu() {
        Maksukortti kortti = new Maksukortti(0);
        paate.lataaRahaaKortille(kortti, 1000);
        assertEquals(100000 + 1000, paate.kassassaRahaa());
    }

    @Test
    public void kortinSaldoEiMuutuJosLadataanNegSumma() {
        Maksukortti kortti = new Maksukortti(0);
        paate.lataaRahaaKortille(kortti, -10);
        assertEquals(0, kortti.saldo());
    }

}
