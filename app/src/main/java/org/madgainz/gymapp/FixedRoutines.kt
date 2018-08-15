package org.madgainz.gymapp

import org.madgainz.gymapp.model.*

val TIME10s = Time(10)
val TIME15s = Time(15)
val TIME45s = Time(45)

val sixPackAbsForBeginnersYouCanDoAnywhere2018: TimedWorkout =
        TimedWorkout("Six Pack Abs For Beginners You Can Do Anywhere | 2018", listOf(
                TimedStage("Knee Slaps", TIME10s, TIME45s),
                TimedStage("Russian Twists", TIME15s, TIME45s),
                TimedStage("Leg Raises", TIME15s, TIME45s),
                TimedStage("Knee Raises", TIME15s, TIME45s),
                TimedStage("Flutter Kicks", TIME15s, TIME45s),
                TimedStage("Plank Knee to Elbow", TIME15s, TIME45s),
                TimedStage("Chair Sit Ups", TIME15s, TIME45s),
                TimedStage("Seated In and Outs", TIME15s, TIME45s),
                TimedStage("Jumping Jacks", TIME15s, TIME45s)
        ))
