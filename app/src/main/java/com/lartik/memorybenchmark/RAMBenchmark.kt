package com.lartik.memorybenchmark

import android.app.Application
import android.content.Context
import android.util.Log
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs
import kotlin.math.log

class RAMBenchmark() {
    private val MEMORY_MUL = 1
    private val ARRAY_SIZE: Int = MEMORY_MUL * 1024 * 1024
    var readResult: Long = 0
    var rand: Random = Random()
    var data = IntArray(ARRAY_SIZE) {rand.nextInt()}


    init {
        readResult = 0
    }

    fun run(){
        this.compute()
    }

    fun getScore(): Double {
        val resRead: Double = this.MEMORY_MUL * Int.SIZE_BYTES / (this.readResult / 1000000000.0)
        return resRead
    }


    private fun compute() {
        var rand: Random = Random()

        val startTime = System.nanoTime()
        for (i in 0 until ARRAY_SIZE){
            var index = abs(rand.nextInt() % ARRAY_SIZE)
            val get_data = data[index]
        }
        this.readResult = System.nanoTime() - startTime
    }

}