package org.madgainz.gymapp

import org.madgainz.gymapp.model.*

class TimedWorkoutRunner(private val workout: TimedWorkout) {
    private var pos = 0

    fun start(): Stage {
        return getStageAt(0)
    }

    fun next(): Stage {
        return if (hasNext()) {
            getStageAt(++pos)
        } else {
            TimedStage("Finished!", Time(0))
        }
    }

    private fun getStageAt(pos: Int): Stage {
        val stage = workout.exercises[pos]
        if (stage is Rest) {
            if (hasNext()) {
                return Rest("Next, ${workout.exercises[pos + 1].name}", stage.time)
            }
        }
        return stage
    }

    fun reset(): Stage {
        pos = 0
        return workout.exercises[pos]
    }

    fun hasNext(): Boolean {
        return pos < workout.exercises.size -1
    }
}