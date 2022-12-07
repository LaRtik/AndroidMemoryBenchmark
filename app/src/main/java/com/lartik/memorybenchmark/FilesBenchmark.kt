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
import kotlin.math.log

class FilesBenchmark(context: Context) {

    private val BUFFER_SIZE: Int = 1024 * 4 // KB
    private val FILE_SIZE: Int = 1024 * 1024 * 64 // KB
    private val FILE_NAME: String = "bencmark.dat"
    var writeResult: Long = 0
    var readResult: Long = 0
    private var dir: File
    private lateinit var file: File
    private var fileArr: ArrayList<File>
    private var shouldTestRun: Boolean = false




    init {
        writeResult = 0
        readResult = 0
        fileArr = ArrayList<File>()
        dir = context.filesDir
        for (i in 0..15)
            fileArr.add(File(dir, FILE_NAME+i))
    }

    fun run(){
        this.compute()
        this.clean()
    }

    fun getScore(): Pair<Double, Double> {
        val resRead: Double = FILE_SIZE / (1024 * 1024.0) / (this.readResult / 1000000000.0)
        val resWrite: Double = FILE_SIZE / (1024 * 1024.0) / (this.writeResult / 16 / 1000000000.0)
        return Pair(resRead, resWrite)
    }

    private fun clean() {
        for (file in fileArr) {
            if (file != null) {
                file.delete()
            }
        }
    }

    private fun compute() {
        shouldTestRun = true
        var outputStream: BufferedOutputStream? = null
        var inputStream: BufferedInputStream? = null
        var rand: Random = Random()
        var buffer: ByteArray = ByteArray(BUFFER_SIZE)

        for (i in 0..15) {
            if (!shouldTestRun) break
            try {
                outputStream = BufferedOutputStream(FileOutputStream(fileArr.get(i)), BUFFER_SIZE)
            }
            catch (e: Exception) {
                Log.v("FILE CREATE ERROR", e.message.toString())
                return
            }
            val startTime = System.nanoTime()
            for (j in 0 until FILE_SIZE step BUFFER_SIZE) {
                if (!shouldTestRun) break
                rand.nextBytes(buffer)
                try {
                    outputStream.write(buffer)
                }
                catch (e: Exception) {
                    Log.v("FILE WRITE ERROR", e.message.toString())
                    return
                }
            }

            try {
                outputStream.close()
            }
            catch (e: IOException) {
                Log.v("OUTPUT STREAM ERROR", e.message.toString())
            }
            this.writeResult += System.nanoTime() - startTime
        }
        try {
            inputStream = BufferedInputStream(FileInputStream(fileArr.get(0)), BUFFER_SIZE)
            val startTime = System.nanoTime()
            for (j in 0 until FILE_SIZE step BUFFER_SIZE) {
                rand.nextBytes(buffer)
                try {
                    inputStream.read(buffer)
                }
                catch (e: Exception) {
                    Log.v("FILE READ ERROR", e.message.toString())
                }
            }
            try {
                inputStream.close()
            }
            catch (e: IOException) {
                Log.v("INPUT STREAM ERROR ", e.message.toString())
            }
            this.readResult = System.nanoTime() - startTime
        }
        catch (e: FileNotFoundException) {
            Log.v("FILE NOT FOULD", e.message.toString())
        }
        this.shouldTestRun = false
    }

}