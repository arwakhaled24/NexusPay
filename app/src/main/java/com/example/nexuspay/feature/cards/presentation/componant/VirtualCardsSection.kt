package com.example.nexuspay.feature.cards.presentation.componant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexuspay.feature.cards.presentation.model.VirtualCardData
@Composable
fun VirtualCardsSection(
    modifier: Modifier = Modifier,
    cards: List<VirtualCardData>,
) {
    val pagerState = rememberPagerState(pageCount = { cards.size })

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Active Assets",
                color = Color(0xFFE1E2E7),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFFADC6FF).copy(alpha = 0.10f))
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "See All",
                    color = Color(0xFFADC6FF),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
        ) { page ->
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                VirtualCard(
                    card = cards[page],
                    modifier = Modifier
                        .width(331.5.dp)
                        .height(209.dp),
                )
            }
        }

        CarouselIndicators(
            count = cards.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        )
    }
}



// @Preview(showBackground = true, backgroundColor = 0xFF12131A)
// @Composable
// private fun VirtualCardsSectionPreview() {
//     NexusPayTheme {
//         VirtualCardsSection(
//             cards = listOf(
//                 VirtualCardData(
//                     cardholderName = "Ahmed Al-Rashid",
//                     cardNumber     = "4111111111118842",
//                     expiryDate     = "08/27",
//                     network        = CardNetwork.MASTERCARD,
//                 ),
//                 VirtualCardData(
//                     cardholderName = "Sara Mohamed",
//                     cardNumber     = "5500000000001092",
//                     expiryDate     = "03/26",
//                     network        = CardNetwork.VISA,
//                 ),
//                 VirtualCardData(
//                     cardholderName = "Khalid Hassan",
//                     cardNumber     = "4000000000005678",
//                     expiryDate     = "11/28",
//                     network        = CardNetwork.VISA,
//                 ),
//             ),
//             modifier = Modifier.padding(vertical = 16.dp),
//         )
//     }
// }
