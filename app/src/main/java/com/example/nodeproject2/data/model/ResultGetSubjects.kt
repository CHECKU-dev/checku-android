package com.example.nodeproject2.data.model

import com.google.gson.annotations.SerializedName

data class ResultGetSubjects(

    @SerializedName("grade") val grade: Int,
    @SerializedName("professor") val professor: String,
    @SerializedName("subjectName") val subjectName: String,
    @SerializedName("numberOfPeople") val numberOfPeople: String,
    @SerializedName("remark") val remark: String,
    @SerializedName("subjectType") val subjectType: String,
    @SerializedName("department") val department: String,
    @SerializedName("subjectNumber") val subjectNumber: String


)
