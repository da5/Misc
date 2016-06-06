package practice.drivers;

import javafx.util.Pair;

import java.io.*;
import java.util.*;

/**
 * Created by arindam.das on 21/05/16.
 */
public class Druva {

    private static int versionComparator(String version1, String version2){
        String[] version1Parts = version1.split("\\.");
        String[] version2Parts = version2.split("\\.");
        int idx = 0;
        while(idx<version1Parts.length && idx< version2Parts.length){
            if(Integer.parseInt(version1Parts[idx])!=Integer.parseInt(version2Parts[idx])){
                return (Integer.parseInt(version1Parts[idx])-Integer.parseInt(version2Parts[idx]));
            }else{
                idx++;
            }
        }
        if(idx==version1Parts.length && idx==version2Parts.length){
            return 0;
        }else if(idx==version1Parts.length && idx<version2Parts.length){
            return -1;
        }else if(idx<version1Parts.length && idx==version2Parts.length){
            return 1;
        }
        return 0;
    }

    public static void main(String[] args){
        FileInputStream in;
        FileOutputStream out;
        Map<String, List<Pair<String, String>>> serverMap;
        Map<String, String> latestSoftwareMap;
        Set<String> resultSet;
        try {
            in = new FileInputStream("/Users/arindam.das/Documents/input.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            out = new FileOutputStream("/Users/arindam.das/Documents/output.txt");

            String line;
            int ctr = 1;
            serverMap = new HashMap<>();
            latestSoftwareMap = new HashMap<>();
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] words = line.split(",");
                if(words.length!=4){
                    throw new Exception("Wrong number of parameters in line "+ ctr);
                }
                String server = words[0].trim();
                String softwareType = words[1].trim();
                String softwareName = words[2].trim();
                String softwareVersion = words[3].trim();
                if(serverMap.containsKey(server)){
                    serverMap.get(server).add(new Pair(softwareType+"~"+softwareName,softwareVersion));
                }else {
                    List<Pair<String, String>> softwareList = new ArrayList<>();
                    softwareList.add(new Pair(softwareType+"~"+softwareName,softwareVersion));
                    serverMap.put(server,softwareList);

                }
                if(latestSoftwareMap.containsKey(softwareType+"~"+softwareName)){
                    String currentVersion = latestSoftwareMap.get(softwareType+"~"+softwareName);
                    if(versionComparator(currentVersion, softwareVersion) < 0){
                        latestSoftwareMap.put(softwareType+"~"+softwareName,softwareVersion);
                    }
                }else {
                    latestSoftwareMap.put(softwareType+"~"+softwareName,softwareVersion);
                }
                ctr++;
            }
            resultSet = new HashSet<>();
            for(Map.Entry<String, List<Pair<String, String>>> entry: serverMap.entrySet()){
                for(Pair<String, String> item: entry.getValue()){
                    if(!latestSoftwareMap.get(item.getKey()).equals(item.getValue()))    {
                        resultSet.add(entry.getKey());
                    }
                }

            }
            System.out.println(resultSet);
//            PrintWriter writer = new PrintWriter("/Users/arindam.das/Documents/output.txt", "UTF-8");
//            writer.close();
            for(String server: resultSet){
                byte[] bytes = (server+"\n").getBytes();
                out.write(bytes);
//                writer.println(server);
            }
//            writer.close();
            out.close();
            in.close();
            br.close();
        }catch (Exception e) {
            System.out.println(e);
        }
    }
}
