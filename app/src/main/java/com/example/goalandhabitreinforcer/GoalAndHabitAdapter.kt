package com.example.goalandhabitreinforcer

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class GoalAndHabitAdapter(private val dataSet: MutableList<GoalAndHabitData>, private val context: Activity):
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
                deleteFromList(position)
            }
            else{
                viewHolder.textViewTasksComplete.text = clicked.toString()
            }
            viewHolder.constraintLayoutGoalListLayout.isLongClickable = true

            viewHolder.constraintLayoutGoalListLayout.setOnClickListener {
                when (context) {
                    is GoalAndHabitListActivity -> context.onGoalItemClicked(dataSet[position])
                    else -> throw RuntimeException("Unreachable")
                }
            }
        }

    }

    private fun deleteFromList(position: Int) {
        dataSet.removeAt(position)
    }

    override fun getItemCount() = dataSet.size
}