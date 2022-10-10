package com.example.snsfood.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class DtUtil {
    LocalDateTime now = LocalDateTime.now();

    String format = now.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG));

}
