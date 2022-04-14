package com.example.dmerjimirror.library.utils

import android.os.Build
import com.example.dmerjimirror.library.extension.*
import java.time.YearMonth
import java.util.*
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime


class DateUtils {
    companion object {
        val yesterday: Date
            get() {
                return Date().dayBefore
            }

        val tomorrow: Date
            get() {
                return Date().dayAfter
            }

        fun numberOfDaysInMonth(month: Int, year: Int): Int {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val yearMonthObject = YearMonth.of(year, month)
                yearMonthObject.lengthOfMonth()
            } else {
                val myCal = GregorianCalendar(year, month - 1, 1)
                myCal.getActualMaximum(Calendar.DAY_OF_MONTH)
            }
        }

        fun buildFromDate(from: Date): Calendar {
            val c = Calendar.getInstance()
            c.time = from
            return c
        }

        @OptIn(ExperimentalTime::class)
        fun getLocalizedDateFromSeconds(seconds: Long): String {
            val duration = seconds.seconds
            val x = DurationFormat().format(duration, DurationFormat.Unit.MINUTE)
            return x
        }

        fun getTimePassedInSeconds(relativeDate: Date): Long {
            return (Date().time - relativeDate.time) / 1000
        }
    }
}