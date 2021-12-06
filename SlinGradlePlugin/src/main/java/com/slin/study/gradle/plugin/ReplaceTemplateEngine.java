package com.slin.study.gradle.plugin;

import org.apache.http.util.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * author: slin
 * <p>
 * date: 2021/12/6
 * <p>
 * description:
 */
public class ReplaceTemplateEngine implements TemplateEngine {

    private Map<String, String> variablesMap;

    public ReplaceTemplateEngine(Map<String, String> variablesMap){
        this.variablesMap = variablesMap;
    }

    @Override
    public void process(File input, File output) {
        try {
            processFile(input, output);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void processFile(File inputFile, File outputFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        if(!outputFile.exists()){
            outputFile.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        reader.lines().forEach(line -> {
            try {
                writer.write(replace(line) + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        reader.close();
        writer.flush();
        writer.close();
    }

    private String replace(String line){
        if(TextUtils.isEmpty(line)){
            return "";
        }
        Map<String, String> map = variablesMap;
        if(map.isEmpty()){
            return line;
        }

        for (Map.Entry<String, String> entry : map.entrySet()){
            line = line.replace(entry.getKey(), entry.getValue());
        }
        return line;
    }
}
