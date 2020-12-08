package com.techelevator.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLog {

    public static void log(String message) {
        try (FileOutputStream stream = new FileOutputStream("audit/audit.txt", true);
             PrintWriter writer = new PrintWriter(stream)) {
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
            String formattedDate = dateTime.format(formatter);
            writer.println(formattedDate + " | " + message);
        } catch (IOException e) {
            System.out.println("Failed to log to the audit file");
        }
    }

}
