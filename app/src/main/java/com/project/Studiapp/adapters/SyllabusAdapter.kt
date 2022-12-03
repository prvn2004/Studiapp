package com.project.Studiapp.adapters

import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.Studiapp.R
import com.project.Studiapp.databinding.SyllabusListItemBinding
import com.project.Studiapp.datafiles.SyllabusDataFile

class SyllabusAdapter  (
    private  val LinkList: ArrayList<SyllabusDataFile>
) :

    RecyclerView.Adapter<SyllabusAdapter.MyViewHolder>() { //class  which will take prameter(list of strings)
// ------------------------------------------------------------------------------------------------------------------------------------------


    private lateinit var binding: SyllabusListItemBinding

    //-------------------------------------------------------------------------------------------------------------------------------------------------

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {  //function take parameter()


        binding =
            SyllabusListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

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

        val title = Link.getChapterName().toString()
        val content  = Link.getChapterContent().toString()

        binding.ChapterButton.setOnClickListener {
            val dialog = Dialog(it.context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.popup_window)
            val title_field = dialog.findViewById(R.id.Heading) as TextView
            val content_field = dialog.findViewById(R.id.Content) as TextView
            title_field.text = title
            content_field.text = content
            dialog.show()
        }

    }

    override fun getItemCount(): Int {  // this function is counting the size of list
        return LinkList.size // returning the size of list
    }

    class MyViewHolder(
        ItemViewBinding: SyllabusListItemBinding,
    ) :
        RecyclerView.ViewHolder(ItemViewBinding.root) {

        private val binding = ItemViewBinding
        fun friction(Link: SyllabusDataFile, position: Int, list: ArrayList<SyllabusDataFile>) {

            val context = itemView.getContext()
            binding.EnotesChapterName.text = Link.getChapterName()

        }

    }
}