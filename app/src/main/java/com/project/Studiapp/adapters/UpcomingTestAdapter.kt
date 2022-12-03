package com.project.Studiapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.project.Studiapp.Activities.LiveTestActivity
import com.project.Studiapp.Fragments.UpcomingTestOverviewFragment
import com.project.Studiapp.R
import com.project.Studiapp.databinding.UpcomingTestListItemBinding
import com.project.Studiapp.datafiles.UpcomingTestDataFile
import java.text.SimpleDateFormat
import java.util.*

class UpcomingTestAdapter(
    private val LinkList: ArrayList<UpcomingTestDataFile>
) :

    RecyclerView.Adapter<UpcomingTestAdapter.MyViewHolder>() { //class  which will take prameter(list of strings)
// ------------------------------------------------------------------------------------------------------------------------------------------


    private lateinit var binding: UpcomingTestListItemBinding

    //-------------------------------------------------------------------------------------------------------------------------------------------------

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {  //function take parameter()


        binding =
            UpcomingTestListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

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
        val getdate = LinkList.get(position).getTestDate()
        val getsetno = LinkList.get(position).getTestSetNo()
        val timestamp = LinkList.get(position).getTimeStump()
        val format1 = SimpleDateFormat("MM-dd-yyyy")
        val format2 = SimpleDateFormat("dd MMM yyyy")
        val date: Date = format1.parse(getdate)
        val actualdate = format2.format(date)

        val Title = "$actualdate" + " Exam Set " + "$getsetno"
        val id = LinkList.get(position).getTestNumber()
        binding.mocktestButton.setOnClickListener {
            fragmentJump(
                it.context,
                Title,
                LinkList.get(position).getTestQuestionNumber(),
                LinkList.get(position).getTestDuration(),
                LinkList.get(position).getTestMark(),
                Link.getTestDayTime(),
                id,
                getsetno,
                getdate,
                timestamp
            )
        }

    }

    private fun fragmentJump(
        context: Context,
        Title: String,
        TotalQuestion: String,
        TotalDuration: String,
        TotalMarks: String,
        TestTime: String,
        ID: String,
        setno: String,
        testdate: String,
        timestamp: Long
    ) {
        val mFragment = UpcomingTestOverviewFragment()
        switchContent(
            R.id.live_container,
            mFragment,
            context,
            Title,
            TotalQuestion,
            TotalDuration,
            TotalMarks,
            TestTime,
            ID,
            setno,
            testdate,
            timestamp
        )
    }

    private fun switchContent(
        id: Int,
        fragment: Fragment,
        context: Context,
        Title: String,
        TotalQuestion: String,
        TotalDuration: String,
        TotalMarks: String,
        TestTime: String,
        ID: String,
        setno: String,
        testdate: String,
        timestamp: Long
    ) {
        if (context is LiveTestActivity) {
            val LiveTestActivity = context as LiveTestActivity
            val frag: Fragment = fragment
            LiveTestActivity.switchContent(
                id,
                frag,
                Title,
                TotalQuestion,
                TotalDuration,
                TotalMarks,
                TestTime,
                ID,
                setno,
                testdate,
                timestamp
            )
        }
    }

    override fun getItemCount(): Int {  // this function is counting the size of list
        return LinkList.size // returning the size of list
    }

    class MyViewHolder(
        ItemViewBinding: UpcomingTestListItemBinding,
    ) :
        RecyclerView.ViewHolder(ItemViewBinding.root) {

        private val binding = ItemViewBinding
        fun friction(
            Link: UpcomingTestDataFile,
            position: Int,
            list: ArrayList<UpcomingTestDataFile>
        ) {

            val context = itemView.getContext()

            val format1 = SimpleDateFormat("MM-dd-yyyy")
            val format2 = SimpleDateFormat("dd MMM yyyy")
            val date: Date = format1.parse(Link.getTestDate())
            val actualdate = format2.format(date)

            binding.mocktestDate.text = actualdate
            binding.mocktestDuration.text = Link.getTestDuration() + " MIN."
            binding.mocktestExamdate.text = actualdate
            binding.mocktestMarks.text = Link.getTestMark() + "Marks"
            binding.mocktestSetno.text = "Exam Set " + Link.getTestSetNo()
            binding.mocktestTime.text = Link.getTestDayTime().toString()

        }

    }
}
