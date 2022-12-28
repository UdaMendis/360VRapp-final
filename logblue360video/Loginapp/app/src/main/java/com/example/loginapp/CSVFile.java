package com.example.loginapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CSVFile {
    InputStream inputStream;

    public CSVFile(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public List read(){
        List<Integer> resultList = new ArrayList<>();
        List stringlist = new ArrayList();
        //List intvalues = new ArrayList<Integer>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String csvline;
            while((csvline = reader.readLine()) != null){
                String[] row = csvline.split(",");
//                int[] numbers = new int[row.length];
//                for (int i = 0; i<row.length;i++){
//                    numbers[i] = Integer.parseInt(row[i]);
//                }
//                int factor = 0;
//                int result = 0;
//                for (int i = 0; i < numbers.length; i++) {
//                    result = numbers[i] + factor * 10;
//                    factor = result;
//                }
                List<Integer> intValues = Arrays.asList(row).stream()
                        .map(Double::parseDouble) // parse x.xxE+02 to xxx.0
                        .map(Double::intValue) // xxx.0 to integer xxx
                        .collect(Collectors.toList()); // back to List

                resultList.addAll(intValues);
            }
//            for(int i = 0; i < stringlist.size(); i++) {
//                resultList.add(Integer.parseInt(String.valueOf(stringlist.get(i))));
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try{
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringlist;
    }

}
