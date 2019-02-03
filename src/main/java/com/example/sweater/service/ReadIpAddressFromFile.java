package com.example.sweater.service;

import lombok.Data;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Data
@Service
public class ReadIpAddressFromFile {
    private List<String> ipAddress = new ArrayList<>();
    private File file;
    private Scanner scanner;
    private Resource config;

    @PostConstruct
    public void init() {


    }

    @Scheduled(fixedRate = 5000)
    public void readAndAddToList() throws IOException {

        DefaultResourceLoader drl = new DefaultResourceLoader();
        config = drl.getResource("templates/BlackListIp.txt");
        InputStream is = config.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        ipAddress.clear();
        String line;
        while ((line = br.readLine()) != null) {

              ipAddress.add(line);

        }
    }


}
