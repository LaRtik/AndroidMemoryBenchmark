package com.lartik.memorybenchmark

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lartik.memorybenchmark.ui.theme.MemoryBenchmarkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemoryBenchmarkTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting()
                }
            }
        }
    }
}


@Composable
fun Greeting() {
    val fileBenchmark = FilesBenchmark(LocalContext.current)
    val _RAMBenchmark = RAMBenchmark()
    fileBenchmark.run()
    _RAMBenchmark.run()
    var writeRes = fileBenchmark.getScore().second
    var readRes = fileBenchmark.getScore().first
    var ramRes = _RAMBenchmark.getScore()

    Box (contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        Column {
            Row (){
                Text(text = "Write result $writeRes MB/s")
            }
            Row {
                Text(text = "Read result $readRes MB/s")
            }
            Row {
                Text(text = "RAM Read result $ramRes MB/s")
            }
        }
    }



}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MemoryBenchmarkTheme {
        Greeting()
    }
}