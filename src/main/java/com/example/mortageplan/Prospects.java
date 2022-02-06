package com.example.mortageplan;

import com.example.mortageplan.entity.InvalidInputException;
import com.example.mortageplan.entity.ProspectEntity;
import com.example.mortageplan.util.CSV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Prospects {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    List<ProspectEntity> prospectEntities = new ArrayList<>();

    public Prospects(List<ProspectEntity> prospectEntities) {
        this.prospectEntities = prospectEntities;
    }

    public Prospects(CSV csv) {
        int i=0;
        for (List<String> strings : csv.getStrings()) {
            try {
                ProspectEntity p = new ProspectEntity(strings);
                prospectEntities.add(p);
            } catch (InvalidInputException e) {
                if (i>0) {  // Skip first line header row error
                    logger.warn("Invalid CSV input: " + e.getMessage());
                }
            }
            i++;
        }
        this.prospectEntities = prospectEntities;
    }

    @Override
    public String toString() {
        int i = 0;
        String out;
        out = "****************************************************************************************************\n\n";
        for (ProspectEntity p : prospectEntities) {
            out += "Prospect " + ++i + ": " + p + "\n";
        }
        out += "\n****************************************************************************************************\n";
        return out;
    }
}
