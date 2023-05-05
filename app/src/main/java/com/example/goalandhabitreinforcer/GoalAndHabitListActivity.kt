package com.example.goalandhabitreinforcer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.goalandhabitreinforcer.databinding.ActivityGoalAndHabitListBinding

class GoalAndHabitListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGoalAndHabitListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalAndHabitListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}