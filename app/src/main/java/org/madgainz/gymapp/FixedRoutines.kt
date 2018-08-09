package org.madgainz.gymapp

import org.madgainz.gymapp.model.*

val fifteenSecondsOfTimeWhereYouDoNotExercise = TimedStage("Rest",Time(15))

val sixPackAbsForBeginnersYouCanDoAnywhere2018: TimedWorkout =
        TimedWorkout("Six Pack Abs For Beginners You Can Do Anywhere | 2018", listOf(
                        TimedStage("Knee Slaps", Time(45)),
                        fifteenSecondsOfTimeWhereYouDoNotExercise,
                        TimedStage("Russian Twists", Time(45)),
                        fifteenSecondsOfTimeWhereYouDoNotExercise,
                        TimedStage("Leg Raises", Time(45)),
                        fifteenSecondsOfTimeWhereYouDoNotExercise,
                        TimedStage("Knee Raises", Time(45)),
                        fifteenSecondsOfTimeWhereYouDoNotExercise,
                        TimedStage("Flutter Kicks", Time(45)),
                        fifteenSecondsOfTimeWhereYouDoNotExercise,
                        TimedStage("Plank Knee to Elbow", Time(45)),
                        fifteenSecondsOfTimeWhereYouDoNotExercise,
                        TimedStage("Chair Sit Ups", Time(45)),
                        fifteenSecondsOfTimeWhereYouDoNotExercise,
                        TimedStage("Seated In and Outs", Time(45)),
                        fifteenSecondsOfTimeWhereYouDoNotExercise,
                        TimedStage("Jumping Jacks", Time(45))
                ))