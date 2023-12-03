package com.ssf.day13workshop.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ssf.day13workshop.Day13WorkshopApplication;
import com.ssf.day13workshop.model.User;

public class Contacts {

    String addressBookDirPath = Day13WorkshopApplication.getAddressBookDirPath();

    public void save(User user) throws IOException {
        String fileName = user.getId();
        PrintWriter printWriter = new PrintWriter(new FileWriter(addressBookDirPath + "/" + fileName + ".txt"));

        printWriter.println(user.getName());
        printWriter.println(user.getEmail());
        printWriter.println(user.getPhoneno());
        printWriter.println(user.getDob());

        printWriter.flush();
        printWriter.close();

    }

    public List<String> loadUserById(String id) throws IOException {
        String filePath = addressBookDirPath + "/" + id + ".txt";
        if (Files.exists(Paths.get(filePath))){
            return Files.readAllLines(Paths.get(filePath));
        } else {
            throw new IOException("File not found");
        }

    }

    public List<User> loadUsers() throws IOException {
        List<User> users = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(addressBookDirPath))) {
            for (Path filePath : stream) {
                List<String> fields = Files.readAllLines(filePath);
                String id = filePath.getFileName().toString().substring(0, 8);
                String name = fields.get(0);
                String email = fields.get(1);
                String phoneno = fields.get(2);
                String dobString = fields.get(3);

                LocalDate dob = LocalDate.parse(dobString);

                User user = new User(id, name, email, phoneno, dob);
                users.add(user);
            }
        }
        return users;
    }

}
