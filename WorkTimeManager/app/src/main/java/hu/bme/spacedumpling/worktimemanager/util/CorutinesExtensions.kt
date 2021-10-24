package hu.bme.spacedumpling.worktimemanager.util

import kotlinx.coroutines.channels.SendChannel

fun <T> SendChannel<T>.safeOffer(value: T) {
    try {
        if (!isClosedForSend) offer(value)
    } catch (e: Exception) {
    }
}