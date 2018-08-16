package org.madgainz.gymapp

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.support.v4.app.FragmentActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.timer_activity.*
import org.madgainz.gymapp.model.Stage
import java.util.*
import java.util.concurrent.TimeUnit

class TimerActivity : FragmentActivity() {
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var mp: MediaPlayer
    private lateinit var textToSpeech: TextToSpeech
    private var running: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.timer_activity)
        mp = MediaPlayer.create(this, R.raw.chime)
        textToSpeech = object : TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech.language = Locale.UK
            }
        }) {}

        val workoutRunner = TimedWorkoutRunner(sixPackAbsForBeginnersYouCanDoAnywhere2018)

        val firstStep = workoutRunner.start()
        countDownTimer = createTimer(firstStep, workoutRunner, timer_text)

        timer_button.setOnClickListener {
            if (!running) {
                running = true
                textToSpeech.speak(firstStep.name, 0, Bundle.EMPTY, "someId")
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

    private fun createTimer(stage: Stage, runner: TimedWorkoutRunner, timerView: TextView): CountDownTimer {
        return object : CountDownTimer(stage.time.seconds * 1000, 1) {
            override fun onTick(millisUntilFinished: Long) {
                timerView.text = format(millisUntilFinished)
                display_text.text = stage.name
            }

            override fun onFinish() {
                restartTimer(runner, timerView)
            }
        }
    }

    private fun restartTimer(runner: TimedWorkoutRunner, timer: TextView) {
        countDownTimer.cancel()
        mp.start()
        countDownTimer = if (runner.hasNext()) {
            val nextStage = runner.next()
            textToSpeech.speak(nextStage.name, 0, Bundle.EMPTY, "someId")
            createTimer(nextStage, runner, timer).start()
        } else {
            running = false
            timer_text.text = getString(R.string.initial_timer_text)
            textToSpeech.speak("Finished", 0, Bundle.EMPTY, "someId")
            createTimer(runner.reset(), runner, timer)
        }
    }

    private fun format(millis: Long): String {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)) + 1)
    }
}