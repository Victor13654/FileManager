package com.exhanger.vvasiuk.controller;

import com.exhanger.vvasiuk.entity.Document;
import com.exhanger.vvasiuk.exception.ProccessDocumentsException;
import com.exhanger.vvasiuk.services.FoldersService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    private final FoldersService foldersService;

    @Autowired
    public Controller(FoldersService foldersService) {
        this.foldersService = foldersService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        try {
            foldersService.createEmptyDirectories();
        } catch (ProccessDocumentsException e) {
            LOGGER.info("Nie udało sie stworzyć strukture katalogów");
        }
    }

    @Scheduled(fixedRate = 1000)
    public void reportCurrentTime() {
        List<String> documentsList = foldersService.getListOfFiles();
        List<String> filesToBeAdded = new ArrayList<>();
        documentsList.forEach(document -> {
            Document documentDB = foldersService.getFile(document);
            if (documentDB == null) {
                filesToBeAdded.add(document);
            }
        });
        filesToBeAdded.stream().forEach(documentToSave -> {
            foldersService.saveFile(new Document(documentToSave));
            foldersService.moveFile(documentToSave);
        });

    }


}
