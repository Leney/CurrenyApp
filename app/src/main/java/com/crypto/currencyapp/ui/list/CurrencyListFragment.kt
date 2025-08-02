//package com.crypto.currencyapp.ui.list
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.platform.ComposeView
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import com.crypto.currencyapp.domain.ListType
//import com.crypto.currencyapp.ui.theme.CurrencyAppTheme
//import dagger.hilt.android.AndroidEntryPoint
//
//@AndroidEntryPoint
//class CurrencyListFragment : Fragment() {
//
//    private val viewModel: CurrencyListViewModel by viewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        return ComposeView(requireContext()).apply {
//            setContent {
//                CurrencyAppTheme {
//                    val uiState by viewModel.uiState.collectAsState()
//                    CurrencyListScreen(uiState = uiState)
//                }
//            }
//        }
//    }
//
//    companion object {
//        fun newInstance(listType: ListType): CurrencyListFragment {
//            return CurrencyListFragment().apply {
//                arguments = Bundle().apply {
//                    putSerializable("listType", listType)
//                }
//            }
//        }
//    }
//}