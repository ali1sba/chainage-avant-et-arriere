package com.company;

import java.util.ArrayList;

public class regle {
    ArrayList<String> premisses = new ArrayList<>();
    ArrayList <String> actions = new ArrayList<>();
    boolean activte = true;



    @Override
    public String toString() {
        return "regle{" +
                "premisses=" + premisses.toString() +
                ", actions=" + actions.toString() +
                '}';
    }

}