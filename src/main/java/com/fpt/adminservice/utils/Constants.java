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

    public static interface ROLE {
        final public static String USER = "USER";
        final public static String ADMIN = "ADMIN";
    }
    public static interface MESSAGE_MAIL {
        final public static String EXPIRED_PACKAGE = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "            background-color: #f4f4f4;\n" +
                "        }\n" +
                "        .container {\n" +
                "            width: 100%;\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            background-color: #ffffff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 5px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "        }\n" +
                "        .header {\n" +
                "            background-color: #007BFF;\n" +
                "            color: #ffffff;\n" +
                "            padding: 10px 0;\n" +
                "            text-align: center;\n" +
                "            border-radius: 5px 5px 0 0;\n" +
                "        }\n" +
                "        .content {\n" +
                "            padding: 20px;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            text-align: center;\n" +
                "            color: #777777;\n" +
                "            font-size: 12px;\n" +
                "            padding: 10px 0;\n" +
                "        }\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            padding: 10px 20px;\n" +
                "            margin: 10px 0;\n" +
                "            color: #ffffff;\n" +
                "            background-color: #007BFF;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 5px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">\n" +
                "            <h1>Notification</h1>\n" +
                "        </div>\n" +
                "        <div class=\"content\">\n" +
                "            <p>Hello [User],</p>\n" +
                "            <p>We wanted to notify you about [event/notification].</p>\n" +
                "            <p>Here are the details:</p>\n" +
                "            <ul>\n" +
                "                <li>Detail 1: [Detail 1]</li>\n" +
                "                <li>Detail 2: [Detail 2]</li>\n" +
                "                <li>Detail 3: [Detail 3]</li>\n" +
                "            </ul>\n" +
                "            <p>If you have any questions, feel free to <a href=\"mailto:support@example.com\">contact us</a>.</p>\n" +
                "            <a href=\"[Action URL]\" class=\"button\">Take Action</a>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>&copy; 2024 Your Company. All rights reserved.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>\n";

    }

}
