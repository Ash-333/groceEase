package com.ashish.groceease.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ashish.groceease.model.Address
import com.ashish.groceease.viewModel.AddressViewModel

@Composable
fun AddressItem(
    address: Address,
    navController: NavHostController,
    addressViewModel: AddressViewModel,
    selectedAddressId: String?,
    onAddressSelected: (String) -> Unit
) {
    val isChecked = address._id == selectedAddressId
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable {
                navController.navigate("editAddress/${address._id}")
            },
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RadioButton(
                    selected = isChecked,
                    onClick = {
                        onAddressSelected(address._id!!)
                        addressViewModel.updateAddress(address._id,Address(address.phoneNumber,address.city,address.fullName,address.addressLine1,address.addressLine2,address._id,address.userId,true))
                    }
                )

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = address.fullName!!)
                Icon(imageVector = Icons.Outlined.Edit, contentDescription = "delete")
            }
            Text(text = address.phoneNumber!!)
            Text(text = address.addressLine1!!)
            Text(text = address.addressLine2!!)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = " ")
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "edit")
            }
        }
    }
}
