@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)

package com.btavares.library_base.presentation.extension

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.SystemClock
import android.view.View
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.*
import kotlin.math.abs


fun View.setOnDebouncedClickListener(action: () -> Unit){
    val actionDebouncer = ActionDebouncer(action)

    setOnClickListener{
        actionDebouncer.notifyAction()
    }
}


fun View.removeOnDebouncedClickListener(){
    setOnClickListener(null)
    isClickable = false
}



private class ActionDebouncer(private val action: () -> Unit){

    companion object{
        const val DEBOUNCE_INTERVAL_MILLISECONDS = 600L
    }

    private var lastActionTime = 0L

    fun notifyAction(){
        val now = SystemClock.elapsedRealtime()

        val millisecondsPassed = now - lastActionTime
        val actionAllowed = millisecondsPassed > DEBOUNCE_INTERVAL_MILLISECONDS
        lastActionTime = now

        if (actionAllowed){
            action.invoke()
        }
    }

}


fun setPercentageTextViewColor(percentage: Double?) : Int {
    return if (percentage != null){
        when {
            percentage < 0.0 -> Color.parseColor("#BF360C")
            else -> Color.parseColor("#43A047")
        }
    }else
        Color.TRANSPARENT
}

fun roundNumber(number: Double?) : String?{
    return "%.2f".format(number).plus("%")
}

fun setNewsItemLayoutVisibility(list :List<Any>) : Boolean {
    return !(list.isEmpty() || list.size < 2)
}

private fun getHours(date : String) : Long {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val zonedDateTime = ZonedDateTime.parse(date)
        val dat = Date.from(zonedDateTime.toInstant())
        val nowDate = Date()
        val dif = (nowDate.time - dat.time)
        (dif / (1000 * 60 * 60))
    } else {
        val format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val parseDate = SimpleDateFormat(format, Locale.getDefault()).parse(date)
        val dif = (Date().time - parseDate.time)
        (dif / (1000 * 60 * 60))
    }

}


@SuppressLint("SimpleDateFormat", "DefaultLocale")
private fun printNewsDay(date: String) : String {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val zonedDateTime = ZonedDateTime.parse(date)
        val dat = Date.from(zonedDateTime.toInstant())
        val pattern = "MMM d"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val dateFormat= simpleDateFormat.format(dat)
        return dateFormat.toString().capitalize()
    } else {
        val pattern = "MMM d"
        val dateTimeOnly = SimpleDateFormat(pattern, Locale.getDefault())
        val inputFormat2 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val parseDate2 = SimpleDateFormat(inputFormat2, Locale.getDefault()).parse(date)
        val dateFormat = dateTimeOnly.format(parseDate2)
        return dateFormat.toString().capitalize()

    }
}

fun getNewsDate(date : String?) : String{
    return if (date != null){
        val zero: Long = 0
        val one: Long = 1
        val hours =  getHours(date)
        when {
            hours.equals(zero) -> "Now"
            hours.equals(one) -> "${hours} hour ago"
            hours <= 24     -> "${hours} hours ago"
            else -> printNewsDay(date)
        }
    }else
        return ""
}


fun <T> getFirstFiveElementsFromList(list: List<T>) : MutableList<T> {
    val newList = mutableListOf<T>()
    if (list.size < 4)newList.addAll(list.toMutableList())
    else {
        for (element in  0..4){
            newList.add(list[element])
        }
    }
    return newList
}



fun getLastMonthInDays() : String {
    val today = Date()
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.MONTH, -1)
    val lastMonthDate = calendar.time

    val daysBetween = (today.time - lastMonthDate.time)/ 86400000

    val days = abs(daysBetween).toString()
    return days
}

@SuppressLint("SimpleDateFormat")
fun getLastHourDateTime() : String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.HOUR, -1)
    val lastHour = calendar.time
    val sdf = SimpleDateFormat("MM/dd/yyyy HH:mm")
    val formatted = sdf.format(lastHour)

    val sDate = sdf.parse(formatted).time / 1000L
    return sDate.toString()
}


@SuppressLint("SimpleDateFormat")
fun getCurrentDateTime() : String {
    val date = Date()
    val sdf = SimpleDateFormat("MM/dd/yyyy HH:mm")
    val formated = sdf.format(date)

    val sDate = sdf.parse(formated).time / 1000L
    return sDate.toString()
}

fun getLastYearInDays() : String{
    val today = Date()
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.YEAR, -1)
    val lastMonthDate = calendar.time

    val daysBetween = (today.time - lastMonthDate.time)/ 86400000

    return abs(daysBetween).toString()
}

fun round(number: Double?) = BigDecimal(number!!).setScale(2, RoundingMode.HALF_EVEN).toDouble()


fun roundBigDecimal(number : String?) = BigDecimal(number).round(MathContext(5)).toString()






