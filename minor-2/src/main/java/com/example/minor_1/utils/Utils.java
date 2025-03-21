package com.example.minor_1.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public static Map<String, String> getAuthoritiesForUser(){

        HashMap<String,String> authoritiesMap = new HashMap<>();

        List<String> studentAuthorities = Arrays.asList(
                Constants.STUDENT_SELF_INFO_AUTHORITY,
                Constants.READ_BOOK_AUTHORITY,
                Constants.MAKE_PAYMENT_AUTHORITY
        );

        List<String> adminAuthorities = Arrays.asList(
                Constants.STUDENT_INFO_AUTHORITY,
                Constants.CREATE_ADMIN_AUTHORITY,
                Constants.CREATE_BOOK_AUTHORITY,
                Constants.DELETE_BOOK_AUTHORITY,
                Constants.UPDATE_BOOK_AUTHORITY,
                Constants.READ_BOOK_AUTHORITY,
                Constants.INITIATE_TRANSACTION_AUTHORITY
        );

        String studentAuthoritiesString = String.join(Constants.DELIMITER,studentAuthorities);

        String adminAuthoritiesString = String.join(Constants.DELIMITER,adminAuthorities);

        authoritiesMap.put(Constants.STUDENT_USER,studentAuthoritiesString);
        authoritiesMap.put(Constants.ADMIN_USER,adminAuthoritiesString);

        return authoritiesMap;
    }

}
