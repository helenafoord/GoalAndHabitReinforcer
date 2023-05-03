package com.example.goalandhabitreinforcer

import com.example.goalandhabitreinforcer.databinding.ActivityDataBinding

class GoalAndHabitListActivity {
    companion object{
        const val TAG = "FruitListActivity"
    }

    private lateinit var binding: ActivityDataBinding
    private var goalAndHabitData = mutableListOf<GoalAndHabitData>()
    //lateinit var adapter = GoalAndHabitAdapter

}