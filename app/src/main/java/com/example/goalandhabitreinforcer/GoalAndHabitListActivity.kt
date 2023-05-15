package com.example.goalandhabitreinforcer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.goalandhabitreinforcer.databinding.ActivityGoalAndHabitListBinding

class GoalAndHabitListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoalAndHabitListBinding
    private lateinit var addGoal: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalAndHabitListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        wireWidgets()

        addGoal.setOnClickListener {

        }
    }

    private fun wireWidgets() {
        addGoal = findViewById(R.id.button_addGoal)
    }
}