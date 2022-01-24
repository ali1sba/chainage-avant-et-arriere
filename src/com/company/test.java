package com.company;

import java.util.ArrayList;

public class test {
    public String verify (String x , ArrayList basedesfait , ArrayList<regle> regles ){

        if (basedesfait.contains(x)) return "existe dans la base des fait";
        else {
            ArrayList<regle> pileDesRegles = new ArrayList<>();
            StringBuilder result = new StringBuilder();
            boolean doneP ;
            for (regle regle : regles) if (regle.actions.contains(x)) pileDesRegles.add(regle);
            if (pileDesRegles.isEmpty())return "existe pas";
            for (regle R:pileDesRegles){
                doneP = true;
                for (String P :R.premisses) {
                    String tmp = verify(P,basedesfait,regles);
                    doneP = doneP && !tmp.contains("existe pas");
                    if (!tmp.equals("existe pas") && !tmp.equals("existe dans la base des fait")) result.append(tmp+ " ");

                }
                if (doneP) result.insert(0," => "+(regles.indexOf(R) + 1));else result.append("existe pas");

            }
            if (result.toString().contains("existe pas"))return "existe pas";else return result.toString();
        }
    }
}