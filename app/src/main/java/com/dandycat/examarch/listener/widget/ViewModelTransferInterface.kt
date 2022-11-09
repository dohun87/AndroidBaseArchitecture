package com.dandycat.examarch.listener.widget

/**
 * ListAdapter -> ViewModel로의 데이터 전달 Interface
 * 우선은 포지션 및 Data 기준으로 전달하게 한다
 * 차후 추가적으로 더 좋은 동작이 있으면 연구 후 적용한다.
 * @author dohun8832
 */
interface ViewModelTransferInterface {
    /**
     * 선택된 포지션을 전달하기 위한 Callback Method
     * @author dohun8832
     * @param position ListAdapter Position
     */
    fun clickPosition(position : Int)

    /**
     * 선택한 데이터를 전달하기 위한 Callback Method
     * @author dohun8832
     * @param data Any(GenericAdapter의 경우 Any를 기반으로 데이터 젇달 진행됨)
     */
    fun clickData(data : Any)
}