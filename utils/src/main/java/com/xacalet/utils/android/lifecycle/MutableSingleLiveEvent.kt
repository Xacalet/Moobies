package com.xacalet.utils.android.lifecycle

class MutableSingleLiveEvent<T> : SingleLiveEvent<T>() {

    public override fun postValue(value: T) {
        super.postValue(value)
    }
}
