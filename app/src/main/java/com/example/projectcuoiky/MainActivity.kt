package com.example.projectcuoiky

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.projectcuoiky.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDate


class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Hien thi bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.history -> replaceFragment(History())
                R.id.setting -> replaceFragment(Setting())
                R.id.profile -> replaceFragment(WeatherForecast())
                else->replaceFragment(Home())
            }
            true
        }



        val buttonBack = findViewById<ImageView>(R.id.backButton)
        buttonBack.setOnClickListener{
            onBackPressed()
        }

        //xu ly butotn add ch bottomNavigation
        val button_add_task = findViewById<FloatingActionButton>(R.id.floatingButtonAdd)
        button_add_task.setOnClickListener{
           dialog_CRUD.dialogAddTask(this)
        }

        //Xu ly button_back tren toolbar


        val selectDateButton:ImageView =findViewById(R.id.selectDateButton)
        selectDateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { view, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    Toast.makeText(this, "Selected Date: $selectedDate", Toast.LENGTH_SHORT).show()
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        val today = LocalDate.now()
        val textDay = findViewById<TextView>(R.id.Day_View)
        val yearDay = findViewById<TextView>(R.id.Year_View)

        textDay.text = today.month.name
        yearDay.text = today.year.toString()

    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView3, fragment)
            addToBackStack(null)
            commit()
        }

        checkFragment(fragment)

    }

    fun checkFragment(fragment: Fragment){

        val viewOther = findViewById<LinearLayout>(R.id.fragmentOther)
        val viewHome = findViewById<LinearLayout>(R.id.fragmentHome)
        if (fragment !is Home) {
            viewHome.visibility = View.GONE
            viewOther.visibility = View.VISIBLE
        } else {
            viewHome.visibility = View.VISIBLE
            viewOther.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            // Pop the back stack
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Xác nhận thoát")
                .setMessage("Bạn có chắc chắn muốn thoát không?")
                .setPositiveButton("Yes") { dialog, which ->
                    super.onBackPressed()
                }
                .setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
            val dialog: AlertDialog = builder.create()
            dialog.setOnShowListener{
                val positive: Button = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                val nagative: Button = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)

                positive.setTextColor(ContextCompat.getColor(this, R.color.YellowBocchi))
                nagative.setTextColor(ContextCompat.getColor(this, R.color.blueBocchi))
            }

            dialog.show()
        }
    }


}
