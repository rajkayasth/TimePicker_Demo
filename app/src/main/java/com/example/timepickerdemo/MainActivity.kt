package com.example.timepickerdemo

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.TimePicker
import android.widget.TimePicker.OnTimeChangedListener
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val TIME_PICKER_INTERVAL = 15
    private var mIgnoreEvent = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val textView :TextView = findViewById(R.id.tvSelectedTime)
//        val timePicker : TimePicker = findViewById(R.id.timePicker1)


        OnClickTime()

    }
    private fun OnClickTime() {
        val textView = findViewById<TextView>(R.id.tvSelectedTime)
        val timePicker = findViewById<TimePicker>(R.id.timePicker1)
        timePicker.setOnTimeChangedListener { _, hour, minute -> var hour = hour
            var am_pm = ""
            // AM_PM decider logic
            when {hour == 0 -> { hour += 12
                am_pm = "AM"
            }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> { hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }
            var minute = minute
            if (mIgnoreEvent) return@setOnTimeChangedListener
            if (minute % TIME_PICKER_INTERVAL != 0) {
                val minuteFloor = minute - minute % TIME_PICKER_INTERVAL
                minute = minuteFloor + if (minute == minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minute == 60) minute = 0
                mIgnoreEvent = true
                timePicker.currentMinute = minute
                mIgnoreEvent = false
            }
            if (textView != null) {

                val hour = if (hour < 10) "0" + hour else hour
                val min = if (minute < 10) "0" + minute else minute
                // display format of time
                val msg = "Time is: $hour : $min $am_pm"
                textView.text = msg
                textView.visibility = View.VISIBLE
            }
        }
    }

    private val mTimePickerListener =
        OnTimeChangedListener { timePicker, hourOfDay, minute ->
            var minute = minute
            if (mIgnoreEvent) return@OnTimeChangedListener
            if (minute % TIME_PICKER_INTERVAL != 0) {
                val minuteFloor = minute - minute % TIME_PICKER_INTERVAL
                minute = minuteFloor + if (minute == minuteFloor + 1) TIME_PICKER_INTERVAL else 0
                if (minute == 60) minute = 0
                mIgnoreEvent = true
                timePicker.currentMinute = minute
                mIgnoreEvent = false
            }
        }
}