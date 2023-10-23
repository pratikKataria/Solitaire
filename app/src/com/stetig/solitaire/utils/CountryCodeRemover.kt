package com.stetig.solitaire.utils

import android.util.Log

/**
 * Created by Pratik Kataria on 07-12-2020.
 */
const val TAG = "CountryCodeRemover.kt"
val listOFCountryCode = listOf(
        "+91",
        "+971",
        "+1",
        "+974",
        "+968",
        "+971",
        "+65",
        "+973",
        "+965",
        "+966",
        "+60",
        "+44",
        "+61",
        "+966",
        "+46",
        "+27",
        "+45",
        "+977",
        "+86",
        "+880",
        "+49",
        "+31",
        "+852"
)

class CountryCodeRemover {
    companion object {
        fun numberFormatter(number: String?): String {
            if (number != null) {
                number.trim()
                for (n in listOFCountryCode) {
                    if (number.contains(n)) {
                        Log.d(TAG, "numberFormatter: ${number.contains(n)}")
                        Log.d(TAG, "numberFormatter: ${number.replace(n, "")}")
                        return number.replace(n, "")
                    }
                }
            }
            return number ?: ""
        }
    }
}