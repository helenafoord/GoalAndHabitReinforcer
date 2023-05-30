package com.example.goalandhabitreinforcer

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.example.goalandhabitreinforcer.R

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

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        var clicked: Int = 0
        val context = viewHolder.constraintLayoutGoalListLayout.context
        viewHolder.textViewGoal.text = dataSet[position].goal
        viewHolder.textViewPositiveHabit.text = dataSet[position].purpose
        viewHolder.textViewTasksNeeded.text = dataSet[position].tasks.toString()


        viewHolder.progressButton.setOnClickListener {
            clicked++
            dataSet[position].tasksCompleted = clicked
            viewHolder.textViewTasksComplete.text = dataSet[position].tasksCompleted.toString()
            if (dataSet[position].tasksCompleted >= dataSet[position].tasks) {
                viewHolder.textViewGoal.setTextColor(Color.LTGRAY)
                viewHolder.textViewTasksNeeded.setTextColor(Color.LTGRAY)
                viewHolder.textViewPositiveHabit.setTextColor(Color.LTGRAY)
                viewHolder.textViewTasksComplete.setTextColor(Color.LTGRAY)
            } else {
                viewHolder.textViewGoal.setTextColor(Color.BLACK)
                viewHolder.textViewTasksNeeded.setTextColor(Color.BLACK)
                viewHolder.textViewPositiveHabit.setTextColor(Color.BLACK)
                viewHolder.textViewTasksComplete.setTextColor(Color.BLACK)
            }
            viewHolder.constraintLayoutGoalListLayout.isLongClickable = true

            viewHolder.constraintLayoutGoalListLayout.setOnClickListener {
                val popMenu = PopupMenu(context, viewHolder.textViewGoal)
                popMenu.inflate(R.menu.menu_goal_list)
                popMenu.setOnMenuItemClickListener {
                    when(it.itemId) {
                        R.id.menu_loanList_delete -> {
                            deleteFromBackendless(position)
                            dataSet.removeAt(position)
                            notifyItemRemoved(position)
                            true
                        }
                        else -> true
                    }
                }
                popMenu.show()
                true
            }
            viewHolder.constraintLayoutGoalListLayout.setOnClickListener {
                when (context) {
                    is GoalAndHabitListActivity -> context.onGoalItemClicked(dataSet[position])
                    else -> throw RuntimeException("Unreachable")
                }
            }
        }
    }
        private fun deleteFromBackendless(position: Int) {
            Log.d("GoalAdapter", "deleteFromBackendless: Trying to delete ${dataSet[position]}")
            Backendless.Data.of(Goal::class.java).remove(dataSet[position], object : AsyncCallback<Long> {
                override fun handleResponse(response: Long?) {
                    Log.d(TAG, "handleResponse: Deleted.")
                }
                override fun handleFault(fault: BackendlessFault) {
                    // an error has occurred, the error code can be retrieved with fault.getCode()
                    Log.d("LoanAdapter", "handleFault: ${fault.message}")
                }
            })
        }


        override fun getItemCount() = dataSet.size

}

