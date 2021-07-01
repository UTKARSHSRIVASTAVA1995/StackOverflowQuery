package com.devtides.stackoverflowquery.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devtides.stackoverflowquery.model.Answers
import com.devtides.stackoverflowquery.model.ResponseWrapper
import com.devtides.stackoverflowquery.model.StackOverFlowService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    val answersResponse = MutableLiveData<List<Answers>>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    var questionsId = 0
    var page = 0

    fun getNextPage(questionId: Int?) {
        questionId?.let {
            this.questionsId = it
            page++
            getAnswers()
        }
    }

    private fun getAnswers() {
        StackOverFlowService.api.getAnswers(questionsId, page)
            .enqueue(object : Callback<ResponseWrapper<Answers>> {
                override fun onResponse(
                    call: Call<ResponseWrapper<Answers>>,
                    response: Response<ResponseWrapper<Answers>>
                ) {
                    val answers = response.body()
                    answers?.let {
                        answersResponse.value = it.items
                        loading.value = false
                        error.value = null
                    }
                }

                override fun onFailure(call: Call<ResponseWrapper<Answers>>, t: Throwable) {
                    onError(t.localizedMessage)
                }
            })
    }

    private fun onError(message: String) {
        error.value = message
        loading.value = false
    }

}