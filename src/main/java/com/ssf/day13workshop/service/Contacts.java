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
import org.springframework.ui.Model;

import com.ssf.day13workshop.model.User;

public class Contacts {

    public void save(User user, Model model, String addressBookDirPath) throws IOException {
        String fileName = user.getId();
        PrintWriter printWriter = new PrintWriter(new FileWriter(addressBookDirPath + "/" + fileName + ".txt"));

        printWriter.println(user.getName());
        printWriter.println(user.getEmail());
        printWriter.println(user.getPhoneno());
        printWriter.println(user.getDob());

        printWriter.flush();
        printWriter.close();

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
