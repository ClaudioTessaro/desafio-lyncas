package com.desafio.lyncas.contas.domain.utils;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class MapperUtil {

    public String trimString(String value) {
        return Strings.isNotBlank(value) ? value.trim() : null;
    }

    public static String generateRandomAccountNumber() {
        Random random = new Random();
        int accountNumber = 10000000 + random.nextInt(90000000);
        return String.valueOf(accountNumber);
    }
    public static String generateRandomBranchCode() {
        Random random = new Random();
        int branchCode = 100000 + random.nextInt(900000);
        return String.valueOf(branchCode);
    }

}
