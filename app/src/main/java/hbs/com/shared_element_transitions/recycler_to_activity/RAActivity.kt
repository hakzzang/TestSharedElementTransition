package hbs.com.shared_element_transitions.recycler_to_activity

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import hbs.com.shared_element_transitions.R
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_a.*
import kotlinx.android.synthetic.main.activity_gallery.*
import kotlinx.android.synthetic.main.activity_rv.*

interface GalleryClickListener {
    fun onGalleyItemClick(view: View, galleryMetaData: RAActivity.GalleryMetaData) {}
}

class RAActivity : AppCompatActivity(), GalleryClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rv)

        title = "RecyclerView to Activity"

        val verticalLinearLayoutManager = LinearLayoutManager(this)
        verticalLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        val dummyArrayList: MutableList<GalleryMetaData> = mutableListOf<GalleryMetaData>(
            GalleryMetaData(
                R.mipmap.ic_launcher,
                "0번 데이터"
            ),
            GalleryMetaData(
                R.mipmap.ic_launcher_round,
                "1번 데이터"
            ),
            GalleryMetaData(
                R.mipmap.ic_launcher,
                "2번 데이터"
            ),
            GalleryMetaData(
                R.mipmap.ic_launcher_round,
                "3번 데이터"
            ),
            GalleryMetaData(
                R.mipmap.ic_launcher,
                "4번 데이터"
            )
        )

        val rvTransitionAdapter =
            RVTransitionAdapter(dummyArrayList, this)
        rv_transtion.apply {
            layoutManager = verticalLinearLayoutManager
            adapter = rvTransitionAdapter
        }

    }

    override fun onGalleyItemClick(view: View, galleryMetaData: GalleryMetaData) {
        val intent = Intent(this, GalleyActivity::class.java)
        intent.putExtra("gallery", galleryMetaData)

        val options = ActivityOptionsCompat
            .makeSceneTransitionAnimation(
                this@RAActivity, view,
                ViewCompat.getTransitionName(view)!!
            )

        startActivity(intent, options.toBundle())
    }

    @Parcelize
    data class GalleryMetaData(val drawable: Int, val content: String) : Parcelable
}