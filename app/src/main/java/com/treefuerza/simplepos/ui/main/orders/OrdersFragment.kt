package com.treefuerza.simplepos.ui.main.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.treefuerza.simplepos.R
import kotlinx.android.synthetic.main.orders_fragment.*

//data class OrdersState(val user: Async<User> = Uninitialized): MvRxState

private const val ARG_OBJECT = "object"
class OrdersFragment : Fragment() {

    private lateinit var ordersPageAdapter: FragmentPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.orders_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ordersPageAdapter = object : FragmentPagerAdapter(requireFragmentManager()) {
            override fun getPageTitle(position: Int): CharSequence? {
                return "TAB ${position+1}"
            }

            override fun getItem(position: Int): Fragment {
                val fragment = DemoObjectFragment()
                fragment.arguments = Bundle().apply { putInt(ARG_OBJECT, position+1) }
                return fragment
            }

            override fun getCount(): Int = 2

        }
        pager.adapter = ordersPageAdapter
    }

    class DemoObjectFragment : Fragment() {

        override fun onCreateView(inflater: LayoutInflater,
                                  container: ViewGroup?,
                                  savedInstanceState: Bundle?): View {
            // The last two arguments ensure LayoutParams are inflated properly.
            val rootView = inflater.inflate(
                R.layout.products_fragment, container, false)
            arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
                val textView: TextView = rootView.findViewById(R.id.hello)
                textView.text = getInt(ARG_OBJECT).toString()
            }
            return rootView
        }
    }

}
