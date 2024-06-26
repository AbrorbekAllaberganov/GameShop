package com.example.Game.service;

import com.example.Game.entity.Attachment;
import com.example.Game.payload.Result;
import com.example.Game.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final Result result;
    private final AttachmentRepository attachmentRepository;

    @Value("${upload}")
    private String uploadPath;


    public Result save(MultipartFile multipartFile) {
        Attachment file = new Attachment();
// music.mp3
        file.setContentType(multipartFile.getContentType());
//        file.setExtension();
        file.setFileSize(multipartFile.getSize());
        file.setName(multipartFile.getOriginalFilename());
        file.setHashId(UUID.randomUUID().toString());
        file.setExtension(getExtension(file.getName()));

        Date now = new Date();
        java.io.File uploadFolder = new java.io.File(String.format("%s/upload/%d/%d/%d/", this.uploadPath,
                1900 + now.getYear(), 1 + now.getMonth(), now.getDate()));

        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }
        file.setUploadPath(String.format("upload/%d/%d/%d/%s.%s",
                1900 + now.getYear(),
                1 + now.getMonth(),
                now.getDate(),
                file.getHashId(),
                file.getExtension()));
        Attachment file2 = attachmentRepository.save(file);
        java.io.File file3 = new java.io.File(uploadFolder.getAbsoluteFile(), String.format("%s.%s", file2.getHashId(), file2.getExtension()));
        try {
            multipartFile.transferTo(file3);

            return result.success(file2.getHashId());
        } catch (IOException e) {
            e.printStackTrace();
            return result.exception(e);
        }
    }

    private String getExtension(String fileName) {
        String extension = null;
        if (fileName != null && !fileName.isEmpty()) {
            int index = fileName.lastIndexOf(".");
            extension = fileName.substring(index + 1);
        }
        return extension;
    }

    public Attachment findByHashId(String hashId) {
        return attachmentRepository.findByHashId(hashId);
    }


    public boolean delete(String hashId) {
        try {
            Attachment file = attachmentRepository.findByHashId(hashId);
            java.io.File file1 = new java.io.File(String.format("%s/%s", uploadPath, file.getUploadPath()));
            file1.delete();
            attachmentRepository.delete(file);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
