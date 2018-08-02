package org.madgainz.gymapp

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.madgainz.gymapp.MainActivity.State.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var countDownTimer: CountDownTimer
    private lateinit var mp: MediaPlayer

    private enum class State(val time: Long, val displayText: String) {
        STOPPED(0, "Ready"), WARM_UP(10000, "Hello Thenx athletes"), COOL_DOWN(15000, "Woo, *clap*, Alright!"), EXERCISE(45000, "Lets do it, baby!")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mp = MediaPlayer.create(this, R.raw.chime)

        var running = false
        beepFab.setOnClickListener {
            if (!running) {
                running = true
                countDownTimer = startTimer(WARM_UP, timer_display)
            } else {
                running = false
                timer_display.text = getString(R.string.initial_timer_display)
                countDownTimer.cancel()
            }
        }
    }

    private fun createTimer(state: State, timer: TextView): CountDownTimer {
        return object : CountDownTimer(state.time, 1) {
            override fun onTick(millisUntilFinished: Long) {
                timer.text = format(millisUntilFinished)
                display_text.text = state.displayText
            }

            override fun onFinish() {
                countDownTimer.cancel()
                mp.start()
                if (state != STOPPED) {
                    timer.text = format(0)
                    countDownTimer = startTimer(getNextState(state), timer)
                } else {
                    timer.text = getString(R.string.initial_timer_display)
                }
            }
        }
    }

    private fun format(millis: Long): String {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)) + 1)
    }

    private fun startTimer(initialState: State, timer: TextView): CountDownTimer {
        return createTimer(initialState, timer).start()
    }

    private fun getNextState(currentState: State): State {
        return when (currentState) {
            MainActivity.State.STOPPED -> STOPPED
            WARM_UP -> EXERCISE
            EXERCISE -> COOL_DOWN
            COOL_DOWN -> EXERCISE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
