package com.example.curious_u

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.curious_u.data.Query

class QueryFragment : Fragment() {
    private val choiceViewModel: ChoiceViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.quiz_fragment, container, false)
        val buttons = view.findViewById<LinearLayout>(R.id.choices)
        val query = requireArguments().getSerializable("query") as Query
        // Log.d("mytag", query.toString())
        view.findViewById<TextView>(R.id.question).text = query.question
        /*
        for(select in query.selects) {
            Log.d("mytag", select.toString())
            val btn = Button(activity)
            btn.text = select.text
            buttons.addView(btn)
        }
        */
        query.selects.forEachIndexed { index, select ->
            val btn = Button(activity)
            btn.text = select.text
            btn.setOnClickListener {
                choiceViewModel.setData(index)
            }
            buttons.addView(btn)
        }
        return view
    }

    companion object {
        fun newInstance(query : Query) : QueryFragment {
            val args = Bundle()
            args.putSerializable("query", query)
            val fragment = QueryFragment()
            fragment.arguments = args
            return fragment
        }
    }
}