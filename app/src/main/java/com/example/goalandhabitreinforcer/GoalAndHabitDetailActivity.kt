package com.example.goalandhabitreinforcer

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.goalandhabitreinforcer.databinding.ActivityDataBinding

class GoalAndHabitDetailActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDataBinding
    lateinit var goal: Goal
    private var isCreatingNewGoal: Boolean = false
    var goalIsEditable = false


    companion object{
        const val EXTRA_GOAL = "goal"
        const val TAG = "GoalAndHabitListActivity"
        const val EXTRA_USERID = "EXTRA_USERID"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extraGoal = intent.getParcelableExtra<Goal>(EXTRA_GOAL)
        if(extraGoal == null){
            Log.d(TAG, "onCreate: Goal is Null")
            Log.d(TAG, "onCreate: UserIf is ${intent.getStringExtra(LoginActivity.EXTRA_USERID)}")
            goal = Goal()
            toggleEditable()
            binding.saveButton.setOnClickListener {
                Log.d(TAG, "saving goal")
                goal.goal = binding.editTextGoal.text.toString()
                goal.purpose = binding.editTextReasonForGoal.text.toString()
                goal.tasks = Integer.parseInt(binding.editTextNumberOfTasks.text.toString())

            }
        }
        isCreatingNewGoal = intent.getBooleanExtra(GoalAndHabitListActivity.CREATING_NEW_GOAL, false)
        if(isCreatingNewGoal){
            goal = Goal()
            toggleEditable()
        }else{
            goal = intent.getParcelableExtra(EXTRA_GOAL)?:Goal()
        }
        binding.editTextGoal.setText(goal.goal)
        binding.editTextReasonForGoal.setText(goal.purpose)
        binding.editTextNumberOfTasks.setText(goal.tasks.toString())
        binding.checkBoxCheckGoal.isChecked = goal.goalCompleted

        binding.saveButton.setOnClickListener {
            if(!goal.ownerId.isBlank()){
                goal.ownerId = intent.getStringExtra(GoalAndHabitListActivity.USERID)?: ""
            }
            updateGoalInBackendless()
        }
    }

    private fun deleteFromBackendless(){
        Backendless.Data.of(Goal::class.java).remove( goal,
            object : AsyncCallback<Long?> {
                override fun handleResponse(response: Long?) {
                    Toast.makeText(this@GoalAndHabitDetailActivity, "${goal.goal} Deleted", Toast.LENGTH_SHORT).show()
                    finish()
                }

                override fun handleFault(fault: BackendlessFault) {
                    Log.d("BirthdayDetail", "handleFault: ${fault.message}")
                }
            })
    }

    private fun updateGoalInBackendless() {
        Log.d("GoalDetailActivity", "updateGoalInBackendless: ${binding.checkBoxCheckGoal.isChecked}")

        val goalToUpdate = Goal(
            goal = binding.editTextGoal.text.toString(),
            purpose = binding.editTextReasonForGoal.text.toString(),
            goalCompleted = binding.checkBoxCheckGoal.isChecked,
            tasks = binding.editTextNumberOfTasks.text.toString().toInt(),
            ownerId = goal.ownerId,
            objectId = goal.objectId
        )

        Log.d("LoanDetailActivity", "${goalToUpdate.goalCompleted}")
        Backendless.Data.of(Goal::class.java).save(goalToUpdate, object: AsyncCallback<Goal?>{
            override fun handleResponse(response: Goal?) {
                Log.d("GoalAndHabitDetailActivity", "handleResponse: ${response?.goal} updated")
                goal = goalToUpdate
                toggleEditable()
                if(isCreatingNewGoal){
                    finish()
                }
            }

            override fun handleFault(fault: BackendlessFault?) {
                Log.d("GoalAndHabitDetailActivity", "handleFault: ${fault?.message}")
            }
        })
    }

    private fun toggleEditable() {
        if(!goalIsEditable){
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

