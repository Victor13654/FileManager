package com.exhanger.vvasiuk.Utils;

import com.exhanger.vvasiuk.entity.FilesNumber;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class FileWritter {

    public static void write(FilesNumber filesNumber) throws FileNotFoundException, UnsupportedEncodingException {
        try(PrintWriter writer = new PrintWriter("count.txt", "UTF-8")) {
            writer.println("HOME : " + filesNumber.getHome());
            writer.println("DEV : " + filesNumber.getDev());
            writer.println("TEST : " + filesNumber.getTest());
            writer.println("SUMM : " + filesNumber.getSumm());
        }
    }

}
