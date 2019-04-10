package hbs.com.shared_element_transitions

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_a.*

class AActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)
        title = "Activity to Activity"
        iv_android_icon.setOnClickListener {
            val intent = Intent(this, BActivity::class.java)
            val options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(
                    this@AActivity, iv_android_icon,
                    ViewCompat.getTransitionName(iv_android_icon)!!
                )
            startActivity(intent, options.toBundle())
        }
    }
}