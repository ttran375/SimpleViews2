package com.example.android.simpleviews2

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var progressBar: ProgressBar? = null
    var progressStatus = 0

    //create the Handler object in the UI thread
    var handler: Handler = Handler()

    /** Called when the activity is first created.  */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //show the UI
        setContentView(R.layout.activity_main)
        //
        progress = 0
        //create the progress bar object
        progressBar = findViewById<View>(R.id.progressbar) as ProgressBar
        progressBar!!.max = 1000

        //the update part
        //---do some work in background thread---
        Thread(object : Runnable //create a new thread
        {
            //implement run method
            //this is where you do the thread's job
            override fun run() {
                //�-do some work here�-
                while (progressStatus < 5000) {
                    //get the updated progess value
                    progressStatus = doSomeWork()

                    //�-Update the progress bar�-
                    //you have to do that from within UI thread
                    //by posting a message to Handler object
                    handler.post(Runnable
                    //this thread updates the progress status
                    { //set the updated progress value
                        progressBar!!.progress = progressStatus
                    })
                }

                //---hides the progress bar--- update the UI
                handler.post(Runnable { //---0 - VISIBLE; 4 - INVISIBLE; 8 - GONE---
                    progressBar!!.visibility = View.GONE
                })
            }

            //---do some long running work here---
            private fun doSomeWork(): Int {
                try {
                    //---simulate doing some work---
                    Thread.sleep(50) //one second sleep
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                return ++progress
            }
        }).start() //starts the background thread
    }

    companion object {
        var progress = 0
    }
}
