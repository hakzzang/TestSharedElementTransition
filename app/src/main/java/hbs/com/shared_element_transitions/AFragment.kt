package hbs.com.shared_element_transitions

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_a.*

class AFragment : Fragment() {
    companion object {
        fun newInstance(): AFragment {
            val args: Bundle = Bundle()
            val fragment = AFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iv_android_icon.setOnClickListener {
            fragmentManager?.let {
                it.beginTransaction()
                    .addSharedElement(iv_android_icon, ViewCompat.getTransitionName(iv_android_icon)!!)
                    .addToBackStack("icon_android")
                    .replace(R.id.fl_container, BFragment.newInstance())
                    .commit()
            }
        }
    }
}