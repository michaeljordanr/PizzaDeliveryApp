package com.michaeljordanr.pizzadelivery.presentation.pizzalist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.michaeljordanr.pizzaapp.R
import com.michaeljordanr.pizzadelivery.common.currency
import com.michaeljordanr.pizzadelivery.domain.model.Pizza

@Composable
fun PizzaListItem(pizzaItems: List<Pizza>, position: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "${pizzaItems[position].name} - ${pizzaItems[position].price.currency()}")
        if (pizzaItems[position].isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = LocalContext.current.getString(R.string.flavor_selected_label),
                tint = Color.Green,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}