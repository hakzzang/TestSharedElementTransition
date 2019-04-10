package hbs.com.shared_element_transitions


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.transition.ChangeBounds
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_empty_a.*

class EmptyAFragment : Fragment() {
    companion object {
        fun newInstance(): EmptyAFragment {
            val args: Bundle = Bundle()
            val fragment = EmptyAFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = ChangeBounds()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_empty_a, container, false)
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