package com.exhanger.vvasiuk.services;

import com.exhanger.vvasiuk.Utils.FileUtils;
import com.exhanger.vvasiuk.Utils.FileWritter;
import com.exhanger.vvasiuk.entity.Document;
import com.exhanger.vvasiuk.entity.FilesNumber;
import com.exhanger.vvasiuk.enums.DirectoryType;
import com.exhanger.vvasiuk.exception.ProccessDocumentsException;
import com.exhanger.vvasiuk.repository.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class FoldersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FoldersService.class);

    private final DocumentRepository documentRepository;
    private final FileNumberService fileNumberService;
    private static final String JAR_EXTENSION = "jar";
    private static final String XML_EXTENSION = "xml";

    @Autowired
    public FoldersService(DocumentRepository documentRepository, FileNumberService fileNumberService) {
        this.documentRepository = documentRepository;
        this.fileNumberService = fileNumberService;
    }

    public void createEmptyDirectories() throws ProccessDocumentsException {
        Arrays.asList(DirectoryType.values())
                .stream()
                .forEach(directoryType -> {
                    try {
                        createDirectory(directoryType.getCode());
                    } catch (Exception e) {
                        throw new ProccessDocumentsException("Bład podczas tworzenia katalogu " + directoryType);
                    }
                });
    }

    private void createDirectory(String directoryType) throws Exception {
        File file = new File(directoryType);
        if (!file.exists()) {
            if (file.mkdir()) {
                LOGGER.info("folder " + directoryType + " is succefully created");
            } else {
                throw new Exception();
            }
        }
    }

    public List<String> getListOfFiles() {

        File f = new File(DirectoryType.HOME_DIRECTORY.getCode());
        return Arrays.asList(f.list());
    }

    public void saveFile(Document document) {
        documentRepository.save(document);
    }

    public Document getFile(String fileName) {
        return documentRepository.findByName(fileName);
    }

    public void moveFile(String fileName) {
        String pathString = DirectoryType.HOME_DIRECTORY.getCode() + "\\" + fileName;
        File file = new File(pathString);
        FilesNumber filesNumber = copyFileNumber(fileNumberService.getFileNumber());
        Path path = file.toPath();
        try {
            BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            Date newDate = new Date( attr.creationTime().toMillis());
            String fileExtension = FileUtils.getFileExtension(fileName);
            File target = null;
            if (newDate.getHours() %2 == 0 && fileExtension.equals(JAR_EXTENSION)) {
                target = new File(DirectoryType.DEV_DIRECTORY.getCode() + "\\" + fileName);
                filesNumber.setDev(filesNumber.getDev() + 1);
                updateSumm(filesNumber);
            } else if (fileExtension.equals(JAR_EXTENSION) || fileExtension.equals(XML_EXTENSION)) {
                target = new File(DirectoryType.TEST_DIRECTORY.getCode() + "\\" + fileName);
                filesNumber.setTest(filesNumber.getTest() + 1);
                updateSumm(filesNumber);
            } else {
                filesNumber.setHome(filesNumber.getHome() + 1);
            }
            if (target != null) {
                Files.move(path, target.toPath());
            }
            fileNumberService.save(filesNumber);
            FileWritter.write(filesNumber);
        } catch (Exception e) {
            LOGGER.error("Bład podczas przeniesinia pliku", e);
        }
    }

    private void updateSumm(FilesNumber filesNumber) {
        filesNumber.setSumm(filesNumber.getSumm() + 1);
    }

    private FilesNumber copyFileNumber(FilesNumber filesNumber) {
        FilesNumber filesNumber1 = new FilesNumber();

        filesNumber1.setHome(filesNumber.getHome());
        filesNumber1.setDev(filesNumber.getDev());
        filesNumber1.setTest(filesNumber.getTest());
        filesNumber1.setSumm(filesNumber.getSumm());

        return filesNumber1;

    }


}
