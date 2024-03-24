package xd.jg.custom_figures.presentation.main_screen.components

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import io.github.sceneview.Scene
import io.github.sceneview.animation.Transition.animateRotation
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberCameraNode
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNode
import io.github.sceneview.rememberNodes
import xd.jg.custom_figures.presentation.main_screen.MainScreenViewModel
import xd.jg.custom_figures.utils.Resource
import java.io.File
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

@Composable
fun FullModelViewer(fraction: Float, mainScreenViewModel: MainScreenViewModel = hiltViewModel()) {
    val downloadState by mainScreenViewModel.downloadStateFigure.collectAsState()

    val context = LocalContext.current

    Row(verticalAlignment = Alignment.Top, modifier = Modifier.fillMaxHeight()) {
        Box(modifier = Modifier.fillMaxHeight(fraction)) {
            val engine = rememberEngine()
            val modelLoader = rememberModelLoader(engine)

            val cameraNode = rememberCameraNode(engine).apply {
                position = Position(x = 0.0f, y = 0.5f, z = 1.25f)
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

            var bodyNode: ModelNode

            var hairNode: ModelNode

            var eyesNode: ModelNode

            Scene(
                engine = engine,
                modelLoader = modelLoader,
                cameraNode = cameraNode,
                childNodes = rememberNodes {
                    add(centerNode)
                },

                onFrame = {
                    centerNode.rotation = cameraRotation
                    cameraNode.lookAt(Position(x = 0.0f, y = 0.5f, z = 0.0f))
                },
                onViewUpdated = {
                    Log.d("CRINGE_AAAAA", "XD")
                    when (downloadState.status) {
                        Resource.Status.SUCCESS -> {
                            this.clearChildNodes()

                            hairNode = ModelNode(
                                modelInstance = modelLoader.createModelInstance(
                                    file = File(mainScreenViewModel.mainScreenUIState.value.hair)
                                ),
                                scaleToUnits = 0.125f,
                                centerOrigin = Position(x = 0.0f, y = 3.25f, z = 0.0f)
                            )

                            eyesNode = ModelNode(
                                modelInstance = modelLoader.createModelInstance(
                                    file = File(mainScreenViewModel.mainScreenUIState.value.eyes)
                                ),
                                scaleToUnits = 0.0665f,
                                centerOrigin = Position(x = 0.0f, y = 7.25f, z = 0.0f)
                            )

                            bodyNode = ModelNode(
                                modelInstance = modelLoader.createModelInstance(
                                    file = File(mainScreenViewModel.mainScreenUIState.value.body)
                                ),
                                scaleToUnits = 1.0f,
                                centerOrigin = Position(x = 0.0f, y = 0.25f, z = 0f)
                            )



                            this.addChildNodes(listOf(centerNode, hairNode, eyesNode, bodyNode))

                            Log.d("CRINGE_SUCCESS", "XD")
                            // mainScreenViewModel.changeState()

                        }
                        Resource.Status.LOADING -> {

                        }
                        Resource.Status.ERROR -> {
                            Toast.makeText(context, "${downloadState.message}" , Toast.LENGTH_SHORT).show()
                            Log.d("CRINGE_ERROR", "XD")
                        }
                    }
                }
            )
        }
    }
}

