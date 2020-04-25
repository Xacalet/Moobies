package com.xacalet.moobies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as MoobiesApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie_list)
    }
}
