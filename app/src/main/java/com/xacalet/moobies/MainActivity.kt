package com.xacalet.moobies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xacalet.domain.usecase.GetGenresUseCase
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), CoroutineScope {

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main

    @Inject
    lateinit var getGenresUseCase: GetGenresUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_movie_list)
    }
}
