package com.finder.android.mbti.dataobj

import com.example.nodeproject2.data.model.Subject
import com.google.gson.annotations.SerializedName

data class SubjectListDto(
    @SerializedName("content") val contents: List<Subject>,
    @SerializedName("pageable") val pageable: Pageable,
    @SerializedName("last") val last : Boolean,
    @SerializedName("size") val size : Int
)

//data class Content(
//    @SerializedName("grade") val grade: String,
//    @SerializedName("professor") val professor: String,
//    @SerializedName("subjectName") val subjectName: String,
//    @SerializedName("emptySeat") val emptySeat: String,
//    @SerializedName("numberOfPeople") val numberOfPeople: String,
//    @SerializedName("remark") val remark: String,
//    @SerializedName("subjectType") val subjectType: String,
//    @SerializedName("department") val department: String,
//    @SerializedName("subjectNumber") val subjectNumber: String,
//    @SerializedName("timeAndPlace") val timeAndPlace: String,
//)

data class Pageable(
    @SerializedName("offset") val offset: Int,
    @SerializedName("pageSize") val pageSize: Int,
    @SerializedName("pageNumber") val pageNumber: Int,
    @SerializedName("paged") val paged: Boolean,
    @SerializedName("unpaged") val unPaged: Boolean
)