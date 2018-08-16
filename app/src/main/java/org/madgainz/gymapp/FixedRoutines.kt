package org.madgainz.gymapp

import org.madgainz.gymapp.model.*

val TIME45s = Time(45)

val REST10s = Rest("Next, ", Time(10))
val REST15s = Rest("Next, ", Time(15))

val sixPackAbsForBeginnersYouCanDoAnywhere2018: TimedWorkout =
        TimedWorkout("Six Pack Abs For Beginners You Can Do Anywhere | 2018", listOf(
                REST10s,
                TimedStage("Knee Slaps", TIME45s),
                REST15s,
                TimedStage("Russian Twists", TIME45s),
                REST15s,
                TimedStage("Leg Raises", TIME45s),
                REST15s,
                TimedStage("Knee Raises", TIME45s),
                REST15s,
                TimedStage("Flutter Kicks", TIME45s),
                REST15s,
                TimedStage("Plank Knee to Elbow", TIME45s),
                REST15s,
                TimedStage("Chair Sit Ups", TIME45s),
                REST15s,
                TimedStage("Seated In and Outs", TIME45s),
                REST15s,
                TimedStage("Jumping Jacks", TIME45s)
        ))
