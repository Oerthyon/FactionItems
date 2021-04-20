package com.superkooka.oerthyon.factionitems.utils

class StringUtils {
    companion object {
        @JvmStatic
        fun parse(template: String, vars: HashMap<String, String>): String {
            var parsedString = ""
            var previousChar = ' '
            var isVariable = false
            var variableName = ""

            template.toCharArray().forEach { char: Char ->
                run {
                    if ('}' == char && '}' == previousChar) {
                        isVariable = false
                        variableName = variableName.dropLast(1)
                        parsedString += vars[variableName] ?: ""
                    }
                    else if (isVariable) {
                        variableName += char
                    }
                    else if ('{' == char && '{' == previousChar) {
                        parsedString = parsedString.dropLast(1)
                        isVariable = true
                    } else {
                        parsedString += char
                    }

                    previousChar = char
                }
            }

            return parsedString
        }
    }
}