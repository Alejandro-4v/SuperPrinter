package org.superprinter.utils;

public class Finals {

    public static final int PAGE_SIZE = 400;

    public static final int EMPLOYEES_COUNT = 5;
    public static final int BLACK_AND_WHITE_PRINTERS_COUNT = 3;
    public static final int COLOR_PRINTERS_COUNT = 2;

    public static final String KAFKA_BOOTSTRAP_SERVERS = "localhost:9092";
    public static final String TOPIC_BLACK = "black";
    public static final String TOPIC_COLOR = "color";

    public static final String BLACK_PRINTS_PATH = Directory.STATIONERS_PRINTS_PATH + TOPIC_BLACK + "/";
    public static final String COLOR_PRINTS_PATH = Directory.STATIONERS_PRINTS_PATH + TOPIC_COLOR + "/";

    public static final int EMPLOYEE_SLEEP_TIME_MS = 10000;

}
