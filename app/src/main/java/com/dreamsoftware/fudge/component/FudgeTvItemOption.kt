package com.dreamsoftware.fudge.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Card
import androidx.tv.material3.CardDefaults
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.RadioButton
import androidx.tv.material3.RadioButtonDefaults
import com.dreamsoftware.fudge.theme.FudgeTvTheme
import com.dreamsoftware.fudge.utils.EMPTY

@Composable
fun FudgeTvItemOption(
    modifier: Modifier = Modifier,
    title: String = String.EMPTY,
    description: String = String.EMPTY,
    price: String = String.EMPTY,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    with(MaterialTheme.colorScheme) {
        Card(
            modifier = modifier.fillMaxWidth(),
            colors = CardDefaults.colors(Color.Transparent),
            onClick = onClick,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 10.dp)
            ) {
                FudgeTvText(
                    type = FudgeTvTextTypeEnum.TITLE_MEDIUM,
                    titleText = title,
                    textColor = onSurface
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    FudgeTvText(
                        titleText = description,
                        type = FudgeTvTextTypeEnum.BODY_SMALL,
                        textColor = onSurface.copy(alpha = 0.8f)
                    )
                    FudgeTvText(
                        titleText = price,
                        type = FudgeTvTextTypeEnum.LABEL_LARGE,
                        textColor = onSurface
                    )
                    RadioButton(
                        modifier = Modifier.size(24.dp),
                        selected = isSelected,
                        onClick = { onClick() },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = secondary,
                            unselectedColor = border
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun SelectedSubscriptionOptionPreview() {
    FudgeTvTheme {
        FudgeTvItemOption(
            title = "1 Month Subscription",
            description = "Start your 7-day free trial then \$7.99 / month.\nSubscription continues until cancelled",
            price = "$7.99",
            isSelected = true,
        )
    }
}

@Preview
@Composable
private fun UnSelectedSubscriptionOptionPreview() {
    FudgeTvTheme {
        FudgeTvItemOption(
            title = "1 Month Subscription",
            description = "Start your 7-day free trial then \$7.99 / month.\nSubscription continues until cancelled",
            price = "$7.99",
            isSelected = false,
        )
    }
}