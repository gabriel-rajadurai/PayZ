package com.gabriel.payz.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gabriel.payz.R

@Composable
fun CompleteTransactionScreen(
    modifier: Modifier
) {

    val viewModel: TransactionsViewModel = hiltViewModel()

    val transactionInProgress = viewModel.transactionInProgress.collectAsStateWithLifecycle().value
    var amount by remember { mutableStateOf(TextFieldValue()) }

    Box(
        modifier = modifier,
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.weight(1f, true))

            Text(
                text = "Complete your transaction",
                style = LocalTextStyle.current.copy(fontSize = 24.sp),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.size(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = amount,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.End
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    amount = it
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.ic_rupee),
                        contentDescription = ""
                    )
                }
            )

            Spacer(modifier = Modifier.weight(1f, true))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    viewModel.completeTransaction()
                }
            ) {
                Text(
                    "Click to Pay",
                    style = LocalTextStyle.current.copy(
                        fontSize = 18.sp
                    )
                )
            }
        }

        if (transactionInProgress) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

}

@Composable
@Preview(showBackground = true)
private fun CompleteTransactionScreenPreview() {
    CompleteTransactionScreen(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    )
}