package com.xacalet.moobies

import android.app.Application
import com.xacalet.moobies.di.AppComponent
import com.xacalet.moobies.di.DaggerAppComponent

class MoobiesApplication: Application() {

    val appComponent: AppComponent = DaggerAppComponent.create()
}
