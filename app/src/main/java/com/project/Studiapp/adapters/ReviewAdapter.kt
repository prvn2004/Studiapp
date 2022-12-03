package com.project.Studiapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.Studiapp.R
import com.project.Studiapp.databinding.QuestionListItemBinding
import com.project.Studiapp.datafiles.Question

class ReviewAdapter(
    val context: Context, val question: Question
) :

    RecyclerView.Adapter<ReviewAdapter.MyViewHolder>() { //class  which will take prameter(list of strings)
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


        if (options[position] == question.userAnswer && question.userAnswer == question.Answer) {
            holder.optionView.setBackgroundResource(R.drawable.green_background)
        }else if (options[position] == question.userAnswer && question.userAnswer != question.Answer){
            holder.optionView.setBackgroundResource(R.drawable.red_background)
        }
        else {
            holder.optionView.setBackgroundColor(R.drawable.white_background)
        }


    }

    override fun getItemCount(): Int {  // this function is counting the size of list
        return options.size // returning the size of list
    }
}