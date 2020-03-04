package com.example.android.mycalculator

import android.util.Log
import java.util.*

class CalculatorModel {


    fun calculate(input: String): String {

        return try {

            val entry = toChangeEntry(input)
            count(entry)
        } catch (e: EmptyStackException) {

            "Некоректный ввод, попробуйте еще"
        }

    }


    private fun toChangeEntry(input: String): String {

        val stack = Stack<Char>()
        var result = String()

        for (i in input.indices) {

            result += if (!isOperator(input, i) && !isSpace(input[i])) {
                input[i]
            } else {
                ' '
            }


            if (isOperator(input, i)) {

                when {

                    input[i] == '(' -> stack.push(input[i])
                    input[i] == ')' -> {

                        var s: Char = stack.pop()

                        while (s != '(') {

                            result += "$s"
                            s = stack.pop()
                        }
                    }
                    else -> {

                        if (stack.count() > 0) {

                            if (priority(input[i]) <= priority(stack.peek())) {

                                result += stack.pop()
                            }
                        }
                        stack.push(input[i])
                    }
                }
            }
        }

        while (stack.count() > 0) {

            result += stack.pop()
        }

        return result
    }


    private fun count(input: String): String {

        var result: Double

        val temp = Stack<Double>()

        var help: Int = -1

        for (i in input.indices) {

            var number = ""

            for (n in i until input.length) {
                if (!isOperator(input, n) && !isSpace(input[n]) && n > help) {

                    number += input[n]
                    help = n

                } else if (isOperator(input, n) || isSpace(input[n])) {

                    if (number != "") {
                        temp.push(number.toDouble())
                    }
                    break
                }


            }



            if (isOperator(input, i)) {
                val first: Double = temp.pop()
                val second: Double = temp.pop()

                result = when (input[i]) {
                    '+' -> second + first
                    '-' -> second - first
                    '/' -> second / first
                    else -> second * first
                }
                temp.push(result)
            }
        }
        return temp.peek().toString()
    }


    private fun isOperator(s: String, i: Int): Boolean {

        if (("+/*()".contains(s[i]))) {

            return true
        } else if (i == 0 && s[i] == '-') {

            return false
        } else if (i > 0 && s[i] == '-') {

           /* if (isOperator2(s[i-1])|| isSpace(s[i-1])){
                return false
            } else return true*/
            return !(isOperator2(s[i - 1]) || isSpace(s[i - 1]))

        }
        return false
    }


    private fun isOperator2(s: Char): Boolean {

        if (("+/*-(".contains(s))) {
            return true
        }
        return false
    }


    private fun isSpace(s: Char): Boolean {

        if (s == ' ') {
            return true
        }
        return false
    }


    private fun priority(operator: Char): Int {
        return when (operator) {
            '(' -> 0
            ')' -> 1
            '+' -> 2
            '-' -> 3
            '*' -> 4
            '/' -> 4
            else -> 5
        }
    }


}