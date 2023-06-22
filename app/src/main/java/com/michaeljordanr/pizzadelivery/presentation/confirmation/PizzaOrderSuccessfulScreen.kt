package com.michaeljordanr.pizzadelivery.presentation.confirmation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.michaeljordanr.pizzaapp.R
import com.michaeljordanr.pizzadelivery.common.currency
import com.michaeljordanr.pizzadelivery.presentation.ui.theme.LocalDim
import com.michaeljordanr.pizzadelivery.presentation.ui.theme.LocalSpacing
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun PizzaOrderConfirmationScreen(
    info: ConfirmationInfo
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(LocalSpacing.current.mediumSpacing),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier
                .padding(horizontal = LocalDim.current.logoWidth, vertical = LocalDim.current.logoHeight),
            painter = painterResource(id = R.drawable.pizza),
            contentDescription = context.getString(R.string.image_logo_description)
        )


        Text(
            text = context.getString(R.string.confirmation_label),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(top = LocalSpacing.current.mediumSpacing)
        )
        Spacer(modifier = Modifier.height(LocalSpacing.current.largeSpacing))
        Text(
            text = context.getString(R.string.your_order_label),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(text = info.message, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(LocalSpacing.current.mediumSpacing))
        Text(
            text = context.getString(R.string.total_price_label, info.total.currency()),
            style = MaterialTheme.typography.titleLarge
        )
    }

}