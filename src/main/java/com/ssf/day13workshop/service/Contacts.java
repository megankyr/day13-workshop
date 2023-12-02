package com.ssf.day13workshop.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    public User getUserByID(String userID, String addressBookDirPath){
        User userWithID = new User();
        String userFilePath = addressBookDirPath + "/" + userID + ".txt";
        Path userFile = Paths.get(userFilePath);
        if (!Files.exists(userFile)) {
            System.out.println("User ID does not exist.");
        }
        return userWithID;
    }

}