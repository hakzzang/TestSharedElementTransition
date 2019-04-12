package hbs.com.shared_element_transitions.recycler_to_activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import hbs.com.shared_element_transitions.R
import kotlinx.android.synthetic.main.activity_gallery.*

class GalleyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        supportPostponeEnterTransition()
        val mBundle = intent.extras
        val galleyMetaData = mBundle.getParcelable<RAActivity.GalleryMetaData>("gallery")
        iv_title_image_galley.transitionName = galleyMetaData.content
        tv_title_image_galley.transitionName = galleyMetaData.content
        iv_title_image_galley.setImageResource(galleyMetaData.drawable)
        tv_title_image_galley.text = galleyMetaData.content
        supportStartPostponedEnterTransition()
    }
}