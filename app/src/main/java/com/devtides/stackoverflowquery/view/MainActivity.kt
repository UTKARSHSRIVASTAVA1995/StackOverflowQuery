package com.devtides.stackoverflowquery.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devtides.stackoverflowquery.R
import com.devtides.stackoverflowquery.model.Questions
import com.devtides.stackoverflowquery.viewmodel.QuestionsViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), QuestionClickListener {

    private val questionsAdapter = QuestionsAdapter(arrayListOf(), this)
    private val viewModel: QuestionsViewModel by viewModels()
    private val lm = LinearLayoutManager(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        questions_list.apply {
            layoutManager = lm
            adapter = questionsAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        val childCount = questionsAdapter.itemCount
                        val lastPosition = lm.findLastCompletelyVisibleItemPosition()
                        if (childCount - 1 == lastPosition && loading_view.visibility == View.GONE) {
                            loading_view.visibility = View.VISIBLE
                            viewModel.getNextPage()
                        }
                    }
                }

            })
        }

        observeViewModel()

        viewModel.getNextPage()

        swipe_layout.setOnRefreshListener {
            questionsAdapter.clearQuestions()
            viewModel.getFirstPage()
            loading_view.visibility = View.VISIBLE
            questions_list.visibility = View.GONE
        }

    }


    private fun observeViewModel() {
        viewModel.questionsResponse.observe(this, Observer { items ->

            items?.let {
                questions_list.visibility = View.VISIBLE
                swipe_layout.isRefreshing = false
                questionsAdapter.addQuestions(it)
            }

        })

        viewModel.error.observe(this, Observer { errorMsg ->
            list_error.visibility = if (errorMsg == null) View.GONE else View.VISIBLE
            list_error.text = "Error\n$errorMsg"
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loading_view.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    list_error.visibility = View.GONE
                    questions_list.visibility = View.GONE
                }
            }
        })
    }

    override fun onQuestionClick(questions: Questions) {
        startActivity(DetailActivity.getIntent(this, questions))
    }
}
