package org.abubaker.countdowntimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import org.abubaker.countdowntimer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // Binding Object
    private lateinit var binding: ActivityMainBinding

    // Variable for Timer which will be initialized later.
    private var countDownTimer: CountDownTimer? = null

    // The Duration of the timer in milliseconds
    private var timerDuration: Long = 60000

    // pauseOffset = timerDuration - time left
    private var pauseOffset: Long = 0

    /**
     * onCreate()
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

        // setSupportActionBar(toolbar)

        binding.tvTimer.text = "${(timerDuration / 1000).toString()}"

        binding.btnStart.setOnClickListener {
            startTimer(pauseOffset)
        }

        binding.btnPause.setOnClickListener {
            pauseTimer()
        }

        binding.btnReset.setOnClickListener {
            resetTimer()
        }

    }


    /**
     * Function is used to start the timer of 60 Seconds.
     */
    private fun startTimer(pauseOffsetL: Long) {

        // Passing pauseOffsetL will help us in restarting the timer
        countDownTimer = object : CountDownTimer(timerDuration - pauseOffsetL, 1000) {

            // onTick() - it is called on every countDownInterval (1 sec = 1000)
            override fun onTick(millisUntilFinished: Long) {
                this@MainActivity.pauseOffset = timerDuration - millisUntilFinished
                binding.tvTimer.text = (millisUntilFinished / 1000).toString()
            }

            // onFinish()
            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Timer is finished", Toast.LENGTH_SHORT).show()
            }

        }.start()

    }


    /**
     * Function is used to pause the count down timer which is running
     */
    private fun pauseTimer() {
        if (countDownTimer != null) {

            // Cancel the current timer
            countDownTimer!!.cancel()

        }
    }

    /**
     *  Function is used to reset the count down timer which is running and set
     */
    private fun resetTimer() {
        if (countDownTimer != null) {

            // Cancel the current timer
            countDownTimer!!.cancel()

            // Set tv_timer's text back to 60 seconds
            binding.tvTimer.text = "${(timerDuration / 1000).toString()}"

            // Set it back to null
            countDownTimer = null

            // Set pauseOffset back to 0, otherwise we may face confusing issues in the app
            pauseOffset = 0
        }
    }


}