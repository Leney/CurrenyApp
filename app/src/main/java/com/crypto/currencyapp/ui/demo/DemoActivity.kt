package com.crypto.currencyapp.ui.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.crypto.currencyapp.domain.ListType
import com.crypto.currencyapp.ui.list.CurrencyListScreen
import com.crypto.currencyapp.ui.list.CurrencyListViewModel
import com.crypto.currencyapp.ui.theme.CurrencyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DemoActivity : ComponentActivity() {

    // 这个 ViewModel 只负责控制按钮逻辑，如插入/清除数据
    private val demoViewModel: DemoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // DemoApp 是我们的主 Composable
                    DemoApp(demoViewModel = demoViewModel)
                }
            }
        }
    }
}

@Composable
fun DemoApp(
    demoViewModel: DemoViewModel,
    // 我们直接在 Composable 中创建 CurrencyListViewModel
    currencyListViewModel: CurrencyListViewModel = viewModel()
) {
    // 使用 State 来保存当前要显示的列表类型
    var currentListType by remember { mutableStateOf(ListType.LIST_A) }

    // 当 currentListType 首次出现或发生变化时，这个 Effect 会被触发
    // 它会调用 ViewModel 加载相应的数据
    LaunchedEffect(currentListType) {
        currencyListViewModel.loadCurrencies(currentListType)
    }

    val currencyListUiState by currencyListViewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // 控制按钮区域
        DemoControlScreen(
            onClearData = {
                demoViewModel.clearData()
                // 清除数据后，重新加载当前列表以显示空状态
                currencyListViewModel.loadCurrencies(currentListType)
            },
            onInsertData = {
                demoViewModel.insertData()
                // 插入数据后，重新加载当前列表以显示新数据
                currencyListViewModel.loadCurrencies(currentListType)
            },
            onShowListA = { currentListType = ListType.LIST_A },
            onShowListB = { currentListType = ListType.LIST_B },
            onShowBoth = { currentListType = ListType.PURCHASABLE_BOTH }
        )

        // 货币列表显示区域
        // 直接调用 CurrencyListScreen，它现在是应用的一部分
        CurrencyListScreen(uiState = currencyListUiState)
    }
}

@Composable
fun DemoControlScreen(
    onClearData: () -> Unit,
    onInsertData: () -> Unit,
    onShowListA: () -> Unit,
    onShowListB: () -> Unit,
    onShowBoth: () -> Unit
) {
    // 这个函数的实现可以保持不变
    Column(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onInsertData) { Text("Insert Data") }
            Button(onClick = onClearData) { Text("Clear Data") }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onShowListA) { Text("Show List A") }
            Button(onClick = onShowListB) { Text("Show List B") }
        }
        Button(
            onClick = onShowBoth,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text("Show Purchasable in Both")
        }
    }
}