package com.example.goalandhabitreinforcer

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GoalAndHabitAdapter(private val dataSet: MutableList<GoalData>, private val context: Activity):
RecyclerView.Adapter<GoalAndHabitAdapter.ViewHolder>() {

    companion object{
        const val TAG = "GoalAndHabitAdapter"
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val textViewGoal: TextView
        val textViewCompleted: TextView
        val progressButton: Button

        init{
            textViewGoal = view.findViewById(R.id.textView_Goal)
            textViewCompleted = view.findViewById(R.id.textView_hitGoal)
            progressButton = view.findViewById(R.id.button_addProgress)
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_goal, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int){

    }

    override fun getItemCount() = dataSet.size
}