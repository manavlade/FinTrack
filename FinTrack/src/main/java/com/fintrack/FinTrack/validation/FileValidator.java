package com.fintrack.FinTrack.validation;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.fintrack.FinTrack.exception.ExcelValidationException;

public class FileValidator {

    private static final Logger logger = LoggerFactory.getLogger(FileValidator.class);

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    public static void validate(MultipartFile file) {

        validatePresence(file);
        validateSize(file);
        validateExtension(file);
        validateMagicBytes(file);

        logger.info("File validation passed → name: {}, size: {} KB",
                file.getOriginalFilename(),
                file.getSize() / 1024);
    }

    private static void validatePresence(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ExcelValidationException("No file uploaded or file is empty.");
        }
    }

    private static void validateSize(MultipartFile file) {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new ExcelValidationException(
                    "File size exceeds limit of 5MB. Uploaded size: " + file.getSize());
        }
    }

   public static void validateExtension(MultipartFile file) {

    String filename = file.getOriginalFilename();

    if (filename == null) {
        throw new ExcelValidationException("File name is missing");
    }

    filename = filename.toLowerCase().trim();

    if (!(filename.endsWith(".xls") || filename.endsWith(".xlsx"))) {
        throw new ExcelValidationException("Invalid file type. Only .xls and .xlsx files are allowed.");
    }
}

    private static void validateMagicBytes(MultipartFile file) {
        try (InputStream is = file.getInputStream()) {

            byte[] bytes = is.readNBytes(8);

            if (bytes.length < 4) {
                throw new ExcelValidationException("File is too small to be valid Excel.");
            }

            boolean isXlsx = bytes[0] == 0x50 && bytes[1] == 0x4B;
            boolean isXls = bytes[0] == (byte) 0xD0 && bytes[1] == (byte) 0xCF;

            if (!isXlsx && !isXls) {
                throw new ExcelValidationException(
                        "File content is not a valid Excel format (magic bytes mismatch).");
            }

        } catch (ExcelValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ExcelValidationException("Failed to read file content: " + e.getMessage());
        }
    }
}
