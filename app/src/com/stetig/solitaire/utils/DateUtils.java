/**
 * Created by Pratik Katariya on 03-09-2020
 */
package com.stetig.solitaire.utils;

import java.time.LocalDate;
import java.util.Date;

/**Created by Pratik Katariya on 03-09-2020 */
public class DateUtils {
    public static Date getDate(LocalDate date) {
        return java.sql.Date.valueOf(date.toString());
    }
}