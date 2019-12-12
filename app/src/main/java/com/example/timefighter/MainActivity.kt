package com.example.timefighter

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private var score = 0

class MainActivity : AppCompatActivity() {
    private var TAG = MainActivity::class.java.simpleName
    companion object {

        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }
    lateinit var buttn : Button
    lateinit var txtView : TextView
    lateinit var txtView2 : TextView
    private var gameStarted = false
    lateinit var countDownTimer : CountDownTimer
    private var initialCountDown = 60000
   private  var countDownInterval = 1000
    private var timeLeft = 60

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate called. Score is: $score")
        buttn = findViewById(R.id.button)
        txtView = findViewById(R.id.txt1)
        txtView2 = findViewById(R.id.txt2)
        buttn.setOnClickListener { incrementScore() }

    }
    override fun onSaveInstanceState(outState: Bundle) {

        super.onSaveInstanceState(outState)

        outState.putInt(SCORE_KEY, score)
        outState.putInt(TIME_LEFT_KEY, timeLeft)
        countDownTimer.cancel()

        Log.d(TAG, "onSaveInstanceState: Saving Score: $score & Time Left: $timeLeft")
    }
    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "onDestroy called.")
    }

    private fun incrementScore(){
        if (!gameStarted) {
            startGame()
        }
        score++
        var newScore = getString(R.string.your_score, score )
        txtView2.text = newScore
    }
    private fun resetGame() {
        endGame()
        score = 0

        val initialScore = getString(R.string.your_score, score)
        txtView2.text = initialScore
        val initialTimeLeft = getString(R.string.time_left, 60)
        txtView.text = initialTimeLeft
        countDownTimer = object : CountDownTimer(
            initialCountDown.toLong(),
            countDownInterval.toLong()
        ) {
            override fun onFinish() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished.toInt() / 1000

                val timeLeftString = getString(R.string.time_left, timeLeft)
                txtView.text = timeLeftString
            }


        }
        gameStarted = false
    }
    private fun startGame(){
        countDownTimer.start()
        gameStarted = true
    }
   private fun endGame (){
       Toast.makeText(this, getString(R.string.game_over_message, score), Toast.LENGTH_LONG).show()
       resetGame()
    }
    }

