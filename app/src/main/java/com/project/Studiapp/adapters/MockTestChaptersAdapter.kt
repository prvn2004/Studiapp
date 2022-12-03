package com.project.Studiapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.project.Studiapp.Activities.MockTestActivity
import com.project.Studiapp.Fragments.MockTestOverview
import com.project.Studiapp.R
import com.project.Studiapp.databinding.MocktestsNumbersListItemBinding
import com.project.Studiapp.datafiles.MockTestNumbersDataFile

class MockTestChaptersAdapter (
    private  val LinkList: ArrayList<MockTestNumbersDataFile>
) :

    RecyclerView.Adapter<MockTestChaptersAdapter.MyViewHolder>() { //class  which will take prameter(list of strings)
// ------------------------------------------------------------------------------------------------------------------------------------------


    private lateinit var binding: MocktestsNumbersListItemBinding

    //-------------------------------------------------------------------------------------------------------------------------------------------------

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {  //function take parameter()


        binding =
            MocktestsNumbersListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(
            binding
        ) //returning MyViewHolder class with a view inside it
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val Link = LinkList[position]
        holder.friction(Link, position, LinkList)
        binding.mocktestButton.setOnClickListener {
            fragmentJump(it.context, LinkList.get(position).getTestNumber(), LinkList.get(position).getTestQuestionNumber(), LinkList.get(position).getTestDuration(), LinkList.get(position).getTestMark())
        }

    }

    private fun fragmentJump(context: Context, Title: String, TotalQuestion: String, TotalDuration: String, TotalMarks: String) {
        val  mFragment = MockTestOverview()
        switchContent(R.id.container_mocktest, mFragment, context, Title, TotalQuestion, TotalDuration, TotalMarks)
    }

    private fun switchContent(id: Int, fragment: Fragment, context: Context, Title: String, TotalQuestion: String, TotalDuration: String, TotalMarks: String) {
        if (context is MockTestActivity) {
            val MockTestActivity = context as MockTestActivity
            val frag: Fragment = fragment
            MockTestActivity.switchContent(id, frag, Title, TotalQuestion, TotalDuration, TotalMarks)
        }
    }

    override fun getItemCount(): Int {  // this function is counting the size of list
        return LinkList.size // returning the size of list
    }

    class MyViewHolder(
        ItemViewBinding: MocktestsNumbersListItemBinding,
    ) :
        RecyclerView.ViewHolder(ItemViewBinding.root) {

        private val binding = ItemViewBinding
        fun friction(Link: MockTestNumbersDataFile, position: Int, list: ArrayList<MockTestNumbersDataFile>) {

            val context = itemView.getContext()
            binding.mocktestNumber.text = "Practise Test " + Link.getTestNumber()
            binding.mocktestMarks.text = Link.getTestMark() + " Marks"

        }

    }
}