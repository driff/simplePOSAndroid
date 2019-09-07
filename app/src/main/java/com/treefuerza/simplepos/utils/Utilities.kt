package com.treefuerza.simplepos.utils

import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter


const val STATUS_CREATED = 0
const val STATUS_DELETED = -1
const val STATUS_SERVED = 1
const val STATUS_RETURNED = 2
const val STATUS_PAID = 3

fun ZonedDateTime.toTimeString(): String {
    return this.format(DateTimeFormatter.ofPattern("HH:mm"))
}