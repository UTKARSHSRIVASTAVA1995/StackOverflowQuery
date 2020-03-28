package com.devtides.stackoverflowquery.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devtides.stackoverflowquery.model.DummyDataProvider
import com.devtides.stackoverflowquery.model.Question

class QuestionsViewModel : ViewModel() {

    val questionsResponse = MutableLiveData<List<Question>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun getQuestions() {
        questionsResponse.value = DummyDataProvider.getDummyData(30)
        loading.value = false
        error.value = null
    }

    private fun onError(message: String) {
        error.value = message
        loading.value = false
    }
}