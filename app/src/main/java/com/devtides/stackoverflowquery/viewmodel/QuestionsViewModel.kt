package com.devtides.stackoverflowquery.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devtides.stackoverflowquery.model.Questions
import com.devtides.stackoverflowquery.model.ResponseWrapper
import com.devtides.stackoverflowquery.model.StackOverFlowService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionsViewModel : ViewModel() {

    val questionsResponse = MutableLiveData<List<Questions>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    var page = 0

    fun getNextPage() {
        page++
        getQuestions()
    }

    fun getFirstPage() {
        page = 1
        getQuestions()
    }

    private fun getQuestions() {
        StackOverFlowService.api.getQuestions(page)
            .enqueue(object : Callback<ResponseWrapper<Questions>> {
                override fun onResponse(
                    call: Call<ResponseWrapper<Questions>>,
                    response: Response<ResponseWrapper<Questions>>
                ) {
                    if (response.isSuccessful) {
                        val questions = response.body()
                        questions?.let {
                            questionsResponse.value = questions.items
                            loading.value = false
                            error.value = null
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseWrapper<Questions>>, t: Throwable) {
                    onError(t.localizedMessage)
                    loading.value = false
                    error.value = null
                }
            })
    }

    private fun onError(message: String) {
        error.value = message
        loading.value = false
    }
}