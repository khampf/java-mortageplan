package com.example.mortageplan.prospects;

import com.example.mortageplan.InvalidInputException;
import com.example.mortageplan.libs.CSV;
import com.example.mortageplan.prospects.Prospect;

import java.util.ArrayList;
import java.util.List;

public class Prospects {
    List<Prospect> prospects  = new ArrayList<>();

    public Prospects(List<Prospect> prospects) {
        this.prospects = prospects;
    }

    public Prospects(CSV csv) {
        int i=0;
        for (List<String> strings : csv.getStrings()) {
            try {
                Prospect p = new Prospect(strings);
                prospects.add(p);
            } catch (InvalidInputException e) {
                if (i>0) {  // Omit header row error
                    System.err.println("WARNING: Invalid CSV input: " + e);
                }
            }
            i++;
        }
        this.prospects = prospects;
    }

    @Override
    public String toString() {
        int i = 0;
        String out;
        out = "****************************************************************************************************\n\n";
        for (Prospect p : prospects) {
            out += "Prospect " + ++i + ": " + p + "\n";
        }
        out += "\n****************************************************************************************************\n";
        return out;
    }
}
