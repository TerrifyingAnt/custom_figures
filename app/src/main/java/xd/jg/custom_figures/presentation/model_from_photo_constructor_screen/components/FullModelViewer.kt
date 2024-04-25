package xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.components

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
import xd.jg.custom_figures.presentation.model_from_photo_constructor_screen.ModelFromPhotoConstructorViewModel
import xd.jg.custom_figures.utils.Resource
import java.io.File
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit


@Composable
fun FullModelViewer(
    nullFraction: Float? = 1f,
    viewModel: ModelFromPhotoConstructorViewModel = hiltViewModel()) {
    val fraction = nullFraction ?: 1f

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
            val color = viewModel.modelFromPhotoConstructorUIState.value.skyBoxColor.value
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
                    when (viewModel.modelFromPhotoConstructorUIState.value.isModelRotating.value) {
                        true -> {
                            centerNode.rotation = cameraRotation
                            cameraNode.lookAt(Position(x = 0f, y = 1.5f, z = 0f))
                        }
                        false -> {
                            cameraNode.lookAt(Position(x = 0f, y = 1.5f, z = 0f))
                        }
                    }
                },
                onViewUpdated = {
                    when (viewModel.modelFromPhotoConstructorUIState.value.figure.status) {
                        Resource.Status.SUCCESS -> {
                            if (viewModel.modelFromPhotoConstructorUIState.value.hair.status == Resource.Status.SUCCESS &&
                                viewModel.modelFromPhotoConstructorUIState.value.eyes.status == Resource.Status.SUCCESS &&
                                viewModel.modelFromPhotoConstructorUIState.value.body.status == Resource.Status.SUCCESS)
                            {
                                val nonNullHair =
                                    viewModel.modelFromPhotoConstructorUIState.value.hair.data
                                        ?: return@Scene
                                val nonNullEyes =
                                    viewModel.modelFromPhotoConstructorUIState.value.eyes.data
                                        ?: return@Scene
                                val nonNullBody =
                                    viewModel.modelFromPhotoConstructorUIState.value.body.data
                                        ?: return@Scene
                                this.clearChildNodes()
                                hairNode = ModelNode(
                                    modelInstance = modelLoader.createModelInstance(
                                        file = File(nonNullHair)
                                    ),
                                )

                                eyesNode = ModelNode(
                                    modelInstance = modelLoader.createModelInstance(
                                        file = File(nonNullEyes)
                                    ),
                                )

                                bodyNode = ModelNode(
                                    modelInstance = modelLoader.createModelInstance(
                                        file = File(nonNullBody)
                                    ),
                                )
                                this.addChildNodes(listOf(centerNode, hairNode, eyesNode, bodyNode))
                            }
                        }
                        Resource.Status.LOADING -> {
                        }
                        Resource.Status.ERROR -> {
                            Toast.makeText(context, "${viewModel.modelFromPhotoConstructorUIState.value.figure.message}" , Toast.LENGTH_SHORT).show()
                            Log.d("CRINGE_ERROR", "XD")
                        }
                    }

                    val newColor = viewModel.modelFromPhotoConstructorUIState.value.skyBoxColor.value
                    scene.skybox?.setColor(newColor.red, newColor.green, newColor.blue, newColor.alpha)

                    when {
                        viewModel.modelFromPhotoConstructorUIState.value.deleteModel.value -> {
                            viewModel.clearScene(context)
                            clearChildNodes()
                            clearAnimation()
                            destroy()
                        }
                    }

                    when {
                        viewModel.modelFromPhotoConstructorUIState.value.clearScene.value -> {
                            clearChildNodes()
                            clearAnimation()
                            destroy()
                            viewModel.updateCanGoState()
                        }
                    }
                }
            )
        }
    }
}

