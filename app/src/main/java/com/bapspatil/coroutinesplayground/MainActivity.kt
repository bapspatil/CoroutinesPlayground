package com.bapspatil.coroutinesplayground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAsyncTwoTasks.onClick { _ ->
            val taskOneResult = async { longTaskOne() }
            val taskTwoResult = async { longTaskTwo() }
            linearParentLayout.indefiniteSnackbar("Result of Task 1=${taskOneResult.await()}; Task 2=${taskTwoResult.await()}", "OK") {
                // Empty coroutine
            }
        }

        btnRunBlocking.onClick {
            runBlocking {
                delay(4000)
                runOnUiThread { toast("Waited 4 seconds for coroutine to be done.") }
            }
        }
    }

    private suspend fun longTaskOne(): Int {
        delay(2000)
        runOnUiThread {
            toast("Task 1 completed.")
        }
        return 10
    }

    private suspend fun longTaskTwo(): Int {
        delay(6000)
        runOnUiThread {
            toast("Task 2 completed.")
        }
        return 20
    }
}
