package com.devtides.stackoverflowquery.view

import com.devtides.stackoverflowquery.model.Questions

interface QuestionClickListener {

    fun onQuestionClick(questions: Questions)
}