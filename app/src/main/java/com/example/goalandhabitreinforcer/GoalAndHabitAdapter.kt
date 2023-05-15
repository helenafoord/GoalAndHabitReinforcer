package com.example.goalandhabitreinforcer

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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

        init{
            textViewGoal = view.findViewById(R.id.textView_Goal)
            textViewPositiveHabit = view.findViewById(R.id.textView_positiveHabit)
            progressButton = view.findViewById(R.id.button_addProgress)
            textViewTasksComplete = view.findViewById(R.id.textView_goalDone)
            textViewTasksNeeded = view.findViewById(R.id.textView_goalNeeded)
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_goal, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int){
        var clicked: Int = 0

        viewHolder.textViewGoal.text = dataSet[position].positiveHabit
        viewHolder.textViewPositiveHabit.text = dataSet[position].positiveHabit
        viewHolder.textViewTasksNeeded.text = dataSet[position].goal.toString()
        viewHolder.textViewTasksComplete.text = clicked.toString()

        viewHolder.progressButton.setOnClickListener {
            clicked++
            if(clicked == dataSet[position].goal){
                deleteFromList(position)
            }
            else{
                viewHolder.textViewTasksComplete.text = clicked.toString()
            }
        }

    }

    private fun deleteFromList(position: Int) {
        dataSet.removeAt(position)
    }

    override fun getItemCount() = dataSet.size
}