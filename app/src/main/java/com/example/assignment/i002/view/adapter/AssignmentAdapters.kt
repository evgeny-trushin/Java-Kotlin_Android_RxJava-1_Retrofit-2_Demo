package com.example.assignment.i002.view.adapter

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.assignment.helpers.AbstractPagerAdapter
import com.example.assignment.helpers.AbstractRecyclerViewAdapter
import com.example.assignment.i002.view.activity.AssignmentActivity
import com.example.assignment.i002.R
import com.example.assignment.i002.view.model.AssignmentItemViewModel
import com.example.assignment.i002.databinding.ItemBinding
import com.example.assignment.i002.databinding.SimpleFragmentBinding

class SimpleViewPager(
        manager: FragmentManager,
        fragmentNumbers: Int,
        bundle: Bundle?) : AbstractPagerAdapter<String>(manager, fragmentNumbers, bundle) {
    override fun putItemToBundle(bundle: Bundle, item: String): Bundle {
        val bundleClone: Bundle = bundle.clone() as Bundle
        bundleClone.putString(BUNDLE_KEY, item)
        return bundleClone
    }

    override fun getItemFromBundle(bundle: Bundle) = bundle.getString(BUNDLE_KEY, "0")
}

class SimpleRecyclerViewAdapter(context: Context?, items: MutableList<String>?) :
        AbstractRecyclerViewAdapter<String, RecyclerView.ViewHolder, Void>(context, items) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CardHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item, parent, false))
    }

    override fun bindItem(holder: RecyclerView.ViewHolder?, item: String, position: Int) {
        (holder as CardHolder).bind?.viewModel = AssignmentItemViewModel(item, { context, text ->
            run {
                val vm = (context as AssignmentActivity).mViewModel
                vm.text = text
                context.bindViewModel(vm)
            }
        })
    }
}

internal class CardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val bind: ItemBinding? = DataBindingUtil.bind(itemView)
}

class SimpleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val bind: SimpleFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.simple_fragment, container, false)
        bind.item?.viewModel = AssignmentItemViewModel(arguments!!.getString(AbstractPagerAdapter.BUNDLE_KEY), { context, text ->
            run {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
            }
        })
        return bind.root
    }

    companion object {
        fun newInstance(bundle: Bundle): SimpleFragment {
            val simpleFragment = SimpleFragment()
            simpleFragment.arguments = bundle
            return simpleFragment
        }
    }
}