package com.example.taskapp.utils

import org.threeten.bp.zone.TzdbZoneRulesProvider
import org.threeten.bp.zone.ZoneRulesProvider

fun Any.loadTimeZone() {
    if (ZoneRulesProvider.getAvailableZoneIds().isEmpty()) {
        val stream = this.javaClass.classLoader!!.getResourceAsStream("TZDB.dat")
        stream.use(::TzdbZoneRulesProvider).apply {
            org.threeten.bp.zone.ZoneRulesProvider.registerProvider(this)
        }
    }
}