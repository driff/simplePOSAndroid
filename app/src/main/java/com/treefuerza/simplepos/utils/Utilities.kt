package com.treefuerza.simplepos.utils

import android.view.MotionEvent
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter


const val STATUS_CREATED = 0
const val STATUS_DELETED = -1
const val STATUS_SERVED = 1
const val STATUS_RETURNED = 2
const val STATUS_PAID = 3
const val DRAWABLE_LEFT = 0
const val DRAWABLE_TOP = 1
const val DRAWABLE_RIGHT = 2
const val DRAWABLE_BOTTOM = 3
fun ZonedDateTime.toTimeString(): String {
    return this.format(DateTimeFormatter.ofPattern("HH:mm"))
}

fun handleItemSearch(event: MotionEvent, exec: (e: MotionEvent) -> Boolean): Boolean {
    if(event.action == MotionEvent.ACTION_UP) {
        return exec(event)
    }
    return false
}