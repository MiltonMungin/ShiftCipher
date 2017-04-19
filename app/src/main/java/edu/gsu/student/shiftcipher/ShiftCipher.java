package edu.gsu.student.shiftcipher;

import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;

/**
 * Created by milto on 4/16/2017.
 */

public class ShiftCipher extends Activity {

    private File file;
    private String key;
    private int intKey;
    private int mostFreq;
    private String outputPath;

    public ShiftCipher(File file, String outputPath, String key){
        this.file = file;
        this.key = key;
        this.outputPath = outputPath;
        if(key.equals("guess")) {
            mostFreq = findMostChar();
            System.out.println("freq:"+mostFreq);
            intKey = (30 - mostFreq);
        }else {
            intKey = Integer.valueOf(key);
        }
        if(intKey != 0){
            doEncryptionTask();
        }

        System.out.println(intKey);
    }

    public String getKey(){
        return key;
    }

    private void doEncryptionTask(){

        String outPutFile = outputPath;

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            PrintWriter writer = new PrintWriter(outPutFile, "UTF-8");

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                char curChar;
                for(int i = 0; i < line.length(); i++) {
                    curChar = line.toLowerCase().charAt(i);
                    int curCharInt = (int) curChar;
                    curCharInt = curCharInt - 97;
                    if(curCharInt >= 0 && curCharInt < 25){
                        curCharInt = (curCharInt + intKey) % 26;
                    }
                    curCharInt = curCharInt + 97;
                    System.out.print((char)curCharInt);
                    writer.print((char)curCharInt);
                }
                writer.println("");
                System.out.println("");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int findMostChar(){
        int[] charCountArray =  new int[26];

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                for(int i = 0; i < line.length(); i++){
                    int asciiChar = (int)line.toLowerCase().charAt(i) - 97;
                    if(asciiChar < 26 && asciiChar >= 0) {
                        charCountArray[asciiChar]++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int key = 0;
        for(int i = 0; i < charCountArray.length; i++){

            System.out.print(Character.toString((char) (i + 97)) +": ");
            System.out.println(charCountArray[i]);

            if(charCountArray[i] > charCountArray[key]){
                key = i;
            }
        }
        System.out.print("key is " + Character.toString((char) (key + 97)) +": ");
        System.out.println(charCountArray[key]);

        return key;
    }

}
