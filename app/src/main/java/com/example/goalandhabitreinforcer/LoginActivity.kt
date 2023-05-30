package com.example.goalandhabitreinforcer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.backendless.Backendless
import com.backendless.BackendlessUser
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.goalandhabitreinforcer.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERID = "userid"
        const val TAG = "LoginActivity"
    }


    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Backendless.initApp(this, Constants.APP_ID, Constants.API_KEY)

        binding.buttonLoginLoginnot.setOnClickListener {
            Backendless.UserService.login(
                binding.editTextLoginUsername.text.toString(),
                binding.editTextLoginPassword.text.toString(),
                object : AsyncCallback<BackendlessUser> {
                    override fun handleResponse(response: BackendlessUser?) {
                        Log.d(TAG, "handleResponse: $response")
                        if (response != null) {
                            val goalAndHabitListActivity =
                                Intent(this@LoginActivity, GoalAndHabitListActivity::class.java)
                            goalAndHabitListActivity.putExtra(EXTRA_USERID, response.userId)
                            startActivity(goalAndHabitListActivity)
                        }
                    }

                    override fun handleFault(fault: BackendlessFault?) {
                        Log.d(TAG, "handleFault: $fault")
                    }

                }
            )

        }
    }
}