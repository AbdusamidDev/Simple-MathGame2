package developer.abdusamid.mathgame2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.button.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var firstNumber: Int = 0
    private var secondNumber: Int = 0
    private var sign: Int = 0
    private var answers = mutableSetOf<Int>()
    private var correctAnswer = 0
    private var score = 0
    private var level = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.button)
        nextTask()
        answer1.setOnClickListener(onAnswerClicked)
        answer2.setOnClickListener(onAnswerClicked)
        answer3.setOnClickListener(onAnswerClicked)
        answer4.setOnClickListener(onAnswerClicked)
    }

    private val onAnswerClicked: (view: View) -> Unit = {
        if ((it as Button).text == correctAnswer.toString()) {
            score++
        }
        if (level == 10) {
            val myIntent = Intent(this, GameOverActivity::class.java)
            myIntent.putExtra("ball", score)
            startActivity(myIntent)
            finish()
        } else {
            level++
            nextTask()
        }
    }

    private fun nextTask() {
        sign = Random.nextInt(4)
        if (sign == 3) {
            secondNumber = Random.nextInt(100)
            firstNumber = secondNumber * Random.nextInt(21)
        } else {
            firstNumber = Random.nextInt(100)
            secondNumber = Random.nextInt(100)
        }

        when (sign) {
            0 -> {
                tvSign.text = "+"
                correctAnswer = firstNumber + secondNumber
            }
            1 -> {
                tvSign.text = "-"
                correctAnswer = firstNumber - secondNumber
            }
            2 -> {
                tvSign.text = "*"
                correctAnswer = firstNumber * secondNumber
            }
            3 -> {
                tvSign.text = "/"
                correctAnswer = firstNumber / secondNumber
            }
        }
        answers.add(correctAnswer)
        tvFirstNumber.text = firstNumber.toString()
        tvSecondNumber.text = secondNumber.toString()
        while (answers.size != 4) {
            answers.add(Random.nextInt(correctAnswer - 10, correctAnswer + 11))
        }
        val shuffledAnswers = answers.shuffled() as MutableList<Int>
        answers.clear()
        answer1.text = shuffledAnswers[0].toString()
        answer2.text = shuffledAnswers[1].toString()
        answer3.text = shuffledAnswers[2].toString()
        answer4.text = shuffledAnswers[3].toString()
        shuffledAnswers.clear()
    }
}