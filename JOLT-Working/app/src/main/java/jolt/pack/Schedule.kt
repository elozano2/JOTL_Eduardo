package jolt.pack

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Schedule : Fragment() {

    lateinit var am: AlarmManager
    lateinit var tp: TimePicker
    lateinit var update_text: TextView
    lateinit var btnStart: Button
    lateinit var btnStop: Button
    lateinit var addSchedule: FloatingActionButton
    lateinit var timeTv : TextView

    var hour: Int = 0
    var min: Int = 0
    lateinit var pi : PendingIntent

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_schedule, container, false)
        val rootView2 = inflater.inflate(R.layout.schedule_dialog, container, false)

        am = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        tp = rootView2.findViewById<TimePicker>(R.id.tp)
        update_text = rootView2.findViewById(R.id.update_text) as TextView
        btnStart = rootView2.findViewById(R.id.start_alarm) as Button
        btnStop = rootView2.findViewById(R.id.stop_alarm) as Button
        addSchedule = rootView.findViewById<FloatingActionButton>(R.id.fab1)
        timeTv = rootView.findViewById(R.id.timeTv) as TextView


        var myIntent : Intent = Intent(activity, AlarmReceiver::class.java)
        var calendar =  android.icu.util.Calendar.getInstance()

        addSchedule.setOnClickListener{

            val mDialogView = LayoutInflater.from(activity).inflate(R.layout.schedule_dialog, null)
            btnStart = mDialogView.findViewById(R.id.start_alarm) as Button
            btnStop = mDialogView.findViewById(R.id.stop_alarm) as Button
            tp = mDialogView.findViewById<TimePicker>(R.id.tp)
            am = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val mBuilder = AlertDialog.Builder(activity)
                .setView(mDialogView)
                .setTitle("Set Schedule")
            val mAlertDialog = mBuilder.show()

            btnStart.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    if (Build.VERSION.SDK_INT   >= Build.VERSION_CODES.M) {
                        calendar.set(android.icu.util.Calendar.HOUR_OF_DAY, tp.hour)
                        calendar.set(android.icu.util.Calendar.MINUTE, tp.minute)
                        calendar.set(android.icu.util.Calendar.SECOND, 0)
                        calendar.set(android.icu.util.Calendar.MILLISECOND, 0)
                        hour = tp.hour
                        min = tp.minute
                    }
                    else {
                        calendar.set(android.icu.util.Calendar.HOUR_OF_DAY,tp.currentHour)
                        calendar.set(android.icu.util.Calendar.MINUTE,tp.currentMinute)
                        calendar.set(android.icu.util.Calendar.SECOND,0)
                        calendar.set(android.icu.util.Calendar.MILLISECOND,0)
                        hour = tp.currentHour
                        min = tp.currentMinute
                    }
                    var hr_str : String = hour.toString()
                    var min_str : String = min.toString()
                    if (hour > 12){
                        hr_str = (hour - 12).toString()
                    }
                    if (min < 10 ) {
                        min_str = "0$min"
                    }
                    set_alarm_text("Alarm set to: $hr_str : $min_str")
                    myIntent.putExtra("extra", "on")
                    pi = PendingIntent.getBroadcast(activity, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                    am.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,pi)
                    mAlertDialog.dismiss()

                }
            })
            btnStop.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v:View?) {
                    mAlertDialog.dismiss()
                    set_alarm_text("Alarm off")
                    pi = PendingIntent.getBroadcast(activity, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                    am.cancel(pi)
                    myIntent.putExtra("extra","off")
                    getActivity()?.sendBroadcast(myIntent)
                 }
            })



        }

        return rootView
        }

        private fun set_alarm_text(s: String) {
            update_text.setText(s)
            timeTv.setText(s)

        }
    }



    /*fabBtn.setOnClickListener {
    val cal = android.icu.util.Calendar.getInstance()
    val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
        cal.set(android.icu.util.Calendar.HOUR_OF_DAY, hour)
        cal.set(android.icu.util.Calendar.MINUTE, minute)

        timeTv.text = SimpleDateFormat ("HH:mm").format(cal.time)
    }

    TimePickerDialog ( activity, timeSetListener, cal.get(android.icu.util.Calendar.HOUR_OF_DAY), cal.get(
        android.icu.util.Calendar.MINUTE), false).show()

}*/

    /* override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
     }*/
