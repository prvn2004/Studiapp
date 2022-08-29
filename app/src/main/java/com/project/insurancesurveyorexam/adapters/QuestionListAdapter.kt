package com.project.insurancesurveyorexam.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.project.insurancesurveyorexam.Activities.EnotesActivity
import com.project.insurancesurveyorexam.Activities.MockTestActivity
import com.project.insurancesurveyorexam.Fragments.EnotesOverview
import com.project.insurancesurveyorexam.Fragments.QuestionFragment
import com.project.insurancesurveyorexam.R
import com.project.insurancesurveyorexam.databinding.EnotesNumbersListItemBinding
import com.project.insurancesurveyorexam.databinding.QuestionListItemBinding
import com.project.insurancesurveyorexam.databinding.QuestionNumberListItemBinding
import com.project.insurancesurveyorexam.datafiles.MockTestNumbersDataFile
import com.project.insurancesurveyorexam.datafiles.QuestionListDataFile

class QuestionListAdapter(
    private val LinkList: ArrayList<QuestionListDataFile>, val index: Int, val quizData: String?
) :

    RecyclerView.Adapter<QuestionListAdapter.MyViewHolder>() { //class  which will take prameter(list of strings)
// ------------------------------------------------------------------------------------------------------------------------------------------


    private lateinit var binding: QuestionNumberListItemBinding

    //-------------------------------------------------------------------------------------------------------------------------------------------------

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        binding =
            QuestionNumberListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return MyViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val Link = LinkList[position]
        holder.friction(Link, position, LinkList)

        val quiz =
            Gson().fromJson<MockTestNumbersDataFile>(quizData, MockTestNumbersDataFile::class.java)

        holder.indexView.setOnClickListener {
            fragmentJump(it.context, position)
        }

        if (position == index) {
            holder.indexView.setBackgroundResource(R.drawable.complete_rounded_blue_background)
        }

    }

    private fun fragmentJump(context: Context, indexNo: Int) {
        val mFragment = QuestionFragment()
        switchContentForList(R.id.container_mocktest, mFragment, context, indexNo)
    }

    private fun switchContentForList(id: Int, fragment: Fragment, context: Context, indexNo: Int) {
        if (context is MockTestActivity) {
            val MockTestActivity = context as MockTestActivity
            val frag: Fragment = fragment
            MockTestActivity.switchContentForList(id, frag, indexNo)
        }
    }

    override fun getItemCount(): Int {
        return LinkList.size
    }

    class MyViewHolder(
        ItemViewBinding: QuestionNumberListItemBinding,
    ) :
        RecyclerView.ViewHolder(ItemViewBinding.root) {

        private val binding = ItemViewBinding
        var indexView = binding.textView
        fun friction(
            Link: QuestionListDataFile,
            position: Int,
            list: ArrayList<QuestionListDataFile>
        ) {

            val context = itemView.getContext()

            binding.textView.text = Link.getChapterName().toString()

        }

    }
}
