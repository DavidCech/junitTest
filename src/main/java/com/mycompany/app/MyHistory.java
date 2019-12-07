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

/**
 *
 * @author 2016-e-cech
 */
public class MyHistory {

    Path file;
    List<String> list;

    public MyHistory(String fileName) {
        file = Paths.get(fileName);
        list = new ArrayList<String>();
    }

    public void read() throws IOException {
        try {
            list = Files.readAllLines(file);

        } catch (IOException e) {
            throw e;
        }
        //Potreba pouze pokud MyHistory jiz dostane soubor s duplikaty
        ArrayList<String> noDuplicates = new ArrayList<String>();
        for (String s : list) {
            if (!noDuplicates.contains(s)) {
                noDuplicates.add(s);
            }
        }

        list = noDuplicates;
    }

    public void save() throws IOException {
        try {
            Files.write(file, list);
        } catch (IOException e) {
            throw e;
        }
    }

    public void addLine(String str) {
        if (!list.contains(str)) {
            list.add(str);
        }
    }

    public String toString() {
        String out = String.join("\n", list);
        return out;
    }
}