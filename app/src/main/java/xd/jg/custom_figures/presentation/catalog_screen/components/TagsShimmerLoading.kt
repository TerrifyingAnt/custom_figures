package xd.jg.custom_figures.presentation.catalog_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import xd.jg.custom_figures.ui.theme.CustomPrimaryContainer
import xd.jg.custom_figures.utils.Constants.ROUNDED

@Composable
fun TagShimmerLoading() {
    Surface(
        shape = RoundedCornerShape(ROUNDED.dp),
        modifier = Modifier
            .width(85.dp)
            .shimmer(),
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(CustomPrimaryContainer)
        )
    }
}


@Composable
fun TagsShimmerLoading() {
    Spacer(modifier = Modifier.size(5.dp))
    Row() {
        Spacer(modifier = Modifier.size(10.dp))
        TagShimmerLoading()
        Spacer(modifier = Modifier.size(10.dp))
        TagShimmerLoading()
        Spacer(modifier = Modifier.size(10.dp))
        TagShimmerLoading()
        Spacer(modifier = Modifier.size(10.dp))
        TagShimmerLoading()
        Spacer(modifier = Modifier.size(10.dp))
    }
}
@Composable
@Preview
fun TagsShimmerLoadingPreview() {
    TagsShimmerLoading()
}