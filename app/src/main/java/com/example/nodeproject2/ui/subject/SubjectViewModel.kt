package com.example.nodeproject2.ui.subject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nodeproject2.data.model.AddSubjectRequest
import com.example.nodeproject2.data.model.Subject
import com.example.nodeproject2.di.CheckuApplication
import com.example.nodeproject2.repository.SubjectRepository
import com.example.nodeproject2.widget.utils.MutableSingleLiveData
import com.example.nodeproject2.widget.utils.SingleLiveData
import com.skydoves.sandwich.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubjectViewModel @Inject constructor(
    private val subjectRepository: SubjectRepository
) : ViewModel() {

    private val userId = CheckuApplication.prefs.getUserId()

    private val _subjectList = MutableLiveData<MutableList<Subject>>()
    val subjectList: LiveData<MutableList<Subject>> = _subjectList

    private val _subjectErrorToastEvent = MutableSingleLiveData<Boolean>()
    val subjectErrorToastEvent: SingleLiveData<Boolean> = _subjectErrorToastEvent

    private val _subjectWaitEvent = MutableSingleLiveData<Boolean>()
    val subjectWaitEvent: SingleLiveData<Boolean> = _subjectWaitEvent

    private val _refreshed = MutableLiveData<Boolean>()
    val refreshed: LiveData<Boolean> = _refreshed

    fun getInitData() {
        _subjectWaitEvent.setValue(true)

        viewModelScope.launch {
            // TODO enum 관리

            val department = "공과대학_컴퓨터공학부"
            val grade = "THIRD"
            val type = "OPTIONAL"

            val mySubjectsResponse = subjectRepository.getSubjects(department, grade, type)
            if (mySubjectsResponse is ApiResponse.Success) {
                _subjectList.value = MutableList(mySubjectsResponse.data.size) { mySubjectsResponse.data[it] }

            }else {
                _subjectErrorToastEvent.setValue(true)
            }
//            _policySize.value = myInterestPolicyResponse.data.data.size

        }
    }

    fun refreshData() {
        _refreshed.value = true
        getInitData()
        _refreshed.value = false
    }

    fun addSubject(subjectNumber: String) {
        viewModelScope.launch {
            subjectRepository.addSubject(AddSubjectRequest(userId, subjectNumber))
        }
    }

//    private val _policyList = MutableLiveData<List<PolicyContent?>>()
//    val policyList: LiveData<List<PolicyContent?>> = _policyList
//
//    private val _policySize = MutableLveData<Int>()
//    val policySize: LiveData<Int> = _poilicySize
//
//    private val _spinnerPosition = MutableLiveData<Int>()
//    val spinnerPosition: LiveData<Int> = _spinnerPosition
//
//    private val _bottomSheetShowEvent = MutableSingleLiveData<Boolean>()
//    val bottomSheetShowEvent: SingleLiveData<Boolean> = _bottomSheetShowEvent
//
//    private val _bottomSheetDismissEvent = MutableSingleLiveData<Boolean>()
//    val bottomSheetDismissEvent: SingleLiveData<Boolean> = _bottomSheetDismissEvent
//
//    private val _keyBoardSearchEvent = MutableSingleLiveData<Boolean>()
//    val keyBoardSearchEvent: SingleLiveData<Boolean> = _keyBoardSearchEvent
//
//    private val _goToFilterFragmentEvent = MutableSingleLiveData<Boolean>()
//    val goToFilterFragmentEvent: SingleLiveData<Boolean> = _goToFilterFragmentEvent
//
//    private val _goToDetailFragmentEvent = MutableSingleLiveData<Int>()
//    val goToDetailFragmentEvent: SingleLiveData<Int> = _goToDetailFragmentEvent
//
//    private val _updateRecyclerViewItemEvent = MutableSingleLiveData<Pair<Int, PolicyContent>>()
//    val updateRecyclerViewItemEvent: SingleLiveData<Pair<Int, PolicyContent>> = _updateRecyclerViewItemEvent
//
//    private val _showBookmarkCountMaxToastEvent = MutableSingleLiveData<Boolean>()
//    val showBookmarkCountMaxToastEvent: SingleLiveData<Boolean> = _showBookmarkCountMaxToastEvent
//
//    val searchInputText = MutableLiveData<String>()
//    var searchText = ""
//
//    lateinit var regionIds: List<Int>
//    lateinit var regionTextList: List<String>
//    lateinit var categoryIds: List<Int>
//
//    fun goToDetailFragment(id: Int) {
//        _goToDetailFragmentEvent.setValue(id)
//    }
//
//    fun goToFilterFragment() {
//        _goToFilterFragmentEvent.setValue(true)
//    }
//
//    fun showBottomSheetDialog() {
//        _bottomSheetShowEvent.setValue(true)
//    }
//
//    fun spinnerItemClick(position: Int) {
//        _spinnerPosition.value = position
//        _bottomSheetDismissEvent.setValue(true)
//        changePolicy()
//    }
//
//    fun getInitData() {
//        _policySize.value = 0
//        viewModelScope.launch {
//            val myRegionResponse = myInfoRepository.getMyRegion()
//            val myCategoryResponse = myInfoRepository.getMyCategory()
//            if(myRegionResponse !is ApiResponse.Success || myCategoryResponse !is ApiResponse.Success)   return@launch
//            regionIds = List(myRegionResponse.data.data.size) { myRegionResponse.data.data[it].region.id ?: 0 }
//            initRegions(myRegionResponse.data.data)
//            categoryIds = List(myCategoryResponse.data.data.size) { myCategoryResponse.data.data[it].category.id }
//            changePolicy()
//        }
//    }
//
//    private fun initRegions(data: List<MyRegion>) {
//        val tempRegions = mutableListOf<String>()
//        for(element in data) {
//            if(element.region.parent == null) tempRegions.add("${element.region.name} 전체")
//            else tempRegions.add("${element.region.parent.name} ${element.region.name}")
//        }
//        for(idx in data.size until MAX_FILTER_REGION_COUNT)
//            tempRegions.add("")
//        regionTextList = tempRegions
//    }
//
//    fun addPolicy() {
//        if (paging.isLastPage || paging.page.value == 0) return
//
//        viewModelScope.launch {
//            val policyResponse = getPolicyResponse()
//            policyResponse.onSuccess {
//                paging.loadData(
//                    data.data.content.toMutableList(),
//                    data.data.last, _policyList,
//                    paging.addData()
//                )
//            }
//        }
//    }
//
//    fun clearPolicy() {
//        _policySize.value = 0
//        _policyList.value = listOf()
//    }
//
//    fun changePolicy() {
//        paging.resetPage()
//
//        if(::regionIds.isInitialized && ::regionTextList.isInitialized && ::categoryIds.isInitialized)
//        viewModelScope.launch {
//            val policyResponse = getPolicyResponse()
//            policyResponse.onSuccess {
//                paging.loadData(
//                    data.data.content.toMutableList(),
//                    data.data.last, _policyList,
//                    paging.changeData()
//                )
//                _policySize.value = data.data.totalElements
//            }
//        }
//    }
//
//    private suspend fun getPolicyResponse(): ApiResponse<PolicyResponse> {
//        return policyRepository.getPolicy(
//            title = searchText,
//            region = regionIds,
//            category = categoryIds,
//            page = paging.page.value ?: 0,
//            sort = listOf(SORT_POLICY[spinnerPosition.value ?: 0])
//        )
//    }
//
//    fun onEditTextSearchClick(actionId: Int): Boolean {
//        return if(actionId == EditorInfo.IME_ACTION_SEARCH) {
//            searchText = searchInputText.value ?: ""
//            changePolicy()
//            _keyBoardSearchEvent.setValue(true)
//            true
//        } else false
//    }
//
//    fun bookmarkClick(data: PolicyContent, position: Int) {
//        viewModelScope.launch {
//            if(!data.isInterest) {
//                val addInterestPolicyResponse = bookmarkRepository.addInterestPolicy(data.id)
//                addInterestPolicyResponse.onSuccess {
//                    data.isInterest = !data.isInterest
//                    _updateRecyclerViewItemEvent.setValue(Pair(position, data))
//                }.onError { if(statusCode.code == 400) _showBookmarkCountMaxToastEvent.setValue(true) }
//
//            } else {
//                val deleteInterestPolicyResponse = bookmarkRepository.deleteInterestPolicy(data.id)
//                deleteInterestPolicyResponse.onSuccess {
//                    data.isInterest = !data.isInterest
//                    _updateRecyclerViewItemEvent.setValue(Pair(position, data))
//                }
//            }
//        }
//    }
//
//    fun checkBookmarkChanged(id: Int, isBookmarked: Boolean) {
//        val position = policyList.value?.indexOfFirst { it?.id == id } ?: return
//        if(position == -1)   return
//        val data = policyList.value!![position]
//        data!!.isInterest = isBookmarked
//        _updateRecyclerViewItemEvent.setValue(Pair(position, data))
//    }
}