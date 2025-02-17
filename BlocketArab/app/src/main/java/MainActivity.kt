package com.example.myapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.blocketarab.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  // تعيين الواجهة للنشاط

        // العثور على الأزرار وتعيين مستمعات لها
        val viewToolsButton = findViewById<Button>(R.id.viewToolsButton)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        // تعيين مستمعين للأزرار
        viewToolsButton.setOnClickListener {
            // هنا يمكنك إضافة كود لعرض قائمة الأدوات
        }

        logoutButton.setOnClickListener {
            // هنا يمكنك إضافة كود لتسجيل الخروج
        }
    }
}
