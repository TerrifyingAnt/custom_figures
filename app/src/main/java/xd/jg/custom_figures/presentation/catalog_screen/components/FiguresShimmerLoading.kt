package xd.jg.custom_figures.presentation.catalog_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import xd.jg.custom_figures.ui.theme.CustomPrimary
import xd.jg.custom_figures.ui.theme.CustomPrimaryContainer
import xd.jg.custom_figures.utils.Constants

@Composable
fun FigureShimmerLoading() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = CustomPrimary,
        ),
        modifier = Modifier
            .height(230.dp)
            .width(150.dp)
            .border(1.dp, CustomPrimary, RoundedCornerShape(Constants.ROUNDED.dp)),
        shape = RoundedCornerShape(Constants.ROUNDED.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp)
                    .border(1.dp, Color.White, RoundedCornerShape(Constants.ROUNDED.dp)),
                shape = RoundedCornerShape(Constants.ROUNDED.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(
                        strokeCap = StrokeCap.Round
                    )
                }
            }
            Spacer(Modifier.size(5.dp))
            Surface(shape = RoundedCornerShape(Constants.ROUNDED.dp),
                color = CustomPrimaryContainer,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(17.dp)
                    .shimmer()) {
            }

            Spacer(Modifier.size(5.dp))
            Surface(shape = RoundedCornerShape(Constants.ROUNDED.dp),
                color = CustomPrimaryContainer,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(12.dp)
                    .shimmer()) {
            }
            Spacer(Modifier.size(5.dp))
            Surface(shape = RoundedCornerShape(Constants.ROUNDED.dp),
                color = CustomPrimaryContainer,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(12.dp)
                    .shimmer()) {
            }
        }
    }
}

@Composable
fun FiguresShimmerLoading() {
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(25.dp),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        contentPadding =  PaddingValues(30.dp, 10.dp),
        modifier = Modifier.fillMaxSize()) {
        item(content = { FigureShimmerLoading() })
        item(content = { FigureShimmerLoading() })
        item(content = { FigureShimmerLoading() })
        item(content = { FigureShimmerLoading() })
        item(content = { FigureShimmerLoading() })
    }

}

@Composable
@Preview
fun FiguresShimmerLoadingPreview() {
    FiguresShimmerLoading()
}