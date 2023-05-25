package com.example.goalandhabitreinforcer

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault

class GoalAndHabitAdapter(private val dataSet: MutableList<Goal>, private val context: Activity):
RecyclerView.Adapter<GoalAndHabitAdapter.ViewHolder>() {

    companion object{
        const val TAG = "GoalAndHabitAdapter"
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textViewGoal: TextView
        val textViewPositiveHabit: TextView
        val progressButton: Button
        val textViewTasksComplete: TextView
        val textViewTasksNeeded: TextView
        val constraintLayoutGoalListLayout: ConstraintLayout

        init{
            textViewGoal = view.findViewById(R.id.textView_Goal)
            textViewPositiveHabit = view.findViewById(R.id.textView_positiveHabit)
            progressButton = view.findViewById(R.id.button_addProgress)
            textViewTasksComplete = view.findViewById(R.id.textView_goalDone)
            textViewTasksNeeded = view.findViewById(R.id.textView_goalNeeded)
            constraintLayoutGoalListLayout = view.findViewById(R.id.layout_itemLoan)
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_goal, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int){
        var clicked: Int = 0
        val context = viewHolder.constraintLayoutGoalListLayout.context
        viewHolder.textViewGoal.text = dataSet[position].goal
        viewHolder.textViewPositiveHabit.text = dataSet[position].purpose
        viewHolder.textViewTasksNeeded.text = dataSet[position].tasks.toString()
        viewHolder.textViewTasksComplete.text = clicked.toString()

        viewHolder.progressButton.setOnClickListener {
            clicked++
            if(clicked == dataSet[position].tasks){
                deleteFromBackendless(position)
            }
            else{
                viewHolder.textViewTasksComplete.text = clicked.toString()
            }

        }
        viewHolder.constraintLayoutGoalListLayout.setOnClickListener {
            when (context) {
                is GoalAndHabitListActivity -> context.onGoalItemClicked(dataSet[position])
                else -> throw RuntimeException("Unreachable") // TODO: handle this more elegantly
            }
        }


    }

    private fun deleteFromBackendless(position: Int) {
        Log.d(TAG, "deleteFromBackendless: Deleting ${dataSet[position]}")
        Backendless.Data.of(Goal::class.java).remove(dataSet[position], object :
            AsyncCallback<Long> {
            override fun handleResponse(response: Long?) {
                Log.d(TAG, "handleResponse: Deleted.")
                dataSet.removeAt(position)
                notifyItemRemoved(position)
            }

            override fun handleFault(fault: BackendlessFault?) {
                Log.d(TAG, "handleFault: Couldn't Delete")
                Log.d(TAG, "handleFault: $fault")
            }

        })
    }

    override fun getItemCount() = dataSet.size
}