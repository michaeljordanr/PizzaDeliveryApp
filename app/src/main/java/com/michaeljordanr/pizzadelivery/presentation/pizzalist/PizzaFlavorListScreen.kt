package com.michaeljordanr.pizzadelivery.presentation.pizzalist

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.michaeljordanr.pizzaapp.R
import com.michaeljordanr.pizzadelivery.common.currency
import com.michaeljordanr.pizzadelivery.presentation.confirmation.ConfirmationInfo
import com.michaeljordanr.pizzadelivery.presentation.destinations.PizzaOrderConfirmationScreenDestination
import com.michaeljordanr.pizzadelivery.presentation.pizzalist.components.PizzaListItem
import com.michaeljordanr.pizzadelivery.presentation.ui.theme.LocalDim
import com.michaeljordanr.pizzadelivery.presentation.ui.theme.LocalSpacing
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun PizzaListScreen(
    navigator: DestinationsNavigator,
    viewModel: PizzaFlavorListViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val pizzas = viewModel.pizzas

    var orderEnabled by remember { mutableStateOf(false) }
    orderEnabled = pizzas.any { it.isSelected }

    var totalPrice by remember { mutableStateOf(0.0) }
    totalPrice = viewModel.getPizzaPrice(viewModel.getSelectedItems())


    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        val context = LocalContext.current

        Text(
            text = context.getString(R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(top = LocalSpacing.current.mediumSpacing)
        )

        Image(
            modifier = Modifier
                .padding(horizontal = LocalDim.current.logoWidth, vertical = LocalDim.current.logoHeight),
            painter = painterResource(id = R.drawable.pizza),
            contentDescription = context.getString(R.string.image_logo_description)
        )

        LazyColumn(Modifier.weight(weight = 1f)) {
            itemsIndexed(pizzas) { position, _ ->
                PizzaListItem(pizzaItems = pizzas, position = position) {
                    if (pizzas[position].isSelected.not() && viewModel.getSelectedItems().size == 2) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.max_flavors_error_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.toggleSelection(position)
                        orderEnabled = pizzas.any { it.isSelected }
                        totalPrice = viewModel.getPizzaPrice(viewModel.getSelectedItems())
                    }
                }
            }
        }

        if (viewModel.getSelectedItems().any()) {
            Text(
                text = if (viewModel.isTwoFlavorsSelected())
                    context.getString(
                        R.string.two_flavor_pizza_label,
                        viewModel.getFirstFlavor()?.name, viewModel.getSecondFlavor()?.name
                    )
                else context.getString(
                    R.string.one_flavor_pizza_label,
                    viewModel.getFirstFlavor()?.name
                ),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(top = LocalSpacing.current.mediumSpacing)
            )
        }

        Text(
            text = context.getString(R.string.total_price_label, totalPrice.currency()),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(top = LocalSpacing.current.mediumSpacing)
        )



        Button(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LocalSpacing.current.mediumSpacing, vertical = LocalSpacing.current.largeSpacing),
            enabled = orderEnabled,
            colors = if (orderEnabled.not()) ButtonDefaults.buttonColors(containerColor = Color.DarkGray) else ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            onClick = {
                if (viewModel.validateMaxFlavorSelected()) {
                    val order = viewModel.getSelectedItems()
                    val message = if (viewModel.getSelectedItems().size > 1) {
                        context.getString(
                            R.string.two_flavor_pizza_message,
                            order[0].name,
                            order[1].name
                        )
                    } else {
                        context.getString(R.string.one_flavor_pizza_message, order[0].name)
                    }
                    val confirmationInfo =
                        ConfirmationInfo(message = message, total = viewModel.getPizzaPrice(order))
                    navigator.navigate(PizzaOrderConfirmationScreenDestination(info = confirmationInfo))
                }
            }) {
            Text(context.getString(R.string.order_label))
        }
    }
    if (state.error.isNotBlank()) {
        Text(
            text = state.error,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.mediumSpacing)
        )
    }
    if (state.isLoading) {
        CircularProgressIndicator()
    }

}