package com.yury.lebowski.ui.about


import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.yury.lebowski.R


class AboutFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.findItem(R.id.settings_item)?.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            activity?.onBackPressed()
            return false
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activity?.setTitle(R.string.about)
    }

    override fun onDetach() {
        super.onDetach()
        activity?.setTitle(R.string.settings)
    }

    companion object {
        @JvmStatic
        fun newInstance() = AboutFragment()
    }
}
