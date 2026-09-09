package com.example.mycinema

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mycinema.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnLogin.setOnClickListener { attemptLogin() }
        binding.etPassword.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                attemptLogin()
                true
            } else {
                false
            }
        }
        binding.tvRegister.setOnClickListener {
            Toast.makeText(this, R.string.login_register_soon, Toast.LENGTH_SHORT).show()
        }
    }

    private fun attemptLogin() {
        val email = binding.etEmail.text?.toString()?.trim().orEmpty()
        val password = binding.etPassword.text?.toString().orEmpty()

        val emailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val passwordValid = password.length >= MIN_PASSWORD_LENGTH

        binding.tilEmail.error = if (emailValid) null else getString(R.string.login_error_email)
        binding.tilPassword.error = if (passwordValid) null else getString(R.string.login_error_password)

        if (!emailValid || !passwordValid) return

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private companion object {
        const val MIN_PASSWORD_LENGTH = 6
    }
}
