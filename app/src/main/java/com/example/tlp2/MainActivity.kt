package com.example.tlp2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

	var edit1: EditText? = null
	var edit2: EditText? = null
	var text1: TextView? = null
	var text2: TextView? = null
	var text3: TextView? = null
	var q_shower: TextView? = null
	var but2: Button? = null

	var n: Int = 0
	var k: Int = 0
	var i: Int = -1
	var end: String = ""
	var function: Int = 0

	var arr: Array<Array<Int>> = arrayOf()
	var alphabet: Array<String> = arrayOf()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		val but1: Button = findViewById(R.id.but1)
		but2 = findViewById(R.id.but2)
		but2?.isVisible = false
		edit1 = findViewById(R.id.edit1)
		edit2 = findViewById(R.id.edit2)

		text1 = findViewById(R.id.text1)
		text2 = findViewById(R.id.text2)
		text3 = findViewById(R.id.text3)
		q_shower = findViewById(R.id.q_shower)

		but1.setOnClickListener {
			when (function) {
				0 -> alphabet()
				1 -> states()
				2 -> strings()
				3 -> checkStrings()
			}
			edit1?.setText("")
		}
		but2?.setOnClickListener {
			text3?.text = ""
			q_shower?.text = ""
			text1?.text = "Количество состояний:"
			text2?.isVisible = true
			edit2?.isVisible = true
			edit2?.setText("")
			but2?.isVisible = false
			function = 0
			i = -1
		}
	}

	fun alphabet() {
		n = edit1?.text.toString().toInt()
		k = edit2?.text.toString().toInt()
		arr = Array(n, { Array(k, { 0 }) })
		alphabet = Array(k, { "" })
		text1?.setText("Введите алфавит:")
		text2?.isVisible = false
		edit2?.isVisible = false
		function = 1
	}

	fun states() {
		val str = edit1?.text.toString()

		var p: Int = 0
		for (g in str) {
			if (i == -1) {
				alphabet[p] = g.toString()
			} else {
				if (g == '-') {
					arr[i][p] = -1
				} else {
					arr[i][p] = g.code - 48
				}
				text3?.text = arr[i][p].toString()
			}
			p++
		}
		text1?.setText("Введите пути:")
		i++
		if (i == n) {
			function = 2
			text1?.text = "Введите индекс(ы) конечного состояния:"
		}
	}

	fun strings() {
		end = edit1?.text.toString()
		text1?.setText("Введите строку:")
		but2?.isVisible = true
		function = 3
	}

	fun checkStrings() {
		val str = edit1?.text.toString()
		var symbol = 0
		var chain = "q0 -> "
		var p: Int
		var ok = true
		text3?.text = ""
		for (g in str) {
			ok = false
			for (h in alphabet) {
				if (h == g.toString()) {
					ok = true
				}
			}
			if (!ok) break
		}
		if (ok) {
			for (g in str) {
				p = 0
				for (h in alphabet) {
					if (h == g.toString()) {
						symbol = arr[symbol][p]
						chain += "q$symbol -> "
					}
					p++
				}
				if (symbol == -1) {
					break
				}
			}
			if (symbol == -1) {
				text3?.text = "Неизвестный путь"
			} else if (!end.contains(symbol.toString())) {
				text3?.text = "Прийти в конечное состояние невозможно"
			} else {
				text3?.text = "Успешно"
			}
		} else {
			text3?.text = "Неизвестный символ"
		}
		q_shower?.setText(chain + "f")
	}
}