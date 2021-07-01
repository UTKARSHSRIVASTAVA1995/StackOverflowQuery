package com.devtides.stackoverflowquery.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devtides.stackoverflowquery.R
import com.devtides.stackoverflowquery.model.Questions
import com.devtides.stackoverflowquery.model.convertTitle
import com.devtides.stackoverflowquery.model.getDate
import com.devtides.stackoverflowquery.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.list_error
import kotlinx.android.synthetic.main.activity_detail.loading_view
import kotlinx.android.synthetic.main.activity_main.*
import java.security.AccessControlContext

class DetailActivity : AppCompatActivity() {

    companion object {

        private const val PARAM_QUESTIONS = "param_questions"

        fun getIntent(context: Context, questions: Questions) =
            Intent(context, DetailActivity::class.java).putExtra(PARAM_QUESTIONS, questions)
    }

    var questions: Questions? = null

    private val viewModel: DetailViewModel by viewModels()

    private val answersAdapter = AnswersAdapter(arrayListOf())
    private val lm = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        questions = intent.extras?.getParcelable(PARAM_QUESTIONS)

        if (questions == null) {
            finish()
        }
        populateUI()

        answers_list.apply {

            layoutManager = lm
            adapter = answersAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        val childCount = answersAdapter.itemCount
                        val lastPosition = lm.findLastCompletelyVisibleItemPosition()
                        if (childCount - 1 == lastPosition && loading_view.visibility == View.GONE) {
                            loading_view.visibility = View.VISIBLE
                            //viewModel.getNextPage(questions!!.)
                        }
                    }
                }
            })
        }
        observeViewModel()
        ///viewModel.getNextPage(questions!!.)
    }

    private fun observeViewModel() {
        viewModel.answersResponse.observe(this, Observer { items ->
            items?.let {
                answers_list.visibility = View.VISIBLE
                answersAdapter.addAnswers(it)
            }

        })
        viewModel.error.observe(this, Observer { errMsg ->
            list_error.visibility = if (errMsg == null) View.GONE else View.VISIBLE
            list_error.text = errMsg
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

    fun populateUI() {
        question_score.text = questions!!.score
        question_date.text = getDate(questions!!.date)
        question_title.text = convertTitle(questions!!.title)
    }
}


