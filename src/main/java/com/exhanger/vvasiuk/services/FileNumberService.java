package com.exhanger.vvasiuk.services;

import com.exhanger.vvasiuk.entity.FilesNumber;
import com.exhanger.vvasiuk.repository.FileNumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileNumberService {

    private final FileNumberRepository fileNumberRepository;

    @Autowired
    public FileNumberService(FileNumberRepository fileNumberRepository) {
        this.fileNumberRepository = fileNumberRepository;
    }

    public FilesNumber getFileNumber() {
        List<FilesNumber> list = fileNumberRepository.findAll();
        return list.isEmpty() ? new FilesNumber() : list.get(list.size() - 1);
    }

    public void save(FilesNumber filesNumber) {
        fileNumberRepository.save(filesNumber);
    }
}
