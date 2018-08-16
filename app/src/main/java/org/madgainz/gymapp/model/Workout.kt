package org.madgainz.gymapp.model

data class Workout(val name: String, val exercises: List<Stage>)

data class TimedWorkout (val name: String, val exercises: List<Stage>)

sealed class Stage(open val name: String, open val time: Time)

data class TimedStage(override val name: String, override val time: Time) : Stage(name, time)

data class Rest(override val name: String = "Rest", override val time: Time) : Stage(name, time)

sealed class ExerciseTypeComponent
data class Time(val seconds: Long) : ExerciseTypeComponent()
data class Weight(val kilos: Double) : ExerciseTypeComponent()
data class Reps(val number: Int) : ExerciseTypeComponent()
data class Distance(val meters: Double) : ExerciseTypeComponent()
data class Pace(val pace: Double) : ExerciseTypeComponent()
