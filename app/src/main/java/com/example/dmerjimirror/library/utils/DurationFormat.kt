package com.example.dmerjimirror.library.utils

import android.icu.text.MeasureFormat
import android.icu.text.NumberFormat
import android.icu.util.MeasureUnit
import android.os.Build
import java.util.Locale
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.days
import kotlin.time.hours
import kotlin.time.milliseconds
import kotlin.time.minutes
import kotlin.time.seconds

@ExperimentalTime
data class DurationFormat(val locale: Locale = Locale.getDefault()) {
    enum class Unit {
        DAY, HOUR, MINUTE, SECOND, MILLISECOND
    }

    fun format(duration: kotlin.time.Duration, smallestUnit: Unit = Unit.SECOND): String {
        val formattedStringComponents = mutableListOf<String>()
        var remainder = duration

        for (unit in Unit.values()) {
            val component = calculateComponent(unit, remainder)

            remainder = when (unit) {
                Unit.DAY -> remainder - component.days
                Unit.HOUR -> remainder - component.hours
                Unit.MINUTE -> remainder - component.minutes
                Unit.SECOND -> remainder - component.seconds
                Unit.MILLISECOND -> remainder - component.milliseconds
            }

            val unitDisplayName = unitDisplayName(unit)

            if (component > 0) {
                val formattedComponent = NumberFormat.getInstance(locale).format(component)
                formattedStringComponents.add("$formattedComponent$unitDisplayName")
            }

            if (unit == smallestUnit) {
                val formattedZero = NumberFormat.getInstance(locale).format(0)
                if (formattedStringComponents.isEmpty()) formattedStringComponents.add("$formattedZero$unitDisplayName")
                break
            }
        }

        return formattedStringComponents.joinToString(" ")
    }

    private fun calculateComponent(unit: Unit, remainder: Duration) = when (unit) {
        Unit.DAY -> remainder.inDays.toLong()
        Unit.HOUR -> remainder.inHours.toLong()
        Unit.MINUTE -> remainder.inMinutes.toLong()
        Unit.SECOND -> remainder.inSeconds.toLong()
        Unit.MILLISECOND -> remainder.inMilliseconds.toLong()
    }

    private fun unitDisplayName(unit: Unit) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val measureFormat = MeasureFormat.getInstance(locale, MeasureFormat.FormatWidth.NARROW)
        when (unit) {
            DurationFormat.Unit.DAY -> measureFormat.getUnitDisplayName(MeasureUnit.DAY)
            DurationFormat.Unit.HOUR -> measureFormat.getUnitDisplayName(MeasureUnit.HOUR)
            DurationFormat.Unit.MINUTE -> measureFormat.getUnitDisplayName(MeasureUnit.MINUTE)
            DurationFormat.Unit.SECOND -> measureFormat.getUnitDisplayName(MeasureUnit.SECOND)
            DurationFormat.Unit.MILLISECOND -> measureFormat.getUnitDisplayName(MeasureUnit.MILLISECOND)
        }
    } else {
        when (unit) {
            Unit.DAY -> "day"
            Unit.HOUR -> "hour"
            Unit.MINUTE -> "min"
            Unit.SECOND -> "sec"
            Unit.MILLISECOND -> "msec"
        }
    }
}