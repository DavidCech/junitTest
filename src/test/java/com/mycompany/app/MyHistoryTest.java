/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author 2016-e-cech
 */
public class MyHistoryTest {
    
    private MyHistory h;
    private Path p;
    
    public MyHistoryTest() {

    }
    
    @Before
    public void setup() throws Exception{
        p = Files.createTempFile(null, null);
        h = new MyHistory(p.toString());
    }
    
    @After
    public void cleanup() throws Exception{
        Files.delete(p);
    }

    //Test spravneho pridavani
    @Test
    public void testAdd1() throws Exception {
        h.addLine("Ahoj");
        h.addLine("Testik");
        h.save();

        assertEquals("Ahoj\nTestik", h.toString());
    }

    //Test pridani duplicity
    @Test
    public void testAdd2() throws Exception {
        h.addLine("Testik");
        h.addLine("Testik");
        h.addLine("Testik");

        assertEquals("Testik", h.toString());
    }

    //Test cteni a nevypisovani duplicit
    @Test
    public void testRead1() throws Exception {
        createDuplicates();
        h.read();

        assertEquals("Ahoj", h.toString());
    }

    //Test chyby pri cetni
    @Test(expected = IOException.class)
    public void testRead2() throws Exception {
        //Soubor neexistuje
        h = new MyHistory("");
        h.read();
    }

    //Test ulozeni
    @Test
    public void testSave1() throws Exception {
        h.read();
        h.addLine("HURA");
        h.save();

        List<String> content = Files.readAllLines(p);
        String all = String.join("\n", content);

        assertEquals(h.toString(), all);
    }

    //Test ulozeni souboru bez duplicit
    @Test
    public void testSave2() throws Exception {
        //createDuplicates vytvori soubor s "Ahoj\nAhoj\nAhoj"
        createDuplicates();
        h.read();
        h.save();

        List<String> content = Files.readAllLines(Paths.get(p.toString()));
        String noDuplicates = String.join("\n", content);

        assertEquals(h.toString(), noDuplicates);
    }

    @Test
    public void testSave3() throws Exception {
        h.save();
 
    }

    @Test
    public void testSave4() throws Exception {
        h.addLine("Ahoj");
        h.save();
    }

    @Test
    public void testSave5() throws Exception {
        h.addLine("Ahoj");
        h.addLine("Tady");
        h.addLine("David");
        h.save();
    }
    
    public void createDuplicates() throws Exception{
        ArrayList<String> testdata = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            testdata.add("Ahoj");
        }
        Files.write(p, testdata);
    }
}