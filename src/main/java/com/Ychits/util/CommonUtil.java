package com.Ychits.util;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@Slf4j
public class CommonUtil {

    public static String getFormattedDate(String date) {

        if (StringUtils.isNotEmpty(date)) {
            if (date.equalsIgnoreCase("--")) {
                return date;
            }
            DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            try {
                String formattedDate = outputFormat.format(inputFormat.parse(date));
                return formattedDate;
            } catch (DateTimeParseException dateTimeParseException) {
                log.error("getFormattedDate Exception Date = " + date + " " + dateTimeParseException.getMessage());
            }
        }
        return date;
    }

    public static String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        var now = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        return dtf.format(now);
    }

    public static boolean isMultipartEmpty(MultipartFile file) {
        return Objects.nonNull(file) && file.getSize() != 0;
    }

}
