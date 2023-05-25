package com.example.goalandhabitreinforcer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import com.example.goalandhabitreinforcer.databinding.ActivityGoalAndHabitListBinding

class GoalAndHabitListActivity : AppCompatActivity() {

    companion object{
        const val CREATING_NEW_GOAL = "create new goal"
        const val TAG = "GoalListActivity"
        const val USERID = "UserId"
    }

    private lateinit var binding: ActivityGoalAndHabitListBinding
    private lateinit var adapter: GoalAndHabitAdapter
    private var goals = mutableListOf<GoalAndHabitData>()
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalAndHabitListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userId = intent?.getStringExtra(GoalAndHabitDetailActivity.EXTRA_GOAL).toString()

        retrieveAllData(userId)

        binding.fabLoanListCreateNewGoal.setOnClickListener {
            val goalDetailIntent = Intent(this@GoalAndHabitListActivity, GoalAndHabitDetailActivity::class.java).apply{
                putExtra(CREATING_NEW_GOAL, true)
            }
            startActivity(goalDetailIntent)
        }
    }

    private fun retrieveAllData(userId: String) {
        Log.d(TAG, "retrieveAllData: Retrieving Goals")
        val place = "ownerID = '$userId"
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.whereClause = place
        Backendless.Data.of(GoalAndHabitData::class.java).find(
            queryBuilder, object:AsyncCallback<List<GoalAndHabitData>> {
                override fun handleResponse(foundGoals: List<GoalAndHabitData>?) {
                    if (foundGoals != null) {
                        goals = foundGoals as MutableList<GoalAndHabitData>
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
        )
    }


    fun onGoalItemClicked(goal: GoalAndHabitData){
        val intent = Intent(this, GoalAndHabitDetailActivity::class.java)
        intent.putExtra(GoalAndHabitDetailActivity.EXTRA_GOAL, goal)
        startActivity(intent)
    }

}