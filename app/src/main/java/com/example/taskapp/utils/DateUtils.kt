package com.example.taskapp.utils

import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter

object DateUtils{
    val TODAY: LocalDate = LocalDate.now()
    val TOMORROW: LocalDate = LocalDate.ofEpochDay(TODAY.toEpochDay() + 1)

    val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val ZONE_OFFSET: ZoneOffset = OffsetDateTime.now().offset

}