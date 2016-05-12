package com.arindam.das.driver;

import com.arindam.das.map.TwoLevelMap;
import com.arindam.das.map.impl.TwoLevelMapImpl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * Created by arindam.das on 18/12/15.
 */
public class Config {

    TwoLevelMap twoLevelMap;
    String currentGroup;
    String[] overrides;

    private static Config config = null;

    private Config() {
    }

    private void analyzeLine(String line){
        if(line.length()==0){
            return;
        }
        int commentIndex = line.indexOf(';');
        if(commentIndex == -1){
            commentIndex = line.length();
        }
        String beforeComment = line.substring(0,commentIndex).trim();
        if(beforeComment.length()>0){
            if(beforeComment.matches("\\[(.*)\\]")){
                currentGroup = beforeComment.substring(1,beforeComment.length()-1);
                System.out.println("Group :: " + beforeComment);
            }else if(beforeComment.matches("(.*)=(.*)")){
                String key = beforeComment.substring(0,beforeComment.indexOf('=')).trim();
                String value = beforeComment.substring(beforeComment.indexOf('=')).trim();
                String override = "default";
                if(key.matches("(.*)<(.*)>")){
                    override = key.substring(key.indexOf('<')+1,key.indexOf('>'));
                    key = key.substring(0,key.indexOf('<'));
                }
                twoLevelMap.setObject(currentGroup,key,override,value);
                System.out.println("Key :: " + key + ", Value :: " + value + ", Override :: " + override);
            }
        }
    }

    public Object get(String searchStr){
        return twoLevelMap.getObject(currentGroup,searchStr.substring(0,searchStr.indexOf('.')),searchStr.substring(searchStr.indexOf('.')));
    }

    public static Config load(String configFilePath, String[] overrides) {
        if (config == null) {
            config = new Config();
            config.twoLevelMap = new TwoLevelMapImpl();
            config.currentGroup = "default";
            config.overrides = overrides;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(configFilePath);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            String fileLine;
            while ((fileLine = bufferedReader.readLine()) != null) {
               config.analyzeLine(fileLine.trim());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File " + configFilePath + " not found!");
        } catch (Exception e) {
            System.out.println("Unknown Exception!");
            e.printStackTrace();
        }
        return config;
    }
}
