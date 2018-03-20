package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti != null);
    }

    //kortin saldo alussa oikein
    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals("saldo: 0.10", kortti.toString());
    }

    //rahan lataaminen kasvattaa saldoa oikein
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(10);
        assertEquals("saldo: 0.20", kortti.toString());
    }

    //rahan ottaminen toimii
    @Test
    public void rahanOttaminenToimii() {
        assertTrue(kortti.otaRahaa(5));
    }

    //saldo vähenee oikein, jos rahaa on tarpeeksi
    @Test
    public void saldoVaheneeOikeinJosRahaaTarpeeksi() {
        kortti.otaRahaa(5);
        assertEquals("saldo: 0.05", kortti.toString());
    }

    //saldo ei muutu, jos rahaa ei ole tarpeeksi
    @Test
    public void saldoEiMuutuJosRahaaEiTarpeeksi() {
        kortti.otaRahaa(12);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    //metodi palauttaa true, jos rahat riittivät ja muuten false
    @Test
    public void palauttaaTrueJosRahatRiittavat() {
        assertTrue(kortti.otaRahaa(5));
    }
    
    @Test
    public void palauttaaFalseJosRahatEiRiita() {
        assertFalse(kortti.otaRahaa(13));
    }
    
    @Test
    public void saldonPalautusOnOikein() {
        assertEquals(10, kortti.saldo());
    }

}
