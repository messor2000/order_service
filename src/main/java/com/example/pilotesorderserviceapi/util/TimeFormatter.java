package com.example.pilotesorderserviceapi.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class TimeFormatter {
  private static final String PATTERN_FORMAT = "dd.MM.yyyy hh.mm";

  public String formatTime(Instant instant) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
        .withZone(ZoneId.systemDefault());

    return formatter.format(instant);
  }
}
