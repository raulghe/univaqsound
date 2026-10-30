package it.univq.service.impl;

import it.univq.service.LoggerService;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerServiceImpl implements LoggerService {

    private static final LoggerService istanza = new LoggerServiceImpl();
    private static final String LOG_FILE = "univaqsound.log";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private LoggerServiceImpl() {}

    public static LoggerService getIstanza() {
        return istanza;
    }

    @Override
    public synchronized void log(String operazione, String dettagli) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String riga = String.format("[%s] [%s] %s", timestamp, operazione.toUpperCase(), dettagli);

        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(riga);
        } catch (IOException elem) {
            System.err.println("Errore di scrittura nel file di log: " + elem.getMessage());
        }

        System.out.println(riga);
    }
}
