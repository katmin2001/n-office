/*
 * Copyright (C) 2010 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.fis.crm.commons;

import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author thangdd8@viettel.com.vn
 * @version 1.0
 * @since Apr 12, 2010
 */
@Service
public class FileUtils {
    private final HttpServletRequest request;

    public FileUtils(HttpServletRequest request) {
        this.request = request;
    }

    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static int validateAttachFile(MultipartFile attachFile, String fileName) {
        String fileExt = "7z,rar,zip,txt,ppt,pptx,doc,docx,xls,xlsx,pdf,jpg,jpeg,png,bmp,gif";
        List<String> lstValidFileExt = Arrays.asList(fileExt.split(","));

        String fileType = fileName.toLowerCase().substring(fileName.lastIndexOf(".") + 1);
        if (!lstValidFileExt.contains(fileType)) {
            return 24; //24-wrongFileType
        }

        double bytes = attachFile.getSize();
        double kilobytes = (bytes / 1024);
        double megabytes = (kilobytes / 1024);
        if (megabytes > 20) {
            return 25; //25-wrongFileSize20MB
        }
        return 0; //0-validate ok
    }

    public static Long validateAttachFile(MultipartFile attachFile, String fileExt, Double fileSize) {
        List<String> lstValidFileExt = Arrays.asList(fileExt.split(","));
        String fileName = attachFile.getOriginalFilename();
        String fileType = fileName.toLowerCase().substring(fileName.lastIndexOf(".") + 1);
        if (!lstValidFileExt.contains(fileType)) {
            return 24L; //24-wrongFileType
        }

        double bytes = attachFile.getSize();
        double kilobytes = (bytes / 1024);
        double megabytes = (kilobytes / 1024);
        if (megabytes > fileSize) {
            return 25L; //25-wrongFileSize20MB
        }
        return 0L; //0-validate ok
    }

    public static int validateAttachFileExcel(MultipartFile attachFile, String fileName) {
        String fileExt = "xlsx,xls";
        List<String> lstValidFileExt = Arrays.asList(fileExt.split(","));

        String fileType = fileName.toLowerCase().substring(fileName.lastIndexOf(".") + 1);
        if (!lstValidFileExt.contains(fileType)) {
            return 24; //24-wrongFileType
        }

        double bytes = attachFile.getSize();
        double kilobytes = (bytes / 1024);
        double megabytes = (kilobytes / 1024);
        if (megabytes > 20) {
            return 25; //25-wrongFileSize20MB
        }
        return 0; //0-validate ok
    }


    public static Boolean copyAttachFile(MultipartFile file, String attachFileDir, String fileName) {
        try {
            File dir = new File(attachFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Path targetLocation = Paths.get(attachFileDir + File.separator + fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            log.info("File copied successful: " + attachFileDir + File.separator + fileName);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public static Boolean deleteAttachFile(String attachFileDir, String fileName) {
        try {
            File fileDelete = new File(attachFileDir + File.separator + fileName);
            fileDelete.delete();
            log.info("Delete file successful: " + attachFileDir + File.separator + fileName);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public static void saveFile(String filePath, MultipartFile multipartFile) throws IOException {
        File file = new File(filePath);
        File folder = file.getParentFile();
        if (!folder.exists())
            folder.mkdirs();
        FileCopyUtils.copy(multipartFile.getBytes(), new FileOutputStream(new File(filePath)));
    }

    public static String copyFile(String filePath, String newFilePath) throws IOException {
        File file = new File(filePath);
        String fileName = file.getName();
        File newFile = new File(newFilePath);
        File folder = newFile.getParentFile();
        if (!folder.exists())
            folder.mkdirs();
        org.apache.commons.io.FileUtils.copyFileToDirectory(file, newFile);
        return fileName;
    }

    public static Object downloadFile(String path, String fileName) {
        File file;
        file = new File(path + fileName);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            return IOUtils.toByteArray(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String saveFile(MultipartFile file) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        String encryptFileName = simpleDateFormat.format(new Date()) + "_" + file.getOriginalFilename();
        byte[] bytes = file.getBytes();
        String rootPath = request.getServletContext().getRealPath("/uploads/files/" + encryptFileName);
        Path path = Paths.get(rootPath);
        Files.write(path, bytes);
        return encryptFileName;
    }
}
