package com.project.insurancesurveyorexam.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.project.insurancesurveyorexam.R
import com.project.insurancesurveyorexam.databinding.QuestionListItemBinding
import com.project.insurancesurveyorexam.datafiles.Question


class QuestionAdapter(
    val context: Context, val question: Question
) :

    RecyclerView.Adapter<QuestionAdapter.MyViewHolder>() { //class  which will take prameter(list of strings)
    // ------------------------------------------------------------------------------------------------------------------------------------------
    private var options: List<String> = listOf(
        question.option1,
        question.option2,
        question.option3,
        question.option4,
        question.option5
    )
    private lateinit var binding: QuestionListItemBinding


    inner class MyViewHolder(
        ItemViewBinding: QuestionListItemBinding,
    ) :
        RecyclerView.ViewHolder(ItemViewBinding.root) {

        var optionView = ItemViewBinding.option1

    }
    //-------------------------------------------------------------------------------------------------------------------------------------------------

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {  //function take parameter()

        binding =
            QuestionListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(
            binding
        ) //returning MyViewHolder class with a view inside it
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        holder.optionView.text = options[position]


        holder.itemView.setOnClickListener {
            question.userAnswer = options[position]
            notifyDataSetChanged()
        }

        if (question.userAnswer == options[position]) {
            holder.optionView.setBackgroundResource(R.drawable.green_background)
        } else {
            holder.optionView.setBackgroundColor(R.drawable.white_background)
        }


    }

    override fun getItemCount(): Int {  // this function is counting the size of list
        return options.size // returning the size of list
    }
}