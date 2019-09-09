package com.treefuerza.simplepos.utils

interface OnItemClickListener<T> {
    fun onClick(t: T)
    fun onLongClick(t: T): Boolean
}