package hbs.com.shared_element_transitions

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ll_item_0.setOnClickListener {
            startActivity(Intent(this@MainActivity, AActivity::class.java))
        }
        ll_item_1.setOnClickListener {
            startActivity(Intent(this@MainActivity, FFActivity::class.java))
        }
        ll_item_2.setOnClickListener {
            startActivity(Intent(this@MainActivity, AFActivity::class.java))
        }
    }
}
