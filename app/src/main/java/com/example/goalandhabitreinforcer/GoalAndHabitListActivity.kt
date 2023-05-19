package com.example.goalandhabitreinforcer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.goalandhabitreinforcer.databinding.ActivityGoalAndHabitListBinding

class GoalAndHabitListActivity : AppCompatActivity() {

    companion object{
        const val CREATING_NEW_GOAL = "create new goal"
        const val TAG = "GoalListActivity"
    }

    private lateinit var binding: ActivityGoalAndHabitListBinding
    private lateinit var goal: String
    private lateinit var adapter: GoalAndHabitAdapter
    private var goals = mutableListOf<GoalAndHabitData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalAndHabitListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        goal = intent?.getStringExtra(GoalAndHabitDetailActivity.EXTRA_GOAL).toString()

        retrieveAllData(goal)

        binding.fabLoanListCreateNewGoal.setOnClickListener {
            val goalDetailIntent = Intent(this@GoalAndHabitListActivity, GoalAndHabitDetailActivity::class.java).apply{
                putExtra(CREATING_NEW_GOAL, true)
            }
            startActivity(goalDetailIntent)
        }
    }

    private fun retrieveAllData(goal: String?) {
        object:AsyncCallback<List<GoalAndHabitData>>{
            override fun handleResponse(response: List<GoalAndHabitData>?) {
                if(response != null){
                    goals = response as MutableList<GoalAndHabitData>
                    adapter = GoalAndHabitAdapter(goals, this@GoalAndHabitListActivity)
                    binding.recyclerViewGoalListGoals.adapter = adapter
                    binding.recyclerViewGoalListGoals.layoutManager =
                        LinearLayoutManager(this@GoalAndHabitListActivity)
                    Log.d(TAG, "handleResponse: data set changed")
                }
            }

            override fun handleFault(fault: BackendlessFault?) {
                Log.d(TAG, "handleFault: $fault?.message")
            }

        }
    }


    fun onGoalItemClicked(goal: GoalAndHabitData){
        val intent = Intent(this, GoalAndHabitDetailActivity::class.java)
        intent.putExtra(GoalAndHabitDetailActivity.EXTRA_GOAL, goal)
        startActivity(intent)
    }



}