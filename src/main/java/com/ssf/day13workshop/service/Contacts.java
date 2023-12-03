package com.ssf.day13workshop.service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public List<String> generateFilePaths(Path addressBookDir) throws IOException {
        List<String> filePaths = new ArrayList<>();
        DirectoryStream<Path> stream = Files.newDirectoryStream(addressBookDir);
        for (Path filepath : stream) {
            filePaths.add(filepath.toString());
        }
        return filePaths;

    }

    public List<String> generateLinks(List<String> filePaths){
        List<String> links = new ArrayList<>();
        for (String filePath : filePaths){
            try {
            Path path = Paths.get(filePath);
            String name = Files.lines(path).findFirst().get();
            String filename = path.getFileName().toString().substring(0, 8);
            String link = String.format("<a href=\"/contact/%s\">%s</a>", filename, name);
            links.add(link);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return links;
    }

}
