package org.madgainz.gymapp

import android.media.MediaPlayer
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Chronometer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.madgainz.gymapp.MainActivity.State.*

class MainActivity : AppCompatActivity() {

    private var state = STOPPED
    private lateinit var mp: MediaPlayer

    private enum class State(val time: Long) {
        STOPPED(0), WARM_UP(10000), COOL_DOWN(15000), EXERCISE(45000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mp = MediaPlayer.create(this, R.raw.beep)

        chronometer.onChronometerTickListener = Chronometer.OnChronometerTickListener { chronometer ->
            if (isTimedOut()) {
               state = when (state) {
                   MainActivity.State.STOPPED -> STOPPED
                   WARM_UP -> switchStateTo(EXERCISE, chronometer)
                   EXERCISE -> switchStateTo(COOL_DOWN, chronometer)
                   COOL_DOWN -> switchStateTo(EXERCISE, chronometer)
               }
            }
        }
        beepFab.setOnClickListener {
            if (state == STOPPED) {
                switchStateTo(WARM_UP, chronometer)
            } else {
                state = STOPPED
                chronometer.stop()
            }
        }
    }

    private fun switchStateTo(newState: State, chronometer: Chronometer): State {
        mp.start()
        chronometer.stop()
        chronometer.base = SystemClock.elapsedRealtime() + getTimeRemaining(newState)
        chronometer.start()
        return newState
    }

    private fun getTimeRemaining(state: State) = SystemClock.elapsedRealtime() + state.time

    private fun isTimedOut() = SystemClock.elapsedRealtime() == chronometer.base

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
