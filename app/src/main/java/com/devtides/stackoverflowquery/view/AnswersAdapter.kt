package com.devtides.stackoverflowquery.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devtides.stackoverflowquery.R
import com.devtides.stackoverflowquery.model.Answers
import kotlinx.android.synthetic.main.answers_layout.view.*

class AnswersAdapter(val answers: ArrayList<Answers>) :
    RecyclerView.Adapter<AnswersAdapter.AdapterViewHolder>() {

    fun addAnswers(newAnswer: List<Answers>) {
        val currentLength = answers.size
        answers.addAll(newAnswer)
        notifyItemInserted(currentLength)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        AdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.answers_layout, parent, false)
        )

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.bind(answers[position])
    }

    override fun getItemCount() = answers.size

    class AdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.item_answers
        fun bind(answers: Answers) {
            title.text = answers.toString()
        }
    }
}