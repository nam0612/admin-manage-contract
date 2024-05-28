package com.fpt.adminservice.utils;

public interface Constants {

    public static interface ResponseCode {

        final public static String SUCCESS = "00";
        final public static String FAILURE = "01";

        final public static String AccessDenied = "02";
        final public static String NOT_FOUND = "03";
        final public static String KAFKA_SEND_FAILURE = "04";
        final public static String CONNECTION_TIMEOUT = "05";
        final public static String REQUEST_TIMEOUT = "06";
        final public static String EXPIRED_JWT = "07";
        final public static String RESOURCE_ACCESS = "08";
    }

    public static interface DETAIL_DEFAULT {
        final public static int PAGE_NUMBER = 0;
        final public static int PAGE_SIZE = 11;
    }


}
