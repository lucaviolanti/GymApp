package org.madgainz.gymapp

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.support.v4.app.FragmentActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import kotlinx.android.synthetic.main.timer_activity.*
import java.util.*
import java.util.concurrent.TimeUnit

class TimerActivity : FragmentActivity() {
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var mp: MediaPlayer
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.timer_activity)
        mp = MediaPlayer.create(this, R.raw.chime)
        textToSpeech = object : TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech.language = Locale.UK
            }
        }) {}

        val programme = sixPackAbsForBeginnersYouCanDoAnywhere2018.toVoiceCommands().iterator()

        var running = false

        val firstStep = programme.next()
        countDownTimer = createTimer(firstStep.second, firstStep.first, programme, timer_text)

        timer_button.setOnClickListener {
            if (!running) {
                running = true
                textToSpeech.speak(firstStep.first, 0, Bundle.EMPTY, "someId")
                countDownTimer.start()
            } else {
                running = false
                timer_text.text = getString(R.string.initial_timer_text)
                countDownTimer.cancel()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        countDownTimer.cancel()
    }

    private fun createTimer(time: Long, stageName: String, restOfProgramme: Iterator<Pair<String, Long>>, timerView: TextView): CountDownTimer {
        return object : CountDownTimer(time * 1000, 1) {
            override fun onTick(millisUntilFinished: Long) {
                timerView.text = format(millisUntilFinished)
                display_text.text = stageName
            }

            override fun onFinish() {
                restartTimer(restOfProgramme, timerView)
            }
        }
    }

    private fun restartTimer(programme: Iterator<Pair<String, Long>>, timer: TextView) {
        countDownTimer.cancel()
        mp.start()
        if (programme.hasNext()) {
            val nextStage = programme.next()
            textToSpeech.speak(nextStage.first, 0, Bundle.EMPTY, "someId")
            countDownTimer = createTimer(nextStage.second, nextStage.first, programme, timer).start()
        }
    }

    private fun format(millis: Long): String {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)) + 1)
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