package com.example.lets_meet.model

data class Event(
    var date: String = "",
    var title: String ="",

    var starttime: String="",
    var endtime: String = "",
    var public: Boolean = false
)