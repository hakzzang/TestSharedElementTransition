package hbs.com.shared_element_transitions.recycler_to_activity

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hbs.com.shared_element_transitions.R
import kotlinx.android.synthetic.main.item_gallery.view.*

class RVTransitionAdapter(
    val arrayList: MutableList<RAActivity.GalleryMetaData>,
    val galleryClickListener: GalleryClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RVTransitionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_gallery,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val rvTransitionViewHolder = viewHolder as RVTransitionViewHolder
        rvTransitionViewHolder.view.iv_title_image_galley.setImageResource(arrayList[position].drawable)
        rvTransitionViewHolder.view.tv_content_gallery.text = arrayList[position].content
        ViewCompat.setTransitionName(
            rvTransitionViewHolder.view.cl_item_gallery,
            arrayList[position].content
        )

        rvTransitionViewHolder.view.cl_item_gallery.setOnClickListener {
            galleryClickListener.onGalleyItemClick(it, arrayList[position])
        }
    }

    class RVTransitionViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}