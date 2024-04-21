package xd.jg.custom_figures.presentation.figure_detail_screen.components

import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.filament.Skybox
import io.github.sceneview.Scene
import io.github.sceneview.animation.Transition.animateRotation
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberEnvironmentLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNode
import io.github.sceneview.rememberNodes
import xd.jg.custom_figures.R
import xd.jg.custom_figures.presentation.figure_detail_screen.FigureDetailViewModel
import xd.jg.custom_figures.ui.theme.unboundedBoldFont
import xd.jg.custom_figures.utils.Constants.ROUNDED
import java.io.File
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

@Composable
fun FigureCardViewer(viewModel: FigureDetailViewModel = hiltViewModel()) {
    Column(modifier = Modifier
        .fillMaxHeight(0.9f)
        .clip(RoundedCornerShape(ROUNDED.dp))
        .background(Color(0xFFFFFFFF)))
    {
        Box(modifier = Modifier.fillMaxHeight()) {
            val engine = rememberEngine()
            val modelLoader = rememberModelLoader(engine)
            val environmentLoader = rememberEnvironmentLoader(engine)

            val env = environmentLoader.createHDREnvironment(
                assetFileLocation = "environments/evening_field.hdr",
                createSkybox = false
            )

            val skybox = Skybox.Builder().build(engine)
            val color = Color(255, 255, 255, 255)
            skybox.setColor(color.red, color.green, color.blue, color.alpha)
            val indirectLight = env?.indirectLight
            indirectLight?.intensity = 50_000f
            val coolEnv = environmentLoader.createEnvironment(indirectLight, skybox)

            val currentModelPath = viewModel.figureDetailUIState.value.figureModel.data ?: return
            val model = ModelNode(
                modelInstance = modelLoader.createModelInstance(
                    file = File(currentModelPath)
                ),
                scaleToUnits = 1.0f
            )

            val cameraNode = rememberCameraNode(engine).apply {
                position = Position(x = 0.0f, y = 1.5f, z = 7f)
            }
            val centerNode = rememberNode(engine)
                .addChildNode(cameraNode)
            val cameraTransition = rememberInfiniteTransition(label = "CameraTransition")
            val cameraRotation by cameraTransition.animateRotation(
                initialValue = Rotation(y = 0.0f),
                targetValue = Rotation(y = 360.0f),
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 7.seconds.toInt(DurationUnit.MILLISECONDS))
                )
            )

            Scene(
                engine = engine,
                modelLoader = modelLoader,
                cameraNode = cameraNode,
                childNodes = rememberNodes {
                    add(centerNode)
                    add(model)
                },
                environment = coolEnv,

                onFrame = {
                    centerNode.rotation = cameraRotation
                    cameraNode.lookAt(Position(x = 0f, y = 1.5f, z = 0f))
                },
                onViewUpdated = {
                    when {
                        viewModel.figureDetailUIState.value.clearScene.value -> {
                            clearChildNodes()
                            clearAnimation()
                            destroy()
                            viewModel.updateModelState()
                        }
                    }
                }
            )
        }
    }
    Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight()) {
        Row(
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                stringResource(R.string.turn_back_string),
                fontFamily = unboundedBoldFont,
                fontSize = 24.sp,
                color = White
            )
        }
    }
}
