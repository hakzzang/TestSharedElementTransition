package hbs.com.shared_element_transitions

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class FFActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ff)
        title = "Fragment to Fragment"
        createAFragment()
    }

    private fun createAFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fl_container, AFragment.newInstance())
            .commit()
    }
}