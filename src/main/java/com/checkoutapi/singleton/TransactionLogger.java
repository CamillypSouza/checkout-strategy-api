package com.checkoutapi.singleton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementação do padrão Singleton utilizando inicialização preguiçosa (lazy)
 * com double-checked locking, garantindo uma única instância mesmo em cenários
 * concorrentes, sem o custo de sincronizar toda chamada a getInstance().
 */

public class TransactionLogger {

    private static volatile TransactionLogger instance;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private static final List<String> logs = new ArrayList<>();

    private TransactionLogger() {

    }

    public static TransactionLogger getInstance() {
        if (instance == null) {
            synchronized (TransactionLogger.class) {
                if (instance == null) {
                    instance = new TransactionLogger();
                }
            }
        }
        return instance;
    }

    public synchronized void log(String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        logs.add("[" + timestamp + "] " + message);
    }

    public synchronized List<String> getLogs() {
        return Collections.unmodifiableList(logs);
    }

    public synchronized void printLogs() {
        logs.forEach(System.out::println);
    }

}
