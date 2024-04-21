package xd.jg.custom_figures.presentation.main_screen.components

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import xd.jg.custom_figures.presentation.main_screen.MainScreenViewModel
import xd.jg.custom_figures.utils.Resource
import java.io.File
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit


@Composable
fun FullModelViewer(nullFraction: Float? = 1f, baseState: Boolean? = false, mainScreenViewModel: MainScreenViewModel = hiltViewModel()) {
    val fraction = nullFraction ?: 1f
    val downloadState by mainScreenViewModel.downloadStateFigure.collectAsState()
    var listOfPaths = ""
    if (baseState == true){
        listOfPaths = mainScreenViewModel.getPaths(LocalContext.current.applicationContext)
    }

    val skyBoxUserScreen by mainScreenViewModel.skyBoxUserScreen.collectAsState()

    val context = LocalContext.current

    Row(verticalAlignment = Alignment.Top, modifier = Modifier
        .fillMaxHeight()
        .background(Color(0xFFFFFFFF))) {
        Box(modifier = Modifier.fillMaxHeight(fraction)) {
            val engine = rememberEngine()
            val modelLoader = rememberModelLoader(engine)
            val environmentLoader = rememberEnvironmentLoader(engine)

            val env = environmentLoader.createHDREnvironment(
                assetFileLocation = "environments/evening_field.hdr",
                createSkybox = false
            )

            val skybox = Skybox.Builder().build(engine)
            val color = skyBoxUserScreen.data ?: Color(255, 255, 255, 255)
            skybox.setColor(color.red, color.green, color.blue, color.alpha)
            val indirectLight = env?.indirectLight
            indirectLight?.intensity = 50_000f
            val coolEnv = environmentLoader.createEnvironment(indirectLight, skybox)

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
                environment = coolEnv,

                onFrame = {
                    centerNode.rotation = cameraRotation
                    cameraNode.lookAt(Position(x = 0f, y = 1.5f, z = 0f))
                },
                onViewUpdated = {
                    if (baseState == true) {
                        this.clearChildNodes()
                        hairNode = ModelNode(
                            modelInstance = modelLoader.createModelInstance(
                                file = File("$listOfPaths/hair")
                            ),
                        )

                        eyesNode = ModelNode(
                            modelInstance = modelLoader.createModelInstance(
                                file = File("$listOfPaths/eye")
                            ),
                        )

                        bodyNode = ModelNode(
                            modelInstance = modelLoader.createModelInstance(
                                file = File("$listOfPaths/body")
                            ),
                        )
                        this.addChildNodes(listOf(centerNode, hairNode, eyesNode, bodyNode))
                    }
                    when (downloadState.status) {
                        Resource.Status.SUCCESS -> {
                            this.clearChildNodes()
                            hairNode = ModelNode(
                                modelInstance = modelLoader.createModelInstance(
                                    file = File(mainScreenViewModel.mainScreenUIState.value.hair)
                                ),
                            )

                            eyesNode = ModelNode(
                                modelInstance = modelLoader.createModelInstance(
                                    file = File(mainScreenViewModel.mainScreenUIState.value.eyes)
                                ),
                            )

                            bodyNode = ModelNode(
                                modelInstance = modelLoader.createModelInstance(
                                    file = File(mainScreenViewModel.mainScreenUIState.value.body)
                                ),
                            )
                            this.addChildNodes(listOf(centerNode, hairNode, eyesNode, bodyNode))
                        }
                        Resource.Status.LOADING -> {

                        }
                        Resource.Status.ERROR -> {
                            Toast.makeText(context, "${downloadState.message}" , Toast.LENGTH_SHORT).show()
                            Log.d("CRINGE_ERROR", "XD")
                        }
                    }

                    when (skyBoxUserScreen.status) {
                        Resource.Status.SUCCESS -> {
                            val newColor = skyBoxUserScreen.data ?: return@Scene
                            scene.skybox?.setColor(newColor.red, newColor.green, newColor.blue, newColor.alpha)

                        }
                        else -> {}
                    }
                }
            )
        }
    }
}

