package de.codecentric.kotlinspring.common

inline fun <R> R?.orElse(block: () -> R): R = this ?: block()
