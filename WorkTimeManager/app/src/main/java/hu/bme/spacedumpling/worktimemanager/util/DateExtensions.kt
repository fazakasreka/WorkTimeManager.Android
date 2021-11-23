package hu.bme.spacedumpling.worktimemanager.util

import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

const val ONLY_DATE_FORMAT = "yyyy.MM.dd."
const val HOUR_AND_MIN_FORMAT = "hh:mm"

fun Date.toDate() : String{
    return SimpleDateFormat(ONLY_DATE_FORMAT).format(this)
}

fun Date.toHourMin() : String{
    return SimpleDateFormat(HOUR_AND_MIN_FORMAT).format(this)
}