package com.xacalet.moobies.testutils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import java.util.concurrent.TimeUnit

object FlowTestUtil {

    @ExperimentalCoroutinesApi
    fun <T> Flow<T>.sampleList(
        destination: MutableList<T> = mutableListOf(),
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
    ) {
        TestCoroutineScope().launch {
            collect { destination.add(it) }
        }
        Thread.sleep(timeUnit.convert(time, TimeUnit.MILLISECONDS))
    }
}