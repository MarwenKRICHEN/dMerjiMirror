package com.example.dmerjimirror.library.utils

import java.util.regex.Pattern

class RegexValidator {

    companion object {
        fun validateEmail(email: String): Boolean {
            val regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
            return patternMatches(
                email,
                regexPattern
            )
        }

        private fun patternMatches(emailAddress: String, regexPattern: String): Boolean {
            return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches()
        }
    }

}