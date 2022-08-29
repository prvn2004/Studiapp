package com.project.insurancesurveyorexam.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.insurancesurveyorexam.R
import com.project.insurancesurveyorexam.databinding.DashboardPerformanceListItemBinding
import com.project.insurancesurveyorexam.databinding.OtherPerformanceListItemBinding
import com.project.insurancesurveyorexam.datafiles.DashboardDataFile

class OthersPerformanceAdapter(
    private val LinkList: ArrayList<DashboardDataFile>
) :

    RecyclerView.Adapter<OthersPerformanceAdapter.MyViewHolder>() { //class  which will take prameter(list of strings)
// ------------------------------------------------------------------------------------------------------------------------------------------


    private lateinit var binding: OtherPerformanceListItemBinding

    //-------------------------------------------------------------------------------------------------------------------------------------------------

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {  //function take parameter()


        binding =
            OtherPerformanceListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

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

    }


    override fun getItemCount(): Int {  // this function is counting the size of list
        return LinkList.size // returning the size of list
    }

    class MyViewHolder(
        ItemViewBinding: OtherPerformanceListItemBinding,
    ) :
        RecyclerView.ViewHolder(ItemViewBinding.root) {

        private val binding = ItemViewBinding
        fun friction(Link: DashboardDataFile, position: Int, list: ArrayList<DashboardDataFile>) {

            val context = itemView.getContext()

            binding.name.text = Link.getPerson()
            binding.marks.text = "Marks - " + Link.gettestmarks()

        }
    }
}