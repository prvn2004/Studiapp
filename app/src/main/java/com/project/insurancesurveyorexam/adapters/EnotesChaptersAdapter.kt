package com.project.insurancesurveyorexam.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.project.insurancesurveyorexam.Activities.EnotesActivity
import com.project.insurancesurveyorexam.Fragments.EnotesOverview
import com.project.insurancesurveyorexam.R
import com.project.insurancesurveyorexam.databinding.EnotesNumbersListItemBinding
import com.project.insurancesurveyorexam.datafiles.EnotesNumberDataFile


class EnotesChaptersAdapter (
   private  val LinkList: ArrayList<EnotesNumberDataFile>
) :

    RecyclerView.Adapter<EnotesChaptersAdapter.MyViewHolder>() { //class  which will take prameter(list of strings)
// ------------------------------------------------------------------------------------------------------------------------------------------


    private lateinit var binding: EnotesNumbersListItemBinding

    //-------------------------------------------------------------------------------------------------------------------------------------------------

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {  //function take parameter()


        binding =
            EnotesNumbersListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

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
        binding.ChapterButton.setOnClickListener {
            fragmentJump(it.context, LinkList.get(position).getChapterName(), LinkList.get(position).getChapterContent())
        }

    }

    private fun fragmentJump(context: Context, Title: String, Content: String) {
       val  mFragment = EnotesOverview()
        switchContent(R.id.container_enotes, mFragment, context, Title, Content)
    }

    private fun switchContent(id: Int, fragment: Fragment, context: Context, Title: String, Content: String) {
        if (context is EnotesActivity) {
            val enotesActivity = context as EnotesActivity
            val frag: Fragment = fragment
            val args = Bundle()
            args.putString("YourKey", Title)
            args.putString("Content", Content)
            frag.setArguments(args)
            enotesActivity.switchContent(id, frag)
        }
    }

    override fun getItemCount(): Int {  // this function is counting the size of list
        return LinkList.size // returning the size of list
    }

   class MyViewHolder(
        ItemViewBinding: EnotesNumbersListItemBinding,
    ) :
        RecyclerView.ViewHolder(ItemViewBinding.root) {

        private val binding = ItemViewBinding
        fun friction(Link: EnotesNumberDataFile, position: Int, list: ArrayList<EnotesNumberDataFile>) {

            val context = itemView.getContext()
            binding.EnotesChapterName.text = Link.getChapterName()

        }

    }
}
