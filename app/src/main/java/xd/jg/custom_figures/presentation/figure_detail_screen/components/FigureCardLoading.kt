package xd.jg.custom_figures.presentation.figure_detail_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.CustomPrimaryContainer
import xd.jg.custom_figures.utils.Constants
import xd.jg.custom_figures.utils.Constants.ROUNDED

@Composable
fun FigureCardLoading() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = CustomPrimary,
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .border(1.dp, CustomPrimary, RoundedCornerShape(Constants.ROUNDED.dp)),
            shape = RoundedCornerShape(Constants.ROUNDED.dp),
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .fillMaxWidth()
                    .border(1.dp, CustomPrimary, RoundedCornerShape(Constants.ROUNDED.dp)),
                shape = RoundedCornerShape(Constants.ROUNDED.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(strokeCap = StrokeCap.Round)
                }
            }
            Spacer(Modifier.size(5.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().shimmer()
            ) {
                Box(modifier = Modifier.fillMaxWidth(0.6f).height(24.dp).clip(RoundedCornerShape(ROUNDED.dp)).background(CustomPrimaryContainer))
            }
            Spacer(Modifier.size(5.dp))
            Divider()
            Spacer(Modifier.size(5.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().shimmer()
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(0.9f).height(120.dp)
                        .clip(RoundedCornerShape(ROUNDED.dp)).background(CustomPrimaryContainer)

                )
            }
            Spacer(Modifier.size(5.dp))
            Divider()
            Spacer(Modifier.size(5.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().shimmer()
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(0.9f).height(140.dp)
                        .clip(RoundedCornerShape(ROUNDED.dp)).background(CustomPrimaryContainer)
                )
            }

        }
    }

}

@Composable
@Preview
fun FigureCardLoadingPreview() {
    FigureCardLoading()
}