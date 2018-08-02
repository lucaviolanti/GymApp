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
import android.speech.tts.TextToSpeech
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var countDownTimer: CountDownTimer
    private lateinit var mp: MediaPlayer
    private lateinit var textToSpeech: TextToSpeech
    private var stateAsInt = 0

    private enum class State(val time: Long, val displayText: String, val order: Int) {
        STOPPED(0, "Ready", -1),
        WARM_UP(10000, "Hello Thenx athletes", 0),
        COOL_DOWN(15000, "Next, ", 2),
        HIGH_KNEES(45000, "High Knees", 1),
        RUSSIAN_TWIST(45000, "Russian twists", 3),
        LEG_RAISE(45000, "Leg Raises", 5),
        KNEE_RAISE(45000, "Knee Raises", 7),
        FLUTTER_KICKS(45000, "Flutter kicks", 9),
        KNEE_TO_ELBOW_PLANK(45000, "Knees to elbows plank", 11),
        CHAIR_SITUPS(45000, "Chair situps", 13),
        SEATED_IN_AND_OUTS(45000, "Seated in and outs", 15),
        JUMPING_JACKS(45000, "Jumping jacks", 17);
    }

    private fun stateFromInt(value: Int): State {
        if (value > 17 || value < 0) {
            return STOPPED
        }
        return State.values().firstOrNull { it.order == value } ?: COOL_DOWN
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        mp = MediaPlayer.create(this, R.raw.chime)
        textToSpeech = object : TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech.language = Locale.UK
            }
        }) {}

        var running = false
        beepFab.setOnClickListener {
            if (!running) {
                running = true
                stateAsInt = 0
                textToSpeech.speak("First up, High knees", 0, Bundle.EMPTY, "someId")
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
                textToSpeech.speak(getDisplayText(), 0, Bundle.EMPTY, "someId")
                if (state != STOPPED) {
                    timer.text = format(0)
                    countDownTimer = startTimer(getState(++stateAsInt), timer)
                } else {
                    timer.text = getString(R.string.initial_timer_display)
                }
            }
        }
    }

    private fun getDisplayText(): String {
        val nextState = getState(stateAsInt + 1)
        return if (nextState == COOL_DOWN) {
            "${nextState.displayText} ${getState(stateAsInt + 2).displayText}"
        } else {
            nextState.displayText
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

    private fun getState(currentState: Int): State {
        return stateFromInt(currentState)
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
