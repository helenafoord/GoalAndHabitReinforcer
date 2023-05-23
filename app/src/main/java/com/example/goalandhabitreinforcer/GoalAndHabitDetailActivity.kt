package com.example.goalandhabitreinforcer

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.goalandhabitreinforcer.databinding.ActivityDataBinding

class GoalAndHabitDetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDataBinding
    lateinit var goal: GoalAndHabitData
    private var isCreatingNewGoal: Boolean = false
    var goalIsEditable = false


    companion object{
        const val EXTRA_GOAL = "goal"
        const val TAG = "GoalAndHabitListActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isCreatingNewGoal = intent.getBooleanExtra(GoalAndHabitListActivity.CREATING_NEW_GOAL, false)
        if(isCreatingNewGoal){
            goal = GoalAndHabitData()
            toggleEditable()
        }else{
            goal = intent.getParcelableExtra(EXTRA_GOAL)?:GoalAndHabitData()
        }
        binding.editTextGoal.setText(goal.goal)
        binding.editTextReasonForGoal.setText(goal.purpose)
        binding.editTextNumberOfTasks.setText(goal.tasks.toString())

        binding.saveButton.setOnClickListener {
            val goalToUpdate = GoalAndHabitData(
                goal = binding.editTextGoal.text.toString(),
                purpose = binding.editTextReasonForGoal.text.toString(),
                tasks = binding.editTextNumberOfTasks.text.toString().toInt(),
                tasksCompleted = 0
            )
            goal = goalToUpdate
            toggleEditable()

            // put in the backendless code to save the goal object

            // finish either way
            if(isCreatingNewGoal){
                finish()
            }
        }
    }

    private fun toggleEditable() {
        if(goalIsEditable){
            goalIsEditable = false
            binding.saveButton.isEnabled = false
            binding.saveButton.visibility = View.GONE
            binding.editTextGoal.isEnabled = false
            binding.editTextGoal.inputType = InputType.TYPE_NULL
            binding.editTextReasonForGoal.isEnabled = false
            binding.editTextReasonForGoal.inputType = InputType.TYPE_NULL
            binding.editTextNumberOfTasks.isEnabled = false
            binding.editTextNumberOfTasks.inputType = InputType.TYPE_NULL

            binding.editTextGoal.setText(goal.goal)
            binding.editTextReasonForGoal.setText(goal.purpose)
            binding.editTextNumberOfTasks.setText(goal.tasks.toString())
        }
        else{
            goalIsEditable = true
            binding.saveButton.isEnabled = true
            binding.saveButton.visibility = View.VISIBLE
            binding.editTextGoal.isEnabled = true
            binding.editTextGoal.inputType = InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE
            binding.editTextReasonForGoal.isEnabled = true
            binding.editTextReasonForGoal.inputType = InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE
            binding.editTextNumberOfTasks.isEnabled = true
            binding.editTextNumberOfTasks.inputType = InputType.TYPE_NUMBER_VARIATION_NORMAL
        }
    }
}

