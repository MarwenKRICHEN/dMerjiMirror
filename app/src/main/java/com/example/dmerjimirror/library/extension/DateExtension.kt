package com.example.dmerjimirror.library.extension

import com.example.dmerjimirror.library.utils.DateUtils.Companion.buildFromDate
import java.util.*


val Date.dayBefore: Date
    get() {
        val c: Calendar = buildFromDate(this)
        c.add(Calendar.DATE, -1) // number of days to add
        return c.time
    }


val Date.dayAfter: Date
    get() {
        val c: Calendar = buildFromDate(this)
        c.add(Calendar.DATE, 1) // number of days to add
        return c.time
    }

val Date.monthBefore: Date
    get() {
        val c: Calendar = buildFromDate(this)
        c.add(Calendar.MONTH, -1) // number of days to add
        return c.time
    }

val Date.monthAfter: Date
    get() {
        val c: Calendar = buildFromDate(this)
        c.add(Calendar.MONTH, 1) // number of days to add
        return c.time
    }

val Date.weekBefore: Date
    get() {
        val c: Calendar = buildFromDate(this)
        c.add(Calendar.DATE, -7) // number of days to add
        return c.time
    }

val Date.weekAfter: Date
    get() {
        val c: Calendar = buildFromDate(this)
        c.add(Calendar.DATE, 7) // number of days to add
        return c.time
    }

fun Date.setHour(hour: Int) {
    val c: Calendar = buildFromDate(this)
    c.set(Calendar.HOUR, hour)
    this.time = c.time.time
}

fun Date.setMinute(minutes: Int) {
    val c: Calendar = buildFromDate(this)
    c.set(Calendar.MINUTE, minutes)
    this.time = c.time.time
}

val Date.getMonth: Int
    get() {
        val c: Calendar = buildFromDate(this)
        return c.get(Calendar.MONTH) + 1
    }

val Date.getDay: Int
    get() {
        val c: Calendar = buildFromDate(this)
        return c.get(Calendar.DATE)// number of days to add
    }

val Date.getYear: Int
    get() {
        val c: Calendar = buildFromDate(this)
        return c.get(Calendar.YEAR)// number of days to add
    }

val Date.isLastDayOfMonth: Boolean
    get() {
        return dayAfter.getMonth != getMonth
    }

fun Date.nDaysBefore(days: Int): Date {
    val c: Calendar = buildFromDate(this)
    c.add(Calendar.DATE, -days) // number of days to add
    return c.time
}

fun Date.nDaysAfter(days: Int): Date {
    val c: Calendar = buildFromDate(this)
    c.add(Calendar.DATE, days) // number of days to add
    return c.time
}

val Date.startOfMonth: Date
    get() {
        val c: Calendar = buildFromDate(this)
        c.set(Calendar.DAY_OF_MONTH, 1) // number of days to add
        return c.time
    }

val Date.endOfMonth: Date
    get() {
        return monthAfter.startOfMonth.dayBefore
    }