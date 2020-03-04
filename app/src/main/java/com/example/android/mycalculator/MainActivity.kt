package com.example.android.mycalculator

import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.ColorSpace
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textViewCalc: TextView
    private lateinit var delete: Button
    private lateinit var textViewRes: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewCalc = findViewById(R.id.textViewCalculator)
        textViewRes = findViewById(R.id.textViewResult)

        val numbers: Array<Button> = arrayOf(
            findViewById(R.id.nine),
            findViewById(R.id.eight),
            findViewById(R.id.seven),
            findViewById(R.id.six),
            findViewById(R.id.five),
            findViewById(R.id.four),
            findViewById(R.id.three),
            findViewById(R.id.two),
            findViewById(R.id.one),
            findViewById(R.id.zero)
        )

        val operators: Array<Button> = arrayOf(
            findViewById(R.id.plus),
            findViewById(R.id.minus),
            findViewById(R.id.division),
            findViewById(R.id.multiple)
        )

        val brackets: Array<Button> = arrayOf(
            findViewById(R.id.leftBracket),
            findViewById(R.id.rightBracket)
        )


        var txt = String()
        var lastSymbol = " "

        for (i in numbers) {
            i.setOnClickListener {
                if (it is Button) {
                    txt += it.text
                    lastSymbol = it.text.toString()
                    textViewCalc.text = txt
                }
            }
        }


        for (i in operators) {
            i.setOnClickListener {
                if (it is Button) {
                    if (!"*/-+".contains(lastSymbol) && txt.isNotEmpty()) {
                        if (!(lastSymbol == "(" && "*+/".contains(it.text))) {
                            txt += it.text
                            lastSymbol = it.text.toString()
                            textViewCalc.text = txt
                        }
                    }
                }
            }
        }

        var countLeftBrackets = 0
        var countRightBrackets = 0


        for (i in brackets) {
            i.setOnClickListener {
                if (it is Button) {
                    if (it.text == "(" && ("+-*/(".contains(lastSymbol) || lastSymbol == " ")) {
                        txt += it.text
                        lastSymbol = it.text.toString()
                        textViewCalc.text = txt
                        countLeftBrackets++

                    } else if (it.text == ")") {

                        if (countLeftBrackets > countRightBrackets && "0123456789)".contains(
                                lastSymbol
                            )
                        ) {
                            txt += it.text
                            lastSymbol = it.text.toString()
                            textViewCalc.text = txt
                            countRightBrackets++
                        }
                    }
                }
            }
        }
//!"+-/*".contains(lastSymbol

        val buttonEqual: Button = findViewById(R.id.equal)


        val calculator = CalculatorModel()
        buttonEqual.setOnClickListener {
            if (txt !== "") {
                textViewRes.text = calculator.calculate(txt)
                textViewCalc.setTextColor(Color.GREEN)
                txt = ""
            }
        }


        delete = findViewById(R.id.delete)
        delete.setOnClickListener {
            txt = ""
            textViewCalc.text = txt
            lastSymbol = " "
        }


    }


}
