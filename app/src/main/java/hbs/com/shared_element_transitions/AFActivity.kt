package hbs.com.shared_element_transitions

import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_af.*

class AFActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_af)
        title = "Activity to Fragment"
        createAFragment()
    }

    private fun createAFragment() {
        supportFragmentManager
            .beginTransaction()
            .addSharedElement(iv_android_icon, ViewCompat.getTransitionName(iv_android_icon)!!)
            .addToBackStack("icon_android")
            .add(R.id.fl_container, EmptyAFragment.newInstance())
            .commit()
    }
}