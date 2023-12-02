package com.ssf.day13workshop.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.ui.Model;

import com.ssf.day13workshop.model.User;

public class Contacts {

    public void save(User user, Model model, String addressBookDirPath) throws IOException{
        String fileName = user.getId();
        PrintWriter printWriter = new PrintWriter(new FileWriter(addressBookDirPath + "/" + fileName + ".txt"));

        printWriter.println(user.getName());
        printWriter.println(user.getEmail());
        printWriter.println(user.getPhoneno());
        printWriter.println(user.getDob());

        printWriter.flush();
        printWriter.close();

    }

}