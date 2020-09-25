package com.xacalet.moobies.presentation.userrating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserRatingFragment : Fragment() {

    private val args: UserRatingFragmentArgs by navArgs()

    private val viewModel by viewModels<UserRatingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.setId(args.showId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        ComposeView(context = requireContext()).apply {
            setContent {
                viewModel.data.observeAsState(null).value.let { data ->
                    UserRatingScreen(data)
                }
            }
        }
}
