package com.example.goalandhabitreinforcer


import android.annotation.SuppressLint
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
    private var goals = mutableListOf<Goal>()
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalAndHabitListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userId = intent?.getStringExtra(LoginActivity.EXTRA_USERID).toString()
        Log.d(TAG, "userID: $userId")


        binding.fabLoanListCreateNewGoal.setOnClickListener {
            val goalDetailIntent = Intent(this@GoalAndHabitListActivity, GoalAndHabitDetailActivity::class.java).apply{
                putExtra(CREATING_NEW_GOAL, true)
                putExtra(LoginActivity.EXTRA_USERID, userId)
            }
            startActivity(goalDetailIntent)
        }
    }
    override fun onStart() {
        super.onStart()
        retrieveAllData(userId)
    }


    private fun retrieveAllData(userId: String) {
        Log.d(TAG, "retrieveAllData: Retrieving Goals for $userId")
        // Backendless.UserService.CurrentUser().userId
        val place = "ownerId = ${userId}"
        val queryBuilder = DataQueryBuilder.create()
        queryBuilder.whereClause = place
        Backendless.Data.of(Goal::class.java).find(
            object:AsyncCallback<List<Goal>> {
                @SuppressLint("NotifyDataSetChanged")
                override fun handleResponse(foundGoals: List<Goal>?) {
                    Log.d(TAG, "handleResponse: $foundGoals")
                    if (foundGoals != null) {
                        goals = foundGoals as MutableList<Goal>
                        adapter = GoalAndHabitAdapter(goals, this@GoalAndHabitListActivity)
                        binding.recyclerViewGoalListGoals.adapter = adapter
                        binding.recyclerViewGoalListGoals.layoutManager =
                            LinearLayoutManager(this@GoalAndHabitListActivity)
                        adapter.notifyDataSetChanged()
                        Log.d(TAG, "handleResponse: data set changed")
                    }
                }

                override fun handleFault(fault: BackendlessFault?) {
                    Log.d(TAG, "handleFault: $fault?.message")
                }
            }
        )
    }


    fun onGoalItemClicked(goal: Goal){
        val intent = Intent(this, GoalAndHabitDetailActivity::class.java)
        intent.putExtra(GoalAndHabitDetailActivity.EXTRA_GOAL, goal)
        startActivity(intent)
    }

}