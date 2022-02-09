package com.example.mortageplan.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class InputStreamFromFile {
    public InputStream InputStreamFromFile(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }
}
