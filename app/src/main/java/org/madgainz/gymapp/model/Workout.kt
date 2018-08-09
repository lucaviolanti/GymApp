package org.madgainz.gymapp.model

data class Workout(val name: String, val exercises: List<Stage>)

data class TimedWorkout (val name: String, val exercises: List<TimedStage>)

sealed class Stage

data class CompositeStage(val name: String, val type: List<ExerciseTypeComponent>) : Stage()

data class TimedStage(val name: String, val time: Time) : Stage()

sealed class ExerciseTypeComponent
data class Time(val seconds: Long) : ExerciseTypeComponent()
data class Weight(val kilos: Double) : ExerciseTypeComponent()
data class Reps(val number: Int) : ExerciseTypeComponent()
data class Distance(val meters: Double) : ExerciseTypeComponent()
data class Pace(val pace: Double) : ExerciseTypeComponent()