package com.project.insurancesurveyorexam.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.project.insurancesurveyorexam.R
import com.project.insurancesurveyorexam.databinding.DashboardPerformanceListItemBinding
import com.project.insurancesurveyorexam.datafiles.DashboardDataFile

class DashboardAdapter(
    private val LinkList: ArrayList<DashboardDataFile>
) :

    RecyclerView.Adapter<DashboardAdapter.MyViewHolder>() { //class  which will take prameter(list of strings)
// ------------------------------------------------------------------------------------------------------------------------------------------


    private lateinit var binding: DashboardPerformanceListItemBinding

    //-------------------------------------------------------------------------------------------------------------------------------------------------

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {  //function take parameter()


        binding =
            DashboardPerformanceListItemBinding.inflate(
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
        ItemViewBinding: DashboardPerformanceListItemBinding,
    ) :
        RecyclerView.ViewHolder(ItemViewBinding.root) {

        private val binding = ItemViewBinding
        fun friction(Link: DashboardDataFile, position: Int, list: ArrayList<DashboardDataFile>) {

            val context = itemView.getContext()
            binding.mocktestNumber.text = "Practice Test " + Link.gettestnumber().toString()
            binding.testDate.text = Link.gettestdate()
            binding.testMarksGot.text = Link.gettestmarks()
//            binding.testTimeEclapsed.text = Link.gettesttime()

            if (Link.gettestpassfail() == "PASS"){
                binding.colorLayout.setBackgroundResource(R.drawable.dashboard_green_background)
            }else{
                binding.colorLayout.setBackgroundResource(R.drawable.dashboard_red_background)
            }

        }
    }
}