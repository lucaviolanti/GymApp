package org.madgainz.gymapp.model

data class Workout(val name: String, val exercises: List<Stage>)

sealed class Stage

data class Rest(val time: Long) : Stage()

data class Exercise(val name: String, val type: List<ExerciseTypeComponent>) : Stage()

sealed class ExerciseTypeComponent
data class Time(val seconds: Long) : ExerciseTypeComponent()
data class Weight(val kilos: Double) : ExerciseTypeComponent()
data class Reps(val number: Int) : ExerciseTypeComponent()
data class Distance(val meters: Double) : ExerciseTypeComponent()
data class Pace(val pace: Double) : ExerciseTypeComponent()