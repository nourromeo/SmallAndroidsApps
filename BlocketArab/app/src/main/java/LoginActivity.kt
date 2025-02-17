package com.example.myapp

    import android.content.Intent
    import android.os.Bundle
    import android.widget.Button
    import android.widget.EditText
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import com.example.blocketarab.MainActivity
    import com.example.blocketarab.R

class LoginActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_login)  // ربط الواجهة بالنشاط

            // العثور على عناصر الواجهة
            val usernameEditText = findViewById<EditText>(R.id.username)
            val passwordEditText = findViewById<EditText>(R.id.password)
            val loginButton = findViewById<Button>(R.id.loginButton)

            // تعيين مستمع للزر
            loginButton.setOnClickListener {
                val username = usernameEditText.text.toString()
                val password = passwordEditText.text.toString()

                // التحقق من بيانات تسجيل الدخول (يمكنك استبدال هذا بالتحقق الحقيقي من الخادم)
                if (username.isNotEmpty() && password.isNotEmpty()) {
                    // الانتقال إلى الصفحة الرئيسية بعد تسجيل الدخول الناجح
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()  // إنهاء نشاط تسجيل الدخول لمنع العودة إليه عند الضغط على زر الرجوع
                } else {
                    // عرض رسالة خطأ إذا كانت البيانات غير صحيحة
                    Toast.makeText(this, "يرجى إدخال اسم المستخدم وكلمة المرور", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }