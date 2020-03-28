package com.devtides.stackoverflowquery.model

object DummyDataProvider {
    fun getDummyData(count: Int): List<Question> {
        val list = arrayListOf<Question>()
        for (i in 1..count) {
            list.add(Question("Title$i"))
        }
        return list
    }
}